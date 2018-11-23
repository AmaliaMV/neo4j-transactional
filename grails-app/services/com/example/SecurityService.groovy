package com.example

import grails.compiler.GrailsCompileStatic
import grails.gorm.transactions.Transactional

@Transactional
@GrailsCompileStatic
class SecurityService {

    @Transactional(readOnly = true)
    User getCurrentUser() {
        log.info("[inicio] SecurityService.getCurrentUser()")
        User user = User.findByName('admin')
        log.info("[fin] SecurityService.getCurrentUser()")
        return
    }
}
