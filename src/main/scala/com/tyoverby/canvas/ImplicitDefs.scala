package com.tyoverby.canvas

import java.awt.{Color => AwtColor}
import language.implicitConversions

class DegreeNumber(val degree: Double) extends AnyVal {
    def degrees = (degree * Math.PI / 180.0)
}

class RadialNumber(val degree: Double) extends AnyVal {
    def radians = degree
}

trait ImplicitDefs {
    implicit def tup3toColor(tup: (Int, Int, Int)) = Color.tupled(tup)

    implicit def intToColor(in: Int) = {
        Color(in, in, in)
    }

    implicit def old2new(old: AwtColor) = Color(old.getRed, old.getGreen, old.getBlue)

    def greyscale(in: Int) = Color(in % 256, in % 256, in % 256)

    implicit def setting2Actual[A](setting: Setting[A]) = setting.value

    implicit def num2DegreeNumber(num: Double) = new DegreeNumber(num)

    implicit def num2RadialNumber(num: Double) = new RadialNumber(num)

    implicit def color2HSV(c: Color) = {
        val fa = new Array[Float](3)
        AwtColor.RGBtoHSB(c.r, c.g, c.b, fa)
        HSVColor(fa(0), fa(1), fa(2))
    }

    implicit def HSV2Color(hsv: HSVColor): Color = {
        AwtColor.getHSBColor(hsv.h.toFloat,hsv.s.toFloat,hsv.v.toFloat)
    }
}
