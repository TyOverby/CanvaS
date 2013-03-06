package com.prealpha.canvas.components

import java.awt.{Color => AWTColor}
import com.prealpha.canvas.Color
import java.awt.image.BufferedImage


trait HigherOrder {
    var totalPoints: Seq[(Int, Int)]
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
}
