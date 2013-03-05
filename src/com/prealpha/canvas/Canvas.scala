package com.prealpha.canvas

import java.awt.{Color => AWTColor}
import java.awt.image.BufferedImage
import Setting.setting
import javax.imageio.ImageIO
import java.io.File
import java.net.URL

class Canvas extends ImplicitDefs {
    var imageBuffer = new BufferedImage(256, 256, BufferedImage.TYPE_INT_RGB)
    var graphics = imageBuffer.createGraphics()
    var totalPoints = computePoints()

    def setNewImage(newImage: BufferedImage) {
        this.imageBuffer = newImage
        graphics = imageBuffer.createGraphics()
        totalPoints = computePoints()
    }

    def computePoints(): Seq[(Int, Int)] = for {
        y <- (0 until imageBuffer.getHeight)
        x <- (0 until imageBuffer.getWidth)
    } yield (x, y)


    val width = setting(50) {
        width =>
            val oldImage = imageBuffer
            val newImage = new BufferedImage(width, oldImage.getHeight, BufferedImage.TYPE_INT_RGB)
            setNewImage(newImage)
    }

    val height = setting(50) {
        height =>
            val oldImage = imageBuffer
            val newImage = new BufferedImage(oldImage.getWidth, height, BufferedImage.TYPE_INT_RGB)
            setNewImage(newImage)
    }

    def loadUrl(url: String) {
        val newBuff = ImageIO.read(new URL(url))
        setNewImage(newBuff)
    }

    def loadFile(file: String) {
        val newBuff = ImageIO.read(new File(file))
        setNewImage(newBuff)
    }

    def load(toLoad: String) {
        if (toLoad.startsWith("http://") || toLoad.startsWith("https://")) {
            loadUrl(toLoad)
        }
        else {
            loadFile(toLoad)
        }
    }

    private[this] var _color: Color = ((255, 255, 255))

    def color = _color

    def color_=(co: Color) {
        _color = co
        graphics.setColor(co)
    }

    def clear(color: Color) {
        val prev: Color = this.color
        this.color = color
        fillRect(0, 0, width, height)
        this.color = prev
    }

    def clear() {
        clear((255, 255, 255))
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

    def drawPolyLine(points: (Int, Int)*) {
        val xPoints = points.map(_._1)
        val yPoints = points.map(_._2)
        val n = points.length
        graphics.drawPolyline(xPoints.toArray, yPoints.toArray, n)
    }

    def putPixel(x: Int, y: Int, color: Color) {
        imageBuffer.setRGB(x, y, color.getRGB)
    }

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

    def map(f: (Int, Int, Int) => Color) {
        this.totalPoints.foreach {
            p =>
                val (x, y) = p
                val c = new AWTColor(imageBuffer.getRGB(x, y))
                putPixel(x, y, f(c.getRed, c.getGreen, c.getBlue))
        }
    }

    def mapColor(f: (Int, Int, Int) => Color) {
        map(f)
    }

    val drawString = graphics.drawString(_: String, _: Float, _: Float)
}

class MyCanvas extends Canvas {
    width := 256
    height := 256

    load("ty.jpg")

    def piecewise(r: Int, g: Int, b: Int): Color = {
        (r, g, b) match {
            case _ if r > g && r > b => Color(255, 0, 0)
            case _ if g > r && g > b => Color(0, 255, 0)
            case _ if b > r && b > g => Color(0, 0, 255)
            case _ => Color(0, 0, 0)
        }
    }

    mapColor(piecewise)
}
