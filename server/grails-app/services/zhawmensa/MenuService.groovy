package zhawmensa

import grails.gorm.transactions.Transactional

@Transactional
class MenuService {

    List<Menu> findAllByMenuPlanId(long menuPlanId) {
        return Menu.createCriteria().list {
            menuPlan {
                eq('id', menuPlanId)
            }
            order('label', 'asc')
        } as List<Menu>
    }

    Menu store(Menu menu) {
        return menu.save()
    }

    boolean deleteById(long id) {
        Menu menuToDelete = Menu.findById(id)
        if (menuToDelete) {
            menuToDelete.delete()
            return true
        } else {
            return false
        }
    }
}
