package br.dev.mhc.financialassistant.common.logs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.Objects;

public class LogHelper {

    private final Logger LOG;

    public LogHelper(Class<?> classType) {
        this.LOG = LogManager.getLogger(classType);
    }

    public void debug(String message) {
        this.LOG.debug(message);
    }

    public void debug(String message, Object... references) {
        String reference = Arrays.stream(references)
                .map(Objects::toString)
                .reduce("", (a, b) -> a.concat("[").concat(b).concat("]"));
        this.LOG.debug(message.concat(reference));
    }

    public void error(String message) {
        this.LOG.error(message);
    }

    public void error(String message, Object... references) {
        String reference = Arrays.stream(references)
                .map(Objects::toString)
                .reduce("", (a, b) -> a.concat("[").concat(b).concat("]"));
        this.LOG.error(message.concat(reference));
    }

    public void stackTrace(String message, Throwable throwable) {
        this.LOG.error(message, throwable);
    }

}
