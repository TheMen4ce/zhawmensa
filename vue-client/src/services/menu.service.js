import api from './api'

export default {
    getAll: menuPlanId => api.get(`menus/${menuPlanId}`)
}