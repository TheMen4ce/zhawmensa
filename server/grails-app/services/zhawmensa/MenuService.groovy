package zhawmensa

import grails.gorm.transactions.Transactional

@Transactional
class MenuService {

    List<Menu> findAllByMenuPlanId(long menuPlanId) {
        return Menu.createCriteria().list{
            menuPlan{
                eq('id', menuPlanId)
            }
        } as List<Menu>
    }
}
