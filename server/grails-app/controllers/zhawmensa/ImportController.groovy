package zhawmensa

class ImportController implements ExceptionHandlingController {
	static responseFormats = ['text']

    MenuImportService menuImportService

    def index() {
        menuImportService.importMenuPlans()
        render "OK"
    }
}
