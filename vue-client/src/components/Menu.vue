<template>
    <div class="card">
        <div class="card-content">
            <div class="title-row">
                <div class="menu-title title is-5">{{menu.title}}</div>
                <b-icon class="edit-icon" icon="pencil" size="is-small" @click.native="editMenu(menu)"/>
            </div>
            <p class="menu-subtitle subtitle is-6">{{menu.label}}</p>
            <div class="content">
                <div class="side-dish">{{menu.sideDishes}}</div>
            </div>
            <div>
                <b-tag type="is-info">STUD: {{menu.studentPrice}}</b-tag>
                <b-tag type="is-info">INT: {{menu.internalPrice}}</b-tag>
                <b-tag type="is-info">EXT: {{menu.externalPrice}}</b-tag>
            </div>
        </div>
    </div>
</template>

<script>
    import MenuModal from "./MenuModal";

    export default {
        name: "Menu",
        props: ['menu'],
        methods: {
            editMenu(menu) {
                console.log(menu);
                this.$buefy.modal.open({
                    parent: this,
                    component: MenuModal,
                    hasModalCard: true,
                    props: {
                        isNewEntry: false,
                        currentMenu: menu,
                        newOrUpdated: this.wasUpdated
                    }
                })
            },
            wasUpdated(menu){
                console.log(menu)
            }
        }
    }
</script>

<style scoped>
    .title-row {
        display: flex;
        justify-content: space-between;
    }

    .menu-title{
        margin: 0;
    }

    .menu-subtitle{
        margin-bottom: 1rem;
        font-style: italic;
    }

    .edit-icon {
        float: right;
        cursor: pointer;
    }

    .edit-icon:hover{
        color: blue;
    }

    .side-dish {
        white-space: pre-wrap;
        font-size: smaller;
    }

    .card-content {
        padding: .5rem;
        display: flex;
        flex-direction: column;
        height: 100%;
    }

    .content {
        flex-grow: 5;
    }

    .card {
        width: 15rem;
        height: 100%;
    }

    .tag {
        margin-right: 0.2rem;
    }
</style>