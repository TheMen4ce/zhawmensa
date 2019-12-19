<template>
    <div>
        <div class="card">
            <div class="card-content">
                <h2 class="title is-4">Profile</h2>
                <b-field label="Username" :message="{'Username cannot contain special characters!' : !usernameContainsNoSpecialChars()}"
                         :type="{'is-danger' : !usernameContainsNoSpecialChars()}">
                    <b-input v-model="username" required/>
                </b-field>
                <button class="button is-primary is-medium"
                        @click="changeUsername()"
                        :disabled="!canChangeUsername()">
                    Change Username
                </button>
                <br><br>
                <b-field label="Old Password">
                    <b-input v-model="oldPassword" type="password" password-reveal/>
                </b-field>
                <b-field label="New Password">
                    <b-input v-model="newPassword" type="password" password-reveal/>
                </b-field>
                <b-field label="Repeat New Password" :message="{'Passwords must match!' : !passwordsMatch()}"
                         :type="{'is-danger' : !passwordsMatch()}">
                    <b-input v-model="newPasswordMatch" type="password" password-reveal/>
                </b-field>
                <button class="button is-primary is-medium"
                        @click="changePassword()"
                        :disabled="!canChangePassword()">
                    Change Password
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
    import AuthService from '../services/auth.service';
    import toaster from "../services/toaster.service";

    export default {
        name: 'profile',
        data() {
            return {
                username: '',
                oldPassword: '',
                newPassword: '',
                newPasswordMatch: '',
            }
        },
        methods: {
            changeUsername() {
                AuthService.changeUsername(this.currentUser.username, this.username)
                    .then(this.afterChangeSuccess);
            },
            changePassword() {
                AuthService.changePassword(this.username, this.oldPassword, this.newPassword)
                    .then(this.afterChangeSuccess);
            },
            afterChangeSuccess() {
                toaster.success("Please login again :)");
                this.$store.dispatch('auth/logout')
            },
            passwordsMatch() {
                return this.newPassword === this.newPasswordMatch;
            },
            canChangePassword() {
                return this.oldPassword && this.passwordsMatch() && this.newPassword;
            },
            usernameContainsNoSpecialChars() {
                return /^\w+$/.test(this.username);
            },
            canChangeUsername() {
                return this.username !== this.currentUser.username && this.usernameContainsNoSpecialChars();
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
