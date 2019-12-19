import VueI18n from 'vue-i18n'
import Vue from 'vue'
import {DEFAULT_LANGUAGE, FALLBACK_LANGUAGE} from './constants'
import en from './messages/en.json'

Vue.use(VueI18n);

export const i18n = new VueI18n({
    locale: DEFAULT_LANGUAGE,
    fallbackLocale: FALLBACK_LANGUAGE,
    messages: {en}
});
