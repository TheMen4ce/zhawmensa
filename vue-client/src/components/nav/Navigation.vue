<template>
    <b-navbar>
        <template slot="brand">
            <b-navbar-item tag="a" href="https://www.zhaw.ch/engineering/">
                <img alt="ZHAW School of Engineering Logo" src="../../assets/ZHAW_Logo.jpg">
            </b-navbar-item>
            <b-navbar-item tag="router-link" :to="{ path: '/' }">
                <h1 class="title">Mensa Master</h1>
            </b-navbar-item>
        </template>
        <template slot="start">
            <b-navbar-item tag="router-link" to="/" v-if="currentUser">
                {{$t('nav.facilities')}}
            </b-navbar-item>
            <b-navbar-item tag="router-link" to="/profile" v-if="currentUser">
                {{$t('nav.profile')}}
            </b-navbar-item>
        </template>

        <template slot="end">
            <b-navbar-item tag="div">
                <div class="nav-button">
                    <LanguageSwitch/>
                </div>
                <div class="nav-button">
                    <b-button @click="logOut" to="/login" type="is-primary" v-if="currentUser">
                        {{$t('nav.logout')}}
                    </b-button>
                </div>
            </b-navbar-item>
        </template>
    </b-navbar>
</template>

<script>
    import LanguageSwitch from "./LanguageSwitch";

    export default {
        components: {LanguageSwitch},
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
    @media (max-width: 1024px) {
        .nav-button {
            margin-top: .5rem;
        }
    }

    @media (min-width: 1024px) {
        .nav-button {
            margin-left: .5rem;
        }
    }
</style>
