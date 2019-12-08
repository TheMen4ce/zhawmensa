import axios from 'axios'

const SERVER_URL = 'http://localhost:8080';

const instance = axios.create({
    baseURL: SERVER_URL,
    timeout: 1000
});

export default {
    create: facility => instance.post('gastronomicFacility', facility),
    getAll: () => instance.get('gastronomicFacility'),
    update: facility => instance.put('gastronomicFacility/' + facility.id, facility),
    delete: id => instance.delete('gastronomicFacility/' + id)
}