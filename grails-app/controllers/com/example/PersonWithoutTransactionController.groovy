package com.example

import grails.compiler.GrailsCompileStatic
import grails.validation.ValidationException

import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.OK

@GrailsCompileStatic
class PersonWithoutTransactionController {

    PersonService personService

    static responseFormats = ['json']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond personService.list(params), model: [personCount: personService.count()]
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
        }
        catch (ValidationException e) {
            respond person.errors, view: 'create'
            return
        }

        respond person, [status: CREATED, view: "show"]
    }

    def update() {
        log.debug('[START] update action')

        log.debug('[INICIO] - personService.get()')
        Person person = personService.get(params.id as Serializable)
        log.debug('[FIN] - personService.get()')

        if (person == null) {
            render status: NOT_FOUND
            return
        }

        log.debug('[INICIO] - binding')
        person.setProperties(getObjectToBind())
        log.debug('[FIN] - binding')

        try {
            log.debug('[INICIO] - personService.save()')
            personService.save(person)
            log.debug('[FIN] - personService.save()')
        }
        catch (ValidationException e) {
            respond person.errors, view: 'edit'
            return
        }

        log.debug('[END] update action')
        respond person, [status: OK, view: "show"]
    }

    def delete(Long id) {
        if (id == null) {
            render status: NOT_FOUND
            return
        }

        personService.delete(id)

        respond([status: OK], [message: 'Object deleted'])
    }

    protected getObjectToBind() {
        request
    }
}
