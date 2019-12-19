import api from './api'

export default {
    getAll: menuPlanId => api.get(`menuPlan/${menuPlanId}`),
    delete: menuPlanId => api.delete(`menuPlan/${menuPlanId}`)
}
