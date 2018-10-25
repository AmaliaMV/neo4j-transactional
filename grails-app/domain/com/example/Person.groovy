package com.example

class Person {

    String name

    static constraints = {
        name unique: true
    }

    void beforeDelete() {
        throw new RuntimeException('just for test')
    }
}
