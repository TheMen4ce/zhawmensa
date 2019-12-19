<template>
    <div>
        <h2 class="title is-4">{{$t('nav.facilities')}}</h2>
        <button class="button is-primary"
                @click="newFacility()">
            {{$t('facilities.new')}}
        </button>
        <MenuImport/>
        <br><br>
        <b-table :data="facilities" striped default-sort="name">
            <template slot-scope="props">
                <b-table-column field="name" :label="$t('facilities.name')" sortable>
                    {{ props.row.name }}
                </b-table-column>

                <b-table-column field="locationId" :label="$t('facilities.locationId')" sortable>
                    {{ props.row.locationId }}
                </b-table-column>

                <b-table-column field="provider" :label="$t('facilities.provider')" sortable>
                    {{ props.row.provider }}
                </b-table-column>

                <b-table-column>
                    <b-button tag="router-link" :to="`/menuplan/${props.row.id}`" type="">{{$t('nav.menuPlan')}}</b-button>
                    <b-button icon-right="pencil" @click="editFacility(props.row)"/>
                    <b-button type="is-danger" @click="deleteFacility(props.row)" icon-right="delete"/>
                </b-table-column>
            </template>

            <template slot="empty">
                <section class="section">
                    <div class="content has-text-grey has-text-centered">
                        <p>
                            <b-icon icon="emoticon-sad" size="is-large"/>
                        </p>
                        <p>{{$t('facilities.noFound')}}</p>
                    </div>
                </section>
            </template>
        </b-table>
    </div>
</template>

<script>
    import service from '../services/facilities.service'
    import toaster from '../services/toaster.service'
    import FacilityModal from "./FacilityModal";
    import MenuImport from "./MenuImport";

    export default {
        components: {MenuImport},
        data() {
            return {
                facilities: []
            }
        },
        mounted() {
            this.fetchFacilities()
        },
        methods: {
            fetchFacilities() {
                service.getAll().then(res => {
                    this.facilities = res.data;
                })
            },

            deleteFacility(facility) {
                service.delete(facility.id)
                    .then(() => {
                        toaster.success(this.$t('facilities.deleted'));
                        let deletedFacility = this.facilities.find(f => f.id === facility.id);
                        const idx = this.facilities.indexOf(deletedFacility);
                        this.facilities.splice(idx, 1);
                    })
            },
            editFacility(facility) {
                this.openModal(false, facility, this.wasUpdated)
            },

            newFacility() {
                this.openModal(true, {}, this.wasCreated)
            },

            wasUpdated(facility) {
                let existingFacility = this.facilities.find(f => f.id === facility.id);
                const idx = this.facilities.indexOf(existingFacility);
                this.$set(this.facilities, idx, facility);
            },

            wasCreated(facility) {
                this.facilities.push(facility)
            },

            openModal(isNewEntry, facility, callback) {
                this.$buefy.modal.open({
                    parent: this,
                    component: FacilityModal,
                    hasModalCard: true,
                    props: {
                        isNewEntry: isNewEntry,
                        currentFacility: facility,
                        newOrUpdated: callback
                    }
                })
            }
        }
    }

</script>

<style scoped>
    .button {
        margin-right: .5rem;
    }
</style>
