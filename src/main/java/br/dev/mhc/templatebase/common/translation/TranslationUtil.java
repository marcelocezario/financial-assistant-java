package br.dev.mhc.templatebase.common.translation;

import br.dev.mhc.templatebase.common.logs.LogHelper;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;

import java.util.Locale;

public class TranslationUtil {

    private final static LogHelper LOG = new LogHelper(TranslationUtil.class);
    private final static MessageSource MESSAGE_SOURCE = TranslationConfig.messageSource();

    public static String translate(TranslationKey key) {
        return translate(key, (Object) null);
    }

    public static String translate(TranslationKey key, Object... args) {
        return translate(key.toString(), args);
    }

    private static String translate(String translateKey, Object... args) {
        try {
            Locale locale = LocaleContextHolder.getLocale();
            return MESSAGE_SOURCE.getMessage(translateKey, args, locale);
        } catch (NoSuchMessageException e) {
            LOG.error("Message not found: [" + translateKey + "]");
            return translateKey;
        }
    }

    public static String translateHttpStatus(HttpStatus httpStatus) {
        try {
            Locale locale = LocaleContextHolder.getLocale();
            return MESSAGE_SOURCE.getMessage("http." + httpStatus.value(), null, httpStatus.getReasonPhrase(), locale);
        } catch (NoSuchMessageException e) {
            LOG.error(e.getMessage());
            return httpStatus.getReasonPhrase();
        }
    }

}
