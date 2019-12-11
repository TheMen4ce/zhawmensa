package zhawmensa

class Menu {

    BigDecimal externalPrice
    BigDecimal internalPrice
    BigDecimal studentPrice
    Date date
    String title
    String label
    String sideDishes

    static belongsTo = [menuPlan: MenuPlan]

    static constraints = {
    }
}
