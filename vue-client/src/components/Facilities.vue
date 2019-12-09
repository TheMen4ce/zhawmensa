<template>
    <div>
        <button class="button is-primary is-medium"
                @click="newFacility()">
            New Facility
        </button>
        <MenuImport/>
        <b-table :data="facilities" striped default-sort="name">
            <template slot-scope="props">
                <b-table-column field="id" label="ID" width="40" numeric sortable>
                    {{ props.row.id }}
                </b-table-column>

                <b-table-column field="name" label="Name" sortable>
                    {{ props.row.name }}
                </b-table-column>

                <b-table-column field="locationId" label="Location ID" sortable>
                    {{ props.row.locationId }}
                </b-table-column>

                <b-table-column field="provider" label="Provider" sortable>
                    {{ props.row.provider }}
                </b-table-column>

                <b-table-column>
                    <router-link :to="`/menus/${props.row.id}`">Menus</router-link>
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
                        <p>Nothing here.</p>
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
        name: "Facilities",
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

            newOrUpdated(facility) {
                let existingFacility = this.facilities.find(f => f.id === facility.id);
                if (existingFacility) {
                    existingFacility = facility;
                } else {
                    this.facilities.push(facility)
                }
                this.fetchFacilities()
            },
            deleteFacility(facility) {
                service.delete(facility.id)
                    .then(() => {
                        toaster.success("Minus one facility!");
                        this.fetchFacilities()
                    })
                    .catch(() => {
                        toaster.error(`Couldn't delete ${facility.name}`)
                    })
            },
            editFacility(facility) {
                this.openModal(false, facility)
            },

            newFacility() {
                this.openModal(true, {})
            },

            openModal(isNewEntry, facility) {
                this.$buefy.modal.open({
                    parent: this,
                    component: FacilityModal,
                    hasModalCard: true,
                    props: {
                        isNewEntry: isNewEntry,
                        currentFacility: facility,
                        newOrUpdated: this.newOrUpdated
                    }
                })
            }
        }
    }

</script>

<style scoped>
    button {
        margin-right: 1rem;
    }
</style>