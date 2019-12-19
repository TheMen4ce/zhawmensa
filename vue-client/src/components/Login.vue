<template>
    <section>
        <form name="form" @submit.prevent="handleLogin">
            <b-field :label="$t('login.username')" :type="{'is-danger' : message}" :message="message">
                <b-input v-model="user.username" maxlength="30" required/>
            </b-field>

            <b-field :label="$t('login.password')">
                <b-input type="password"
                         v-model="user.password"
                         password-reveal
                         required/>
            </b-field>
            <button class="button is-primary" type="submit">Login</button>
        </form>
    </section>
</template>

<script>
    import User from '../models/user';

    export default {
        name: 'login',
        computed: {
            loggedIn() {
                return this.$store.state.auth.status.loggedIn;
            }
        },
        data() {
            return {
                user: new User('', ''),
                loading: false,
                message: ''
            };
        },
        mounted() {
            if (this.loggedIn) {
                this.$router.push('/');
            }
        },
        methods: {
            handleLogin() {
                this.loading = true;

                if (this.user.username && this.user.password) {
                    this.$store.dispatch('auth/login', this.user).then(
                        () => {
                            this.$router.push('/');
                        },
                        () => {
                            this.loading = false;
                            this.message = 'Login failed';
                        }
                    );
                }
            }
        }
    };
</script>
