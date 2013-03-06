package com.tyoverby.canvas

import components._
import java.awt.{Color => AWTColor}
import java.awt.image.BufferedImage
import collection.parallel.mutable.ParArray

class Canvas extends ImplicitDefs
with Settings
with IO
with GraphicsBuiltins
with HigherOrder
with CanvasOps
with Misc {
    private[this] var setBefore = false
    var imageBuffer = new BufferedImage(256, 256, BufferedImage.TYPE_INT_RGB)
    var graphics = imageBuffer.createGraphics()
    var totalPoints: ParArray[(Int, Int)] = computePoints()

    def reset() {
        setBefore = false
    }

    def setNewImage(newImage: BufferedImage) {
        val old = imageBuffer
        this.imageBuffer = newImage
        graphics = imageBuffer.createGraphics()
        totalPoints = computePoints()
        if (setBefore) {
            graphics.drawImage(old, 0, 0, null)
        }
        setBefore = true
    }

    def computePoints(): ParArray[(Int, Int)] = {
        val width = imageBuffer.getWidth
        val height = imageBuffer.getHeight
        val arr = new ParArray[(Int, Int)](width * height)

        for (y <- 0 until height; x <- 0 until width) {
            arr(x + y * width) = (x, y)
        }
        arr
    }

    private[this] var _color: Color = ((255, 255, 255))

    def color = _color

    def color_=(co: Color) {
        _color = co
        graphics.setColor(co)
    }
}

object MyCanvas extends Canvas with App {
    load("mario.jpg")

    def frag(f: Double, broken: Int) = {
       (f * broken + broken).toInt.toDouble / broken.toDouble
    }

    val hfrag = 6
    val sfrag = 4
    val vfrag = 4

    mapHsv {
        case HSVColor(h, s, v) => HSVColor(frag(h, hfrag), frag(s,sfrag), frag(v, vfrag))
    }

    show()
}
