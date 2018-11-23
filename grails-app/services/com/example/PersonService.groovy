package com.example

import grails.gorm.transactions.Transactional

@Transactional
class PersonService {

    PetService petService

    Person get(Serializable id) {
        Person.get(id)
    }

    List<Person> list(Map args) {
        Person.list(args)
    }

    Long count() {
        Person.count()
    }

    void delete(Serializable id) {
        Person.get(id)?.delete()
    }

    Person save(Person person) {
        Person personGuadada
        log.info("[inicio] PersonService.save()")
        petService.changeName(person.pets)

        personGuadada = person.save()

        log.info("[fin] PersonService.save()")

        return personGuadada
    }

}