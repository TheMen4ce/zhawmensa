import Vue from 'vue'
import Buefy from 'buefy'
import 'buefy/dist/buefy.css'
import VueRouter from "vue-router";
import MenuPlan from "./components/MenuPlan";
import Facilities from "./components/Facilities";
import App from "./App";

Vue.config.productionTip = false;

Vue.use(Buefy);
Vue.use(VueRouter);

const routes = [
    { path: '/', component: Facilities },
    { path: '/menuplan/:facilityId', component: MenuPlan }
];

const router = new VueRouter({
    routes
});

new Vue({
    el: '#app',
    router,
    template: '<App />',
    components: {App}
});
