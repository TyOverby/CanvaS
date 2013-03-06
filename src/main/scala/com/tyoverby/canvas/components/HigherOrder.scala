package com.tyoverby.canvas.components

import java.awt.{Color => AWTColor}
import com.tyoverby.canvas.{ImplicitDefs, HSVColor, Color}
import java.awt.image.BufferedImage
import collection.parallel.mutable.ParArray


trait HigherOrder extends ImplicitDefs{

    // TODO: Add parMap to both position and pixel maps
    var totalPoints: ParArray[(Int, Int)]
    var imageBuffer: BufferedImage

    def putPixel(x: Int, y: Int, color: Color)


    def map(f: (Int, Int) => Color) {
        this.totalPoints.foreach {
            p =>
                val (x, y) = p
                putPixel(x, y, f(x, y))
        }
    }

    def mapPosition(f: (Int, Int) => Color) {
        map(f)
    }

    def map(f: Color => Color) {
        this.totalPoints.foreach {
            p =>
                val (x, y) = p
                val c = new AWTColor(imageBuffer.getRGB(x, y))
                putPixel(x, y, f(Color(c.getRed, c.getGreen, c.getBlue)))
        }
    }

    def mapColor(f: Color => Color) {
        map(f)
    }

    def mapHsv(f: HSVColor => Color){
        this.totalPoints.foreach {
            p =>
                val (x, y) = p
                val c = new AWTColor(imageBuffer.getRGB(x, y))
                putPixel(x, y, f(Color(c.getRed, c.getGreen, c.getBlue)))
        }
    }
}
