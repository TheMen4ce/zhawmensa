<template>
    <b-button class="button is-primary"
            :loading=isImporting
            @click="importMenuplans()">
        Import
    </b-button>
</template>

<script>
    import service from "../services/import.service";
    import toaster from "../services/toaster.service";

    export default {
        data() {
            return {
                isImporting: false
            }
        },
        methods: {
            importMenuplans() {
                this.isImporting = true;
                service.import()
                    .then(response => {
                        toaster.success(response.data);
                    })
                    .finally(() => {
                        this.isImporting = false;
                    })
            },
        }
    }
</script>

<style scoped>

</style>
