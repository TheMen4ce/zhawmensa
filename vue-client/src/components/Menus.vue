<template>
    <div class="table-container">
        <b-loading :is-full-page="false" :active.sync="isLoading"/>
        <table class="table" v-if="!isLoading">
            <tr v-for="day in days" :key="day">
                <td>{{getFormattedDay(day)}}</td>
                <td class="menu-cell" v-for="menu in getMenusFor(day)" :key="menu.id">
                    <Menu :menu=menu v-on:menu-updated="wasUpdated" v-on:menu-deleted="wasDeleted"/>
                </td>
            </tr>
        </table>
    </div>
</template>

<script>
    import service from "../services/menu.service";
    import Menu from "./Menu";
    import dayjs from "dayjs"

    export default {
        props: ['menuPlanId'],
        components: {
            Menu
        },
        data() {
            return {
                menus: [],
                days: [],
                isLoading: false
            }
        },
        mounted() {
            this.fetchMenus()
        },
        watch: {
            menuPlanId: function () {
                this.fetchMenus()
            }
        },
        methods: {
            getFormattedDay(day) {
                return dayjs(day).format('DD.MM.YYYY')
            },
            fetchMenus() {
                this.isLoading = true;
                service.getAll(this.menuPlanId)
                    .then(response => {
                        this.menus = response.data;
                        this.days = [...new Set(this.menus.map(menu => menu.date))];
                        this.isLoading = false;
                    })
            },
            getMenusFor(day) {
                return this.menus.filter(menu => menu.date === day);
            },
            wasUpdated(menu) {
                let existingMenu = this.menus.find(m => m.id === menu.id);
                const idx = this.menus.indexOf(existingMenu);
                this.$set(this.menus, idx, menu);
            },
            wasDeleted(menuId) {
                let existingMenu = this.menus.find(m => m.id === menuId);
                const idx = this.menus.indexOf(existingMenu);
                this.menus.splice(idx, 1);
            },
        }
    }
</script>

<style scoped>
    .menu-cell {
        height: 0;
    }
</style>
