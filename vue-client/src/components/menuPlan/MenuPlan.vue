<template>
    <div>
        <h2 class="title is-4">{{$t('nav.menuPlan')}} {{facility.name}}</h2>
        <b-loading :is-full-page="false" :active.sync="isLoading"/>
        <b-select class="menu-plan-select" v-model="selectedMenuPlan" :placeholder="$t('menuPlan.noMenuPlan')"
                  required>
            <option
                    v-for="option in facility.menuPlans"
                    :key="option.id"
                    :value=option>
                {{ option.year }} | KW {{ option.calendarWeek }}
            </option>
        </b-select>
        <button class="button is-danger" @click="deletePlan" :disabled="!selectedMenuPlan">
            {{$t('global.delete')}}
        </button>
        <Menus v-if="selectedMenuPlan" :menuPlanId="selectedMenuPlan.id"/>
    </div>
</template>

<script>
    import facilityService from "../../services/gastronomicFacility.service";
    import menuPlanService from "../../services/menuPlan.service";
    import Menus from "./Menus";
    import toaster from "../../services/toaster.service";

    export default {
        components: {
            Menus
        },
        data() {
            return {
                isLoading: false,
                facility: {},
                selectedMenuPlan: undefined
            }
        },
        mounted() {
            this.fetchMenuPlan()
        },
        methods: {
            fetchMenuPlan() {
                this.isLoading = true;
                facilityService.show(this.$route.params.facilityId).then(res => {
                    this.facility = res.data;
                    this.selectedMenuPlan =  this.facility.menuPlans[0];
                    this.isLoading = false;
                })
            },
            deletePlan() {
                menuPlanService.delete(this.selectedMenuPlan.id)
                    .then(() => {
                        toaster.success(this.$t('menuPlan.deleted'));
                        let deletedMenuPlan = this.facility.menuPlans.find(m => m.id === this.selectedMenuPlan.id);
                        const idx = this.facility.menuPlans.indexOf(deletedMenuPlan);
                        this.facility.menuPlans.splice(idx, 1);
                        this.selectedMenuPlan = this.facility.menuPlans[0];
                    })
            }
        }
    }
</script>

<style>
    .menu-plan-select {
        display: inline;
    }
</style>
