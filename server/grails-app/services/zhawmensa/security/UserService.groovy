package zhawmensa.security

import grails.gorm.transactions.Transactional
import zhawmensa.exceptions.ObjectNotFoundException

@Transactional
class UserService {

    void updateUsername(String oldUsername, String newUsername) {
        AppUser user = AppUser.findByUsername(oldUsername)
        if (!user) {
            throw new ObjectNotFoundException("No user with name ${oldUsername} found!")
        }
        user.username = newUsername
        user.save()
    }
}
