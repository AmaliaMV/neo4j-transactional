package com.example

import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class Pet {

    User createdBy

    static transients = ['aa']

    A aa

    String name

    static belongsTo = [owner: Person]

    static constraints = {
        name unique: true
        createdBy nullable: true
    }

    void beforeInsert() {
        log.info("Pet - beforeInsert()")
    }
    
    void afterLoad() {
        BindingHelper.withNoAutoFlush {
            aa = A.findByName('A')
        }
    }
}
