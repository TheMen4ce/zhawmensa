import axios from 'axios'
import toaster from '../services/toaster.service'

const SERVER_URL = 'http://localhost:8080';

const instance = axios.create({
    baseURL: SERVER_URL,
    timeout: 10000
});

instance.interceptors.response.use((response) => response, (error) => {
    toaster.error(error.response?.data ? error.response.data : error);
    throw error;
});

export default instance
