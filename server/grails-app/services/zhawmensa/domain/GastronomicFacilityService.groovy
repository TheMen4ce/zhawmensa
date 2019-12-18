package zhawmensa.domain

import grails.gorm.transactions.Transactional
import zhawmensa.GastronomicFacility
import zhawmensa.I18nService
import zhawmensa.exceptions.ObjectNotFoundException

@Transactional
class GastronomicFacilityService {

    I18nService i18nService

    GastronomicFacility store(GastronomicFacility facility) {
        return facility.save()
    }

    GastronomicFacility findById(long id) {
        GastronomicFacility facility = GastronomicFacility.findById(id)
        if (!facility) {
            throw new ObjectNotFoundException(i18nService.getMessage("facility.error.notFound", [id]))
        }
        return facility
    }

    List<GastronomicFacility> findAll() {
        return GastronomicFacility.findAll()
    }

    void deleteById(long id) {
        GastronomicFacility facilityToDelete = findById(id)
        facilityToDelete.delete()
    }
}
