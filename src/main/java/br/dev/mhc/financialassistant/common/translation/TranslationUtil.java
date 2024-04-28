package br.dev.mhc.financialassistant.common.translation;

import br.dev.mhc.financialassistant.common.logs.LogHelper;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;

import java.util.Locale;

public class TranslationUtil {

    private final static LogHelper LOG = new LogHelper(TranslationUtil.class);
    private final static MessageSource MESSAGE_SOURCE = TranslationConfig.messageSource();

    protected static String translate(String translateKey) {
        return translate(translateKey, (Object) null);
    }

    protected static String translate(String translateKey, Object... args) {
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
