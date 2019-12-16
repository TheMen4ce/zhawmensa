package zhawmensa.domain

import grails.gorm.transactions.Transactional
import zhawmensa.GastronomicFacility
import zhawmensa.exceptions.ObjectNotFoundException

@Transactional
class GastronomicFacilityService {

    GastronomicFacility store(GastronomicFacility facility) {
        return facility.save()
    }

    GastronomicFacility findById(long id) {
        GastronomicFacility facility = GastronomicFacility.findById(id)
        if (!facility) {
            throw new ObjectNotFoundException("No facility found with id ${id}")
        }
        return facility
    }

    List<GastronomicFacility> findAll() {
        return GastronomicFacility.findAll()
    }

    void deleteById(long id) {
        GastronomicFacility facilityToDelete = GastronomicFacility.findById(id)
        if (!facilityToDelete) {
            throw new ObjectNotFoundException("No facility found with id ${id}")
        }
        facilityToDelete.delete()
    }
}
