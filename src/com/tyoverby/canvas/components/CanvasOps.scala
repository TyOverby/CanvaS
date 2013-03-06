package com.tyoverby.canvas.components

import java.awt.Graphics2D
import java.awt.image.BufferedImage
import java.awt.geom.AffineTransform

import utilities.copy
import com.tyoverby.canvas.Color

class OperationHolder(image: BufferedImage, graphics: Graphics2D) {
    // Affine Transforms apply their transformations
    // in reverse order.  Because of this, we have to do a hack with a list
    private[this] val transformer = new AffineTransform()

    // A list of all the operations.
    // An operation is a function that adds a
    // transformation to the AffineTransform.
    private[this] var operations = List[() => Unit]()

    var backgroundColor: Option[Color] = None

    def rotate(theta: Double) = {
        operations = {
            () => transformer.rotate(theta)
        } :: operations
        this
    }

    def rotate(theta: Double, xAnchor: Double, yAnchor: Double) = {
        operations = {
            () => transformer.rotate(theta, xAnchor, yAnchor)
        } :: operations
        this
    }

    def scale(scalar: Double) = {
        operations = {
            () => transformer.scale(scalar, scalar)
        } :: operations
        this
    }

    def scale(scaleX: Double, scaleY: Double) = {
        operations = {
            () => transformer.scale(scaleX, scaleY)
        } :: operations
        this
    }

    def translate(transX: Double, transY: Double) = {
        operations = {
            () => transformer.translate(transX, transY)
        } :: operations
        this
    }

    def shear(shearX: Double, shearY: Double) = {
        operations = {
            () => transformer.shear(shearX, shearY)
        } :: operations
        this
    }

    def withBackground(color: Color) = {
        backgroundColor = Some(color)
        this
    }

    def run() {
        // Copy the image for transforming
        val c = copy(image)
        // apply all of the functions in the opposite order in which they
        // were received
        operations.foreach(f => f())
        // Draw the image with the transform applied
        backgroundColor.foreach {
            c =>
                graphics.setColor(c)
                graphics.fillRect(0, 0, this.image.getWidth, image.getHeight)
        }
        graphics.drawImage(c, transformer, null)
    }
}

trait CanvasOps {
    var graphics: Graphics2D
    var imageBuffer: BufferedImage

    def chainedOps = new OperationHolder(imageBuffer, graphics)

    def scale(scaleX: Double, scaleY: Double, xAnchor: Int, yAnchor: Double) {
        chainedOps
            .translate(-xAnchor, -yAnchor)
            .scale(scaleX, scaleY)
            .translate(xAnchor, yAnchor)
    }

    def scale(scaleX: Double, scaleY: Double) {
        chainedOps.scale(scaleX, scaleY).run()
    }

    def scale(scalar: Double) {
        chainedOps.scale(scalar).run()
    }

    def translate(x:Double, y:Double){
        chainedOps.translate(x,y).run()
    }

    def rotate(scalar: Double) {
        chainedOps.rotate(scalar).run()
    }

    def rotate(scalar: Double, xAnchor: Double, yAnchor: Double) {
        chainedOps.rotate(scalar, xAnchor, yAnchor).run()
    }

    def shear(x: Double, y: Double) {
        chainedOps.shear(x, y).run()
    }

}
