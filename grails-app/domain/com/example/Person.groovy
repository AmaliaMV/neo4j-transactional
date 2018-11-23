package com.example

import org.springframework.validation.BindingResult

import grails.compiler.GrailsCompileStatic
import grails.databinding.BindUsing
import grails.databinding.DataBindingSource
import grails.web.databinding.DataBindingUtils

@GrailsCompileStatic
class Person {

    User createdBy

    String name

    @BindUsing({ Person person, DataBindingSource source ->
        log.info('binding pets... ')
        return BindingHelper.bindingPets(person, source)
    })
    List<Pet> pets = []
//
//    @BindUsing({ Person person, DataBindingSource source ->
//
//        println "a buscado"
//
//        String plate = source["car"]["plate"] as String
//        Car car = Car.findByPlate(plate) ?: new Car(plate: plate).save()
//
//        return car
//    })
    Car car

    static hasMany = [pets: Pet]

    static constraints = {
        name unique: true
        createdBy nullable: true
    }

    void beforeDelete() {
        throw new RuntimeException('just for test')
    }

}
