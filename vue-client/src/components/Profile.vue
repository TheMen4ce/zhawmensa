<template>
    <div>
        <div class="card">
            <div class="card-content">
                <p class="title is-4">Profile</p>
                <b-field label="Username">
                    <b-input v-model="username"/>
                </b-field>

                <button class="button is-primary is-medium"
                        @click="changeUsername()">
                    change
                </button>
            </div>
        </div>
        <br>
        <div class="card">
            <div class="card-content">
                <p class="title is-4">Authorities</p>
                <ul>
                    <li v-for="(role,index) in currentUser.roles" :key="index">{{role}}</li>
                </ul>
            </div>
        </div>
    </div>
</template>

<script>
    import AuthService from '../services/auth';
    import toaster from "../services/toaster.service";

    export default {
        name: 'profile',
        data() {
            return {
                username: ''
            }
        },
        methods: {
            changeUsername() {
                AuthService.changeUsername(this.currentUser.username, this.username).then(() => {
                    toaster.success("Username changed. Please login again :)");
                    this.$store.dispatch('auth/logout')
                });
            }
        },
        computed: {
            currentUser() {
                return this.$store.state.auth.user;
            }
        },
        mounted() {
            this.username = this.currentUser.username;
        }
    };
</script>