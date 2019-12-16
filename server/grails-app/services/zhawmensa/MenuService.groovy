package zhawmensa

import grails.gorm.transactions.Transactional
import zhawmensa.exceptions.ObjectNotFoundException

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

    Menu findById(long id) {
        Menu menu = Menu.findById(id)
        if (!menu) {
            throw new ObjectNotFoundException("No menu found with id ${id}")
        }
        return menu
    }

    void deleteById(long id) {
        Menu menuToDelete = Menu.findById(id)
        if (!menuToDelete) {
            throw new ObjectNotFoundException("No menu found with id ${id}")
        }
        menuToDelete.delete()
    }
}
