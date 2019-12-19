import {SUPPORTED_LANGUAGES, DEFAULT_LANGUAGE} from '../i18n/constants'
import {i18n} from '../i18n/i18n'

class TranslationService {
    constructor() {
        if (!this.currentLanguage) {
            this.currentLanguage = DEFAULT_LANGUAGE;
        }
        this.changeLanguage(this.currentLanguage)
    }

    get supportedLanguages() {
        return SUPPORTED_LANGUAGES;
    }
    get currentLanguage() {
        return localStorage.getItem('lang');
    }
    set currentLanguage(lang) {
        localStorage.setItem('lang', lang);
        document.querySelector('html').setAttribute('lang', lang);
        i18n.locale = lang;
    }

    /**
     * Loads new translation messages and changes the language when finished
     * @param lang
     */
    changeLanguage(lang) {
        if (!this.isLangSupported(lang)) {
            lang = DEFAULT_LANGUAGE;
        }
        if (i18n.locale === lang) return Promise.resolve(lang);

        this.loadLanguageFile(lang)
            .then(messages => {
                i18n.setLocaleMessage(lang, messages);
                this.currentLanguage = lang;
            })
    }

    /**
     * Async loads a translation file
     * @param lang
     * @return {Promise<*>|*}
     */
    loadLanguageFile(lang) {
        return import(`../i18n/messages/${lang}.json`)
    }

    isLangSupported(lang) {
        return this.supportedLanguages.includes(lang)
    }
}

export default new TranslationService();
