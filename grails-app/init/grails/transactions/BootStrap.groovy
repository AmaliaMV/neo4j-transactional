package grails.transactions

import com.example.A
import com.example.Person
import com.example.Pet

class BootStrap {

    def init = { servletContext ->

        Person.withNewSession {
            Person.withTransaction {

//                A a = A.findByName('A') ?: new A(name: 'A').save(flush:true, failOnError:true)
//
//                Pet pet1 = Pet.findByName('Pet 1') ?: new Pet(name: 'Pet 1')
//
//                Person person1 = Person.findByName('Person 1')
//
//                if (!person1) {
//                    person1 = new Person(name: 'Person 1')
//                        .addToPets(pet1)
//                        .save(flush:true, failOnError:true)
//                }
            }
        }

    }
    def destroy = {
    }
}
