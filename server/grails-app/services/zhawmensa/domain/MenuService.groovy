package zhawmensa.domain

import grails.gorm.transactions.Transactional
import zhawmensa.I18nService
import zhawmensa.Menu
import zhawmensa.exceptions.ObjectNotFoundException

@Transactional
class MenuService {

    I18nService i18nService

    List<Menu> findAllByMenuPlanId(long menuPlanId) {
        return Menu.createCriteria().list {
            menuPlan {
                eq('id', menuPlanId)
            }
            order('date', 'asc')
            order('label', 'asc')
        } as List<Menu>
    }

    Menu store(Menu menu) {
        return menu.save()
    }

    Menu findById(long id) {
        Menu menu = Menu.findById(id)
        if (!menu) {
            throw new ObjectNotFoundException(i18nService.getMessage("menu.error.notFound", [id]))
        }
        return menu
    }

    void deleteById(long id) {
        Menu menuToDelete = findById(id)
        menuToDelete.delete()
    }
}
