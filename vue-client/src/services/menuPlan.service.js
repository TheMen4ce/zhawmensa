import api from './api'

export default {
    delete: menuPlanId => api.delete(`menuPlan/${menuPlanId}`),
    findAllMenus: menuPlanId => api.get(`menuPlan/${menuPlanId}/menus`)
}
