package zhawmensa

class BootStrap {

    def init = { servletContext ->
        if (GastronomicFacility.findAll().size() == 0) {
            GastronomicFacility.withNewTransaction {
                GastronomicFacility.saveAll(
                        new GastronomicFacility(name: "Cafeteria Bibliothek", locationId: 7900, provider: Provider.SV),
                        new GastronomicFacility(name: "Cafeteria Eulachpassage", locationId: 7901, provider: Provider.SV),
                        new GastronomicFacility(name: "Mensa Grüental", locationId: 7902, provider: Provider.SV),
                        new GastronomicFacility(name: "Cafeteria Mäander", locationId: 7903, provider: Provider.SV),
                )
            }
        }

    }
    def destroy = {
    }
}
