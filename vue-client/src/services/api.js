import axios from 'axios'
import toaster from '../services/toaster.service'
import translationService from '../services/translation.service'

const SERVER_URL = 'http://localhost:8080';

const instance = axios.create({
    baseURL: SERVER_URL,
    timeout: 10000
});

// response interceptor
instance.interceptors.response.use(response => response, error => {
    // general error handling
    if (error.response?.data?.error) {
        toaster.error(error.response.data.error);
    } else if (error.response?.data) {
        toaster.error(error.response.data);
    } else {
        toaster.error(error.message);
    }

    if (error.response?.status === 401 && !error.config.url.endsWith("login")) {
        // in case the user never logged out and there is still a user in local storage
        localStorage.removeItem('user');
        location.reload(true);
    }
    throw error
});

instance.interceptors.request.use(function (config) {
    // provide auth header if existing
    const user = JSON.parse(localStorage.getItem('user'));
    if (user?.access_token) {
        config.headers.Authorization = 'Bearer ' + user.access_token;
    }

    config.headers['Accept-Language'] = translationService.currentLanguage;

    return config;
});

export default instance
