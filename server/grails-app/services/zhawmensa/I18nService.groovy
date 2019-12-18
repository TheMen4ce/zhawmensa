package zhawmensa

import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder

class I18nService {

    MessageSource messageSource

    /**
     * Returns the message for the given key in the correct language, fallback to english
     * @param key used to fetch message in messages.properties
     * @param params objects for use in the message
     * @return the message in the correct language
     */
    String getMessage(String key, List params = null) {
        return messageSource.getMessage(key,params?.toArray(),LocaleContextHolder.getLocale())
    }

}
