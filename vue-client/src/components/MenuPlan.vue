<template>
    <div>
        <b-select v-model="selectedMenuPlan" placeholder="no menu plans" required>
            <option
                    v-for="option in menuPlans"
                    :key="option.id"
                    :value=option>
                {{ option.year }} | KW {{ option.calendarWeek }}
            </option>
        </b-select>
        {{selectedMenuPlan}}
        <Menus v-if="selectedMenuPlan" :menuPlanId=selectedMenuPlan.id />
    </div>
</template>

<script>
    import service from "../services/menuplan.service";
    import Menus from "./Menus";

    export default {
        name: "MenuPlan",
        components: {
            Menus
        },
        data() {
            return {
                menuPlans: [],
                selectedMenuPlan: undefined
            }
        },
        mounted() {
            this.fetchMenuPlan()
        },
        methods: {
            fetchMenuPlan() {
                service.getAll(this.$route.params.facilityId).then(res => {
                    this.menuPlans = res.data;
                    this.selectedMenuPlan = this.menuPlans[0];
                })
            }
        }
    }
</script>

<style scoped>

</style>