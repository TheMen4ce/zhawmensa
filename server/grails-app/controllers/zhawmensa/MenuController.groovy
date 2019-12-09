package zhawmensa

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

    def update(Menu menu) {
        Menu updatedMenu = menuService.store(menu)
        respond updatedMenu
    }

    def delete() {
        if (menuService.deleteById(params.id as long)) {
            render status: 200
        } else {
            render status: 404
        }
    }
}
