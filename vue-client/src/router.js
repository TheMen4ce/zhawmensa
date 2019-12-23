import Vue from 'vue';
import Router from 'vue-router';
import Login from './components/user/Login.vue';
import Facilities from "./components/facilities/Facilities";
import MenuPlan from "./components/menuPlan/MenuPlan";
import Profile from "./components/user/Profile";

Vue.use(Router);

export const router = new Router({
    mode: 'history',
    routes: [
        {path: '/', component: Facilities},
        {path: '/gastronomicFacility/:facilityId/menuPlan', component: MenuPlan},
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
