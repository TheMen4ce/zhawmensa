package zhawmensa

import zhawmensa.domain.MenuService
import zhawmensa.exceptions.ObjectOutdatedException

class MenuController implements ExceptionHandlingController {
    static responseFormats = ['json']

    MenuService menuService

    def findAllByMenuPlanId() {
        respond menuService.findAllByMenuPlanId(params.id as long)
    }

    def save(Menu menu) {
        Menu createdMenu = menuService.store(menu)
        respond createdMenu
    }

    def update() {
        Menu updatedMenu = menuService.findById(params.id as long)
        long clientVersion = request.JSON.version
        bindData(updatedMenu, request.JSON)
        if (updatedMenu.version > clientVersion) {
            throw new ObjectOutdatedException("menu")
        }

        respond menuService.store(updatedMenu)
    }

    def delete() {
        menuService.deleteById(params.id as long)
        render status: 204
    }
}
