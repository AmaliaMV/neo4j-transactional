package com.example

import grails.compiler.GrailsCompileStatic
import grails.gorm.transactions.Transactional


@GrailsCompileStatic
@Transactional
class PetService {

    def changeName(List<Pet> pets) {
        log.info("[inicio] PetService.changeName(List<Pet> pets)")

        for (pet in pets.findAll { it.name == 'par' || it.name == 'tomy' }) {
            changeName(pet)
        }

        log.info("[fin] PetService.changeName(List<Pet> pets)")
    }

    def changeName(Pet pet) {
        log.info("[inicio] PetService.changeName(Pet pet)")
        pet.name == pet.name.toUpperCase()
        log.info("[fin] PetService.changeName(Pet pet)")
    }
}
