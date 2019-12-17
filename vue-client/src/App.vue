<template>
    <div id="app">
        <b-navbar>
            <template slot="brand">
                <b-navbar-item tag="router-link" :to="{ path: '/' }">
                    <img alt="ZHAW School of Engineering Logo" src="./assets/ZHAW_Logo.svg">
                    <h1 class="title">Mensa Master</h1>
                </b-navbar-item>
            </template>
            <template slot="start">
                <b-navbar-item v-if="currentUser" tag="router-link" to="/profile">
                    <b-icon icon="account-circle" size="is-medium"/>
                    {{currentUser.username}}
                </b-navbar-item>
            </template>

            <template slot="end">
                <b-navbar-item tag="div">
                    <div class="buttons">
                        <b-button v-if="!currentUser"
                                  tag="router-link" to="/login" type="is-link is-primary">
                            Login
                        </b-button>
                        <b-button v-if="currentUser" @click="logOut"
                                  to="/login" type="is-info">
                            Logout
                        </b-button>
                    </div>
                </b-navbar-item>
            </template>
        </b-navbar>
        <router-view class="body"/>
    </div>
</template>

<script>
    export default {
        name: 'app',
        computed: {
            currentUser() {
                return this.$store.state.auth.user;
            },
        },
        methods: {
            logOut() {
                this.$store.dispatch('auth/logout');
                this.$router.push('/login');
            }
        }
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
