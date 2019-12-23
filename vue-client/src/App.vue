<template>
    <div id="app">
        <Navigation/>
        <router-view class="body"/>
    </div>
</template>

<script>
    import Navigation from "./components/nav/Navigation";

    export default {
        name: 'app',
        components: {Navigation},
    }
</script>

<style>
    @font-face {
        font-family: "HelveticaRoundedBold";
        src: url('./assets/fonts/HelveticaRoundedBold.woff2');
    }

    h1, h2, h3, h4, h5 {
        font-family: HelveticaRoundedBold, Arial, "Helvetica Neue", Helvetica, sans-serif;
    }

    .body {
        padding: .5rem;
    }
</style>
