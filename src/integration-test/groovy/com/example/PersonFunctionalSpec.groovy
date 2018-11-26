package com.example

import grails.testing.mixin.integration.Integration
import grails.transaction.Rollback


import static org.springframework.http.HttpStatus.*
import geb.spock.*
import grails.plugins.rest.client.RestBuilder

@Integration
@Rollback
class PersonFunctionalSpec extends GebSpec {

    RestBuilder getRestBuilder() {
        new RestBuilder()
    }

    void setup() {
        A a = new A(name: 'A').save(flush: true, failOnError: true)
        Pet pet1 = new Pet(name: 'Pet 1')

        new Person(name: 'Person 1')
            .addToPets(pet1)
            .save(flush: true, failOnError: true)

    }

    void "Test update action without Transaction Notation"() {
        when:
        println "\n[START] test\n"
        def id = Person.findByName('Person 1').id
        def response = restBuilder.put("${baseUrl}/personWithoutTransaction/$id", {
            json([
                pets: [
                    [
                        name: "Pet 2"
                    ]
                ]
            ])
        })

        then: "you should have save it"
        response.status == OK.value()  // <-- this fail
        response.json.pets.size() == 1
    }

}