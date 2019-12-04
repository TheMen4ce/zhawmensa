package zhawmensa

class MenuPlan {

    int calendarWeek
    int year

    static belongsTo = [gastronomicFacility: GastronomicFacility]
    static hasMany = [menus: Menu]

    static constraints = {
    }
}
