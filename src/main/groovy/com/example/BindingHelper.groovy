package com.example

import javax.persistence.FlushModeType

import grails.compiler.GrailsCompileStatic
import grails.databinding.DataBindingSource
import grails.web.databinding.DataBindingUtils

import org.grails.datastore.mapping.core.AbstractSession

@GrailsCompileStatic
class BindingHelper {

    static List<Pet> bindingPets(Person person, DataBindingSource source) {
        Collection<Map> pets = (Collection<Map>) source['pets']
        List ids = []
        for (Map pet in pets) {
            if (pet.id == null) {
                Pet target = new Pet()
                DataBindingUtils.bindObjectToInstance(target, pet)
                person.markDirty('pets')
                person.addToPets(pet)
            }
            else {
                ids.add(pet.id)
            }
        }

        return person.pets
    }

    static void withNoAutoFlush(Closure closure) {
        Person.withSession { AbstractSession session ->
            if (session.flushMode == FlushModeType.COMMIT) {
                closure.call()
            }
            else {
                session.flushMode = FlushModeType.COMMIT
                closure.call()
                session.flushMode = FlushModeType.AUTO
            }
        }
    }
}
