<template>
    <form @submit.prevent="processForm">
        <div class="modal-card" style="width: auto">
            <header class="modal-card-head">
                <p class="modal-card-title">{{facility.name}}</p>
            </header>
            <section class="modal-card-body">
                <b-field label="Name">
                    <b-input
                            type="text"
                            v-model="facility.name"
                            placeholder="Name"
                            required>
                    </b-input>
                </b-field>
                <b-field label="Location Id">
                    <b-input
                            type="number"
                            min="1"
                            v-model="facility.locationId"
                            placeholder="Name"
                            required>
                    </b-input>
                </b-field>
                <b-field label="Provider">
                    <b-select  v-model="facility.provider" placeholder="Select a provider" required>
                        <option
                                v-for="option in ['SV', 'ZFV']"
                                :key="option">
                            {{ option }}
                        </option>
                    </b-select>
                </b-field>

            </section>
            <footer class="modal-card-foot">
                <button class="button" type="button" @click="$parent.close()">Close</button>
                <button class="button is-primary">{{isNewEntry ? 'Create' : 'Update'}}</button>
            </footer>
        </div>
    </form>
</template>

<script>
    import service from '../services/facilities.service'

    export default {
        name: "FacilityModal",
        props: {
            isNewEntry: Boolean,
            currentFacility: Object,
            newOrUpdated: Function
        },
        data() {
            return {
                facility: {... this.currentFacility}
            }
        },
        methods: {
            processForm() {
                if (this.isNewEntry) {
                    service.create(this.facility).then(this.afterSuccess);
                } else {
                    service.update(this.facility).then(this.afterSuccess);
                }
            },
            afterSuccess(response){
                this.newOrUpdated(response.data);
                this.$parent.close();
            }
        }
    }
</script>