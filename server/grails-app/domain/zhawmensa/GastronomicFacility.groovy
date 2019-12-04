package zhawmensa

class GastronomicFacility {

    int locationId
    String name
    Provider provider

    static hasMany = [menuPlans: MenuPlan]

    static mapping = {
        provider enumType: 'string'
    }

    static constraints = {
    }
}
