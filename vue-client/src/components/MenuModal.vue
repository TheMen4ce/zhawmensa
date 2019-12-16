<template>
    <form @submit.prevent="processForm">
        <div class="modal-card">
            <header class="modal-card-head">
                <p class="modal-card-title">{{menu.title}}</p>
            </header>
            <section class="modal-card-body">
                <b-field label="Title">
                    <b-input type="text" v-model="menu.title" placeholder="Title" required/>
                </b-field>
                <b-field label="Label">
                    <b-input type="text" v-model="menu.label" placeholder="Label" required/>
                </b-field>
                <b-field label="Side Dishes">
                    <b-input type="textarea" maxlength="500" v-model="menu.sideDishes" placeholder="Sidedishes"
                             required/>
                </b-field>
                <b-field label="Student Price">
                    <b-input icon="cash-100" type="number" v-model="menu.studentPrice" min="1" step=".05" required/>
                </b-field>
                <b-field label="Internal Price">
                    <b-input icon="cash-100" type="number" v-model="menu.internalPrice" min="1" step=".05" required/>
                </b-field>
                <b-field label="External Price">
                    <b-input icon="cash-100" type="number" v-model="menu.externalPrice" min="1" step=".05" required/>
                </b-field>
            </section>
            <footer class="modal-card-foot">
                <button class="button" type="button" @click="$parent.close()">Close</button>
                <button class="button is-primary" type="submit" >{{isNewEntry ? 'Create' : 'Update'}}</button>
                <button class="button is-danger float-right" type="button" @click="deleteMenu(menu.id)">Delete</button>
            </footer>
        </div>
    </form>
</template>

<script>
    import service from '../services/menu.service'
    import toaster from '../services/toaster.service'

    export default {
        name: "MenuModal",
        props: {
            isNewEntry: Boolean,
            currentMenu: Object,
            newOrUpdated: Function,
            wasDeleted: Function
        },
        data() {
            return {
                menu: {...this.currentMenu}
            }
        },
        methods: {
            processForm() {
                if (this.isNewEntry) {
                    service.create(this.menu).then(this.onSuccess);
                } else {
                    service.update(this.menu).then(this.onSuccess);
                }
            },
            onSuccess(response) {
                this.newOrUpdated(response.data);
                this.$parent.close();
            },
            deleteMenu(menuId){
                service.delete(menuId).then(() => {
                    toaster.success("Minus one menu!");
                    this.wasDeleted(menuId);
                    this.$parent.close();
                })
            }
        }
    }
</script>

<style>
    .float-right {
        margin-left: auto;
    }
</style>