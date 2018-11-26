package com.example

import javax.persistence.FlushModeType

import grails.compiler.GrailsCompileStatic
import grails.databinding.DataBindingSource
import grails.web.databinding.DataBindingUtils

import org.grails.datastore.mapping.core.AbstractSession

@GrailsCompileStatic
class BindingHelper {

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
