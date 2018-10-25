package com.example

import grails.validation.ValidationException

import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.OK

class PersonWithoutTransactionController {

    PersonService personService

    static responseFormats = ['json']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond personService.list(params), model:[personCount: personService.count()]
    }

    def show(Long id) {
        respond personService.get(id)
    }

    def save(Person person) {
        if (person == null) {
            render status: NOT_FOUND
            return
        }

        try {
            personService.save(person)
        } catch (ValidationException e) {
            respond person.errors, view:'create'
            return
        }

        respond person, [status: CREATED, view:"show"]
    }

    def update(Person person) {
        if (person == null) {
            render status: NOT_FOUND
            return
        }

        try {
            personService.save(person)
        } catch (ValidationException e) {
            respond person.errors, view:'edit'
            return
        }

        respond person, [status: OK, view:"show"]
    }

    def delete(Long id) {
        if (id == null) {
            render status: NOT_FOUND
            return
        }

        personService.delete(id)

        respond ([status: OK], [message: 'Object deleted'])
    }
}
