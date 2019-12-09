package zhawmensa

class ImportController implements ExceptionHandlingController {
	static responseFormats = ['text']

    MenuImportService menuImportService

    def index() {
        int importedCount = menuImportService.importMenuPlans()
        if(importedCount){
            render("Imported ${importedCount} menus!")
        }else{
            render("nothing to import")
        }
    }
}
