import axios from 'axios'
import toaster from '../services/toaster.service'

const SERVER_URL = 'http://localhost:8080';

const instance = axios.create({
    baseURL: SERVER_URL,
    timeout: 10000
});

// general error handling
instance.interceptors.response.use((response) => response, (error) => {
    if (error.response?.data?.error) {
        toaster.error(error.response.data.error);
    } else if (error.response?.data) {
        toaster.error(error.response.data);
    } else {
        toaster.error(error.message);
    }

    if (error.response?.status === 401) {
        // in case the user never logged out and there is still a user in local storage
        localStorage.removeItem('user');
    }
    throw error
});

// provide auth header if existing
instance.interceptors.request.use(function (config) {
    let user = JSON.parse(localStorage.getItem('user'));

    if (user?.access_token) {
        config.headers.Authorization = 'Bearer ' + user.access_token;
    }

    return config;
});

export default instance
