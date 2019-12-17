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

    register(user) {
        // TODO
        return api.post('signup', {
            username: user.username,
            email: user.email,
            password: user.password
        });
    }

}

export default new AuthService();