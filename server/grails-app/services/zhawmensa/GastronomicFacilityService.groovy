package zhawmensa

import grails.gorm.transactions.Transactional

@Transactional
class GastronomicFacilityService {

    GastronomicFacility store(GastronomicFacility facility) {
        return facility.save()
    }

    GastronomicFacility findById(long id) {
        return GastronomicFacility.findById(id)
    }

    List<GastronomicFacility> findAll() {
        return GastronomicFacility.findAll()
    }

    boolean deleteById(long id) {
        GastronomicFacility facilityToDelete = GastronomicFacility.findById(id)
        if (facilityToDelete) {
            facilityToDelete.delete()
            return true
        } else {
            return false
        }
    }
}
