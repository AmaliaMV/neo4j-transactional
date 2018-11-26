package com.example

import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class Pet {

    User createdBy

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
        log.info("Pet - afterLoad()")
        A.findByName('A')
    }
}
