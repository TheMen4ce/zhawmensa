package zhawmensa

class UrlMappings {

    static mappings = {
        delete "/$controller/$id(.$format)?"(action:"delete")
        get "/$controller(.$format)?"(action:"index")
        get "/$controller/$id(.$format)?"(action:"show")
        post "/$controller(.$format)?"(action:"save")
        put "/$controller/$id(.$format)?"(action:"update")
        patch "/$controller/$id(.$format)?"(action:"patch")

        get "/menuPlan/$id/menus"(controller: 'menuPlan', action:'findAllMenus')

        post "/user/changeUsername"(controller: 'user', action:'changeUsername')
        post "/user/changePassword"(controller: 'user', action:'changePassword')

        "500"(view: '/error')
        "404"(view: '/notFound')
    }
}
