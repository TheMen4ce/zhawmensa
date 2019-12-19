import Vue from 'vue'
import Buefy from 'buefy'
import 'buefy/dist/buefy.css'
import App from "./App";
import store from './store'
import {router} from './router'
import {i18n} from "./i18n/i18n";

Vue.config.productionTip = false;

Vue.use(Buefy);

new Vue({
    el: '#app',
    router,
    template: '<App />',
    store,
    i18n,
    components: {App}
});
