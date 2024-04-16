package br.dev.mhc.templatebase.messaging.client.impl;

import br.dev.mhc.templatebase.common.logs.LogHelper;
import br.dev.mhc.templatebase.messaging.MessageHandler;
import br.dev.mhc.templatebase.messaging.client.interfaces.IMessagingClientService;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;

@Service
public class MockMessagingClientServiceImpl implements IMessagingClientService {

    private static final LogHelper LOG = new LogHelper(MockMessagingClientServiceImpl.class);
    private static final Map<String, Queue<Object>> topics = new ConcurrentHashMap<>();
    private static final Map<String, MessageHandler> handlers = new ConcurrentHashMap<>();

    @Override
    public void send(String topic, Object message) {
        LOG.debug("Start send message", topic, message);
        requireNonNull(topic);
        requireNonNull(message);
        topics.computeIfAbsent(topic, t -> new ConcurrentLinkedQueue<>()).add(message);
        LOG.debug("End send message", topic, message);
        Thread.startVirtualThread(consumerMessage(topic));
    }

    @Override
    public void receive(String topic, MessageHandler messageHandler) {
        LOG.debug("Start receive message", topic);
        requireNonNull(topic);
        requireNonNull(messageHandler);
        handlers.computeIfAbsent(topic, t -> messageHandler);
        LOG.debug("End receive message", topic);
    }

    private Runnable consumerMessage(String topic) {
        return () -> {
            var handler = handlers.get(topic);
            var queue = topics.get(topic);
            if (nonNull(handler)) {
                for (int i = 0; i < queue.size(); i++) {
                    LOG.debug("Start consumer message", topic);
                    handler.handleMessage(queue.poll());
                    LOG.debug("End consumer message", topic);
                }
            } else {
                LOG.error("Handler not found for topic", topic);
            }
        };
    }
}
