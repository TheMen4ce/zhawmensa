package zhawmensa

import grails.util.Environment
import zhawmensa.security.AppUser
import zhawmensa.security.Role
import zhawmensa.security.AppUserRole

class BootStrap {

    def init = { servletContext ->

        if (AppUser.findAll().size() == 0) {
            Role adminRole = new Role(authority: 'ROLE_ADMIN').save()
            AppUser adminUser = new AppUser(username: 'admin', password: 'admin').save()

            AppUserRole.withTransaction {
                AppUserRole.create(adminUser, adminRole)
            }
        }

        if (Environment.current != Environment.TEST) {
            createFacilitiesIfEmpty()
        }
    }

    private void createFacilitiesIfEmpty() {
        if (GastronomicFacility.findAll().size() == 0) {
            GastronomicFacility.withNewTransaction {
                GastronomicFacility.saveAll(
                        new GastronomicFacility(name: "Cafeteria Bibliothek", locationId: 7900, provider: Provider.SV),
                        // currently the SV service returns an error
                        // new GastronomicFacility(name: "Cafeteria Eulachpassage", locationId: 7901, provider: Provider.SV),
                        new GastronomicFacility(name: "Mensa Grüental", locationId: 7902, provider: Provider.SV),
                        new GastronomicFacility(name: "Cafeteria Mäander", locationId: 7903, provider: Provider.SV),
                        new GastronomicFacility(name: "Cafeteria Mäander", locationId: 7903, provider: Provider.SV),
                        new GastronomicFacility(name: "Mensa St.Georgenplatz", locationId: 7904, provider: Provider.SV),
                        new GastronomicFacility(name: "Mensa Technikum", locationId: 7905, provider: Provider.SV),
                        new GastronomicFacility(name: "Mensa Tössfeld ", locationId: 7906, provider: Provider.SV),
                        new GastronomicFacility(name: "Mensa Vista ", locationId: 7907, provider: Provider.SV),
                        new GastronomicFacility(name: "Mensa Pädagogische Hochschule Zürich", locationId: 303, provider: Provider.ZFV),
                )
            }
        }
    }

    def destroy = {
    }
}
