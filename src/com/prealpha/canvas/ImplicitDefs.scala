package com.prealpha.canvas

import java.awt.{Color => AwtColor}

trait ImplicitDefs {
    implicit def tup3toColor(tup: (Int, Int, Int)) = Color.tupled(tup)

    implicit def intToColor(in: Int) = {
        Color(in, in, in)
    }

    def greyscale(in: Int) = Color(in % 256, in % 256, in % 256)

    implicit def setting2Actual[A](setting: Setting[A]) = setting.sett
}
