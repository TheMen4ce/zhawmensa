package zhawmensa

class ImportController {
	static responseFormats = ['text']

    MenuImportService menuImportService
    MenuImportZFVService menuImportZFVService

    def index() {
        menuImportService.importMenuPlans()
        menuImportZFVService.importMenuPlans()
        render "OK"
    }
}
