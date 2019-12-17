import Vue from 'vue'
import Buefy from 'buefy'
import 'buefy/dist/buefy.css'
import App from "./App";
import store from './store'
import {router} from './router'

Vue.config.productionTip = false;

Vue.use(Buefy);

new Vue({
    el: '#app',
    router,
    template: '<App />',
    store,
    components: {App}
});
