<template>
    <form @submit.prevent="processForm">
        <div class="modal-card">
            <header class="modal-card-head">
                <p class="modal-card-title">{{facility.name}}</p>
            </header>

            <section class="modal-card-body">
                <b-field :label="$t('facilities.name')">
                    <b-input type="text" v-model="facility.name" placeholder="Name" required/>
                </b-field>
                <b-field :label="$t('facilities.locationId')">
                    <b-input type="number" min="1" v-model="facility.locationId" :placeholder="$t('facilities.number')" required/>
                </b-field>
                <b-field :label="$t('facilities.provider')">
                    <b-select v-model="facility.provider" :placeholder="$t('facilities.chooseProvider')" required>
                        <option v-for="option in ['SV', 'ZFV']" :key="option">
                            {{ option }}
                        </option>
                    </b-select>
                </b-field>
            </section>

            <footer class="modal-card-foot">
                <button class="button" type="button" @click="$parent.close()">{{$t('global.close')}}</button>
                <button class="button is-primary">{{isNewEntry ? $t('global.create') : $t('global.update')}}</button>
            </footer>
        </div>
    </form>
</template>

<script>
    import service from '../../services/gastronomicFacility.service'

    export default {
        props: {
            isNewEntry: Boolean,
            currentFacility: Object,
            newOrUpdated: Function
        },
        data() {
            return {
                facility: {...this.currentFacility}
            }
        },
        methods: {
            processForm() {
                if (this.isNewEntry) {
                    service.create(this.facility).then(this.onSuccess);
                } else {
                    service.update(this.facility).then(this.onSuccess);
                }
            },
            onSuccess(response) {
                this.newOrUpdated(response.data);
                this.$parent.close();
            }
        }
    }
</script>
