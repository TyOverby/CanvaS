package com.tyoverby.canvas

import components._
import java.awt.{Color => AWTColor}
import java.awt.image.BufferedImage
import collection.parallel.mutable.ParArray

class Canvas extends App
with ImplicitDefs
with Settings
with IO
with GraphicsBuiltins
with HigherOrder
with CanvasOps
with Misc{
    private[this] var setBefore = false
    var imageBuffer = new BufferedImage(256, 256, BufferedImage.TYPE_INT_RGB)
    var graphics = imageBuffer.createGraphics()
    var totalPoints: ParArray[(Int, Int)] = computePoints()

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

        for (y <- 0 until height;  x <- 0 until width) {
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

object MyCanvas extends Canvas {
    load("example_input/trees.jpg")

    show("unscaled")

    scale(0.5)
    width /= 2
    height /= 2

    show("scaled")
}
