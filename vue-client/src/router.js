import Vue from 'vue';
import Router from 'vue-router';
import Login from './components/Login.vue';
import Facilities from "./components/Facilities";
import MenuPlan from "./components/MenuPlan";
import Profile from "./components/Profile";

Vue.use(Router);

export const router = new Router({
    mode: 'history',
    routes: [
        {path: '/', component: Facilities},
        {path: '/menuplan/:facilityId', component: MenuPlan},
        {path: '/login', component: Login},
        {path: '/profile', component: Profile},
    ]
});

router.beforeEach((to, from, next) => {
    const publicPages = ['/login'];
    const authRequired = !publicPages.includes(to.path);
    const user = localStorage.getItem('user');

    if (authRequired && !user) {
        return next('/login');
    }

    next();
});