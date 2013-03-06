package com.tyoverby.canvas

object Setting {

    def setting[A](deff: A)(ons: A => Unit) = {
        new Setting[A] {
            val default = deff

            def onSet(a: A) {
                ons(a)
            }
        }
    }
}

trait Setting[A] {
    def onSet(a: A)

    val default: A
    var value: A = default

    def :=(a: A) {
        value = a
        onSet(a)
    }

    def =:=(a: A) {
        value = a
    }
}
