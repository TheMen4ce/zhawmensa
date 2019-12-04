package zhawmensa

class ImportController {
	static responseFormats = ['text']

    MenuImportService menuImportService
	
    def index() {
        menuImportService.importMenuPlans()

        render "OK"
    }
}
