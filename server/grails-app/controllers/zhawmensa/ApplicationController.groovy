package zhawmensa

import grails.core.GrailsApplication
import grails.plugin.springsecurity.annotation.Secured
import grails.plugins.*

class ApplicationController implements PluginManagerAware {

    GrailsApplication grailsApplication
    GrailsPluginManager pluginManager

    @Secured("IS_AUTHENTICATED_ANONYMOUSLY")
    def index() {
        [grailsApplication: grailsApplication, pluginManager: pluginManager]
    }
}
