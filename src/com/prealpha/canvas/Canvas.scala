package com.prealpha.canvas

import components._
import java.awt.{Color => AWTColor}
import java.awt.image.BufferedImage

class Canvas extends ImplicitDefs
with Settings
with IO
with GraphicsBuiltins
with HigherOrder
with CanvasOps {
    private[this] var setBefore = false
    var imageBuffer = new BufferedImage(256, 256, BufferedImage.TYPE_INT_RGB)
    var graphics = imageBuffer.createGraphics()
    var totalPoints = computePoints()

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

    def computePoints(): Seq[(Int, Int)] = for {
        y <- (0 until imageBuffer.getHeight)
        x <- (0 until imageBuffer.getWidth)
    } yield (x, y)

    private[this] var _color: Color = ((255, 255, 255))

    def color = _color

    def color_=(co: Color) {
        _color = co
        graphics.setColor(co)
    }
}

class MyCanvas extends Canvas {
    load("ty.jpg")

    scale(0.5)

    width /= 2
    height /= 2

    mapColor {
        case Color(r, g, b) => (b, r, g)
    }

    //shear(0,0.5)
}
