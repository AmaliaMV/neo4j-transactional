package com.example

import grails.gorm.transactions.Transactional

@Transactional
class PersonService {

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
        person.save()
    }

}