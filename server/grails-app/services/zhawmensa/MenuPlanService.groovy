package zhawmensa

import grails.gorm.transactions.Transactional

@Transactional
class MenuPlanService {

    List<MenuPlan> findAllByFacilityId(long facilityId) {
        return MenuPlan.createCriteria().list{
            gastronomicFacility{
                eq('id', facilityId)
            }
            order('year', 'desc')
            order('calendarWeek', 'desc')
        } as List<MenuPlan>
    }
}
