<template>
    <section class="section">
        <div class="content has-text-grey has-text-centered">
            <p>
                <b-icon icon="emoticon-sad" size="is-large"/>
            </p>
            <p>{{text}}</p>
        </div>
    </section>
</template>

<script>
    export default {
        props: ['text']
    }
</script>
