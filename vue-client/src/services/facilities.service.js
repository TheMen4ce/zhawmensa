import api from './api'

export default {
    create: facility => api.post('gastronomicFacility', facility),
    getAll: () => api.get('gastronomicFacility'),
    update: facility => api.put('gastronomicFacility/' + facility.id, facility),
    delete: id => api.delete('gastronomicFacility/' + id)
}