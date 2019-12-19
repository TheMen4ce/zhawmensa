<template>
    <b-select name="language" v-on:input="changeLanguage" v-model="selectedLang" placeholder="select language" icon="translate">
        <option v-for="lang in supportedLanguages" :key="lang">
            {{lang}}
        </option>
    </b-select>
</template>

<script>
    import translationService from '../services/translation.service'

    export default {
        data() {
            return {
                selectedLang: ''
            }
        },
        computed: {
            supportedLanguages() {
                return translationService.supportedLanguages
            }
        },
        methods: {
            changeLanguage() {
                translationService.changeLanguage(this.selectedLang);
            }
        },
        mounted() {
            this.selectedLang = translationService.currentLanguage;
        }
    }
</script>
