package com.example

import grails.plugins.rest.client.RestBuilder
import grails.testing.mixin.integration.Integration
import grails.transaction.Rollback

import geb.spock.GebSpec

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR

@Integration
@Rollback
class Person2FunctionalSpec extends GebSpec {

    RestBuilder getRestBuilder() {
        new RestBuilder()
    }

    void setup() {
        if (!Person.findByName('Tomy')) {
            new Person(name: 'Tomy').save(flush:true, failOnError:true)
        }
    }

    void "Test the delete action with Transaction Notation"() {
        when:
        def id = Person.findByName('Tomy').id
        def response = restBuilder.delete("${baseUrl}/person/$id")

        then:"you should have an error"
        response.status == INTERNAL_SERVER_ERROR.value() // <-- this fail
    }


    void "Test the delete action without Transaction Notation"() {
        when:
        def id = Person.findByName('Tomy').id
        def response = restBuilder.delete("${baseUrl}/personWithoutTransaction/$id")

        then:"you should have an error"
        response.status == INTERNAL_SERVER_ERROR.value()
    }
}