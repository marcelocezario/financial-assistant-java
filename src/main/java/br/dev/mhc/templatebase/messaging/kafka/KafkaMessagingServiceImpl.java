package br.dev.mhc.templatebase.messaging.kafka;

import br.dev.mhc.templatebase.common.logs.LogHelper;
import br.dev.mhc.templatebase.email.EmailDTO;
import br.dev.mhc.templatebase.messaging.IConsumerMessageService;
import br.dev.mhc.templatebase.messaging.IProducerMessageService;
import br.dev.mhc.templatebase.messaging.MessageHandler;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Profile("(prod | qa)")
@Service
public class KafkaMessagingServiceImpl implements IProducerMessageService, IConsumerMessageService {

    private static final LogHelper LOG = new LogHelper(KafkaMessagingServiceImpl.class);
    private static final Map<String, MessageHandler> handlers = new HashMap<>();
    private final KafkaTemplate<String, Object> kafkaTemplate;


    public KafkaMessagingServiceImpl(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void send(String topic, Object message) {
        kafkaTemplate.send(topic, message);
    }

    @Override
    public void receive(String topic, MessageHandler<?> messageHandler) {
        handlers.put(topic, messageHandler);
    }

    @KafkaListener(topics = "email", groupId = "group-1")
    public void consumer(EmailDTO message) {
        handlers.get("email").handleMessage(message);
    }

}
