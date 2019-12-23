import api from './api'

export default {
    create: menu => api.post('menu', menu),
    update: menu => api.put('menu/' + menu.id, menu),
    delete: id => api.delete('menu/' + id)
}
