package zhawmensa.security

import grails.gorm.transactions.Transactional
import grails.plugin.springsecurity.SpringSecurityService
import zhawmensa.exceptions.BusinessException
import zhawmensa.exceptions.ObjectNotFoundException

@Transactional
class UserService {

    SpringSecurityService springSecurityService

    void updateUsername(String oldUsername, String newUsername) {
        AppUser user = findUserByUsername(oldUsername)
        user.username = newUsername
        user.save()
    }

    void updatePassword(String username, String oldPassword, String newPassword) {
        AppUser user = findUserByUsername(username)
        if (springSecurityService.passwordEncoder.matches(oldPassword, user.password)) {
            user.password = newPassword
            user.save()
        } else {
            throw new BusinessException("Old password is wrong!")
        }
    }

    private AppUser findUserByUsername(String oldUsername) {
        AppUser user = AppUser.findByUsername(oldUsername)
        if (!user) {
            throw new ObjectNotFoundException("No user with name ${oldUsername} found!")
        }
        return user
    }
}
