<template>
    <div class="card">
        <div class="card-content">
            <div class="media-content">
                <p class="title is-6">{{menu.title}}</p>
            </div>
            <div class="content">
                <div class="side-dish">{{menu.sideDishes}}</div>
                <b-tag type="is-info">EXT: {{menu.externalPrice}}</b-tag>
                <b-tag type="is-info">INT: {{menu.internalPrice}}</b-tag>
                <b-tag type="is-info">STUD: {{menu.studentPrice}}</b-tag>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        name: "Menu",
        props: ['menu']
    }
</script>

<style scoped>
    .side-dish {
        white-space: pre-wrap;
        font-size: smaller;
    }
    .card-content{
        padding: .5rem;
    }
    .card{
        width: 15rem;
    }
    .tag{
        margin-right: 0.2rem;
    }
</style>