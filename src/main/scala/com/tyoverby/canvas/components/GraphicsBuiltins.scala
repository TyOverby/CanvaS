package com.tyoverby.canvas.components

import com.tyoverby.canvas.{Setting, ImplicitDefs, Color}
import java.awt.{Graphics2D, Color => AWTColor}
import java.awt.image.BufferedImage

trait GraphicsBuiltins extends ImplicitDefs {
    var graphics: Graphics2D
    var imageBuffer: BufferedImage
    def color: Color
    def color_= (c: Color)

    def width: Int
    def height: Int

    def clear(color: Color) {
        val prev: Color = this.color
        this.color = color
        fillRect(0, 0, width, height)
        this.color = prev
    }

    def clear() {
        clear(Color(255, 255, 255))
    }

    val drawLine = graphics.drawLine(_, _, _, _)

    val drawRect = graphics.drawRect(_, _, _, _)
    val fillRect = graphics.fillRect(_, _, _, _)

    val drawArc = graphics.drawArc(_, _, _, _, _, _)
    val fillArc = graphics.fillArc(_, _, _, _, _, _)

    val drawOval = graphics.drawOval(_, _, _, _)
    val fillOval = graphics.fillOval(_, _, _, _)

    val drawRoundRect = graphics.drawRoundRect(_, _, _, _, _, _)
    val fillRoundRect = graphics.fillRoundRect(_, _, _, _, _, _)

    val drawString = graphics.drawString(_: String, _: Float, _: Float)

    def drawPolyLine(points: (Int, Int)*) {
        val xPoints = points.map(_._1)
        val yPoints = points.map(_._2)
        val n = points.length
        graphics.drawPolyline(xPoints.toArray, yPoints.toArray, n)
    }

    def putPixel(x: Int, y: Int, color: Color) {
        imageBuffer.setRGB(x, y, color.getRGB)
    }

    def colorAt(x: Int, y: Int): Color = {
        new AWTColor(imageBuffer.getRGB(x,y))
    }
}
