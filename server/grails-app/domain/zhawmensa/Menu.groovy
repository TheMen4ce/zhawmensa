package zhawmensa

class Menu {

    BigDecimal externalPrice
    BigDecimal internalPrice
    BigDecimal partnerPrice
    String label
    String name
    String sideDishes


    static belongsTo = [menuPlan: MenuPlan]

    static constraints = {
    }
}
