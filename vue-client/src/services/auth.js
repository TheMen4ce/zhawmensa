import api from './api'

class AuthService {
    login(user) {
        return api
            .post('api/login', {
                username: user.username,
                password: user.password
            })
            .then(response => {
                if (response.data.access_token) {
                    localStorage.setItem('user', JSON.stringify(response.data));
                }

                return response.data;
            });
    }

    logout() {
        localStorage.removeItem('user');
        location.reload(true);
    }

    changeUsername(oldUsername, newUsername) {
        return api.post('user/changeUsername', {oldUsername: oldUsername, newUsername: newUsername});
    }
}

export default new AuthService();