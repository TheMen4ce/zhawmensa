package zhawmensa

class GastronomicFacility {

    int locationId
    String name

    static hasMany = [menuPlans: MenuPlan]

    static constraints = {
    }
}
