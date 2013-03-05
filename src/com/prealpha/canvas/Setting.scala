package com.prealpha.canvas

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
    var sett: A = default
    var alreadySet = false
    val setOnlyOnce = true

    def :=(a: A) {
        if (!alreadySet || !setOnlyOnce) {
            sett = a
            onSet(a)
            alreadySet = true
        }
    }
}
