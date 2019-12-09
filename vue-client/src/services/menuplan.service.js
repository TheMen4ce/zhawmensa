import api from './api'

export default {
    getAll: facilityId => api.get(`menuplan/${facilityId}`)
}