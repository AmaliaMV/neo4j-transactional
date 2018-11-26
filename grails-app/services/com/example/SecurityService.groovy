package com.example

import grails.compiler.GrailsCompileStatic
import grails.gorm.transactions.Transactional

@Transactional
@GrailsCompileStatic
class SecurityService {

    @Transactional(readOnly = true)
    User getCurrentUser() {
        log.debug("[START] SecurityService.getCurrentUser()")
        User user = User.findByName('admin')
        log.info("[END] SecurityService.getCurrentUser()")
        return user
    }
}
