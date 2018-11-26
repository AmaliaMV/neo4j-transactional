package com.example

import grails.compiler.GrailsCompileStatic
import grails.databinding.BindUsing
import grails.databinding.DataBindingSource

@GrailsCompileStatic
class Person {

    User createdBy

    String name

    List<Pet> pets = []

    static hasMany = [pets: Pet]

    static constraints = {
        name unique: true
        createdBy nullable: true
    }

    void beforeDelete() {
//        throw new RuntimeException('just for test')
    }

}
