package com.tyoverby.canvas.components

import java.awt.Graphics2D
import java.awt.image.BufferedImage
import com.tyoverby.canvas.AwtCanvas
import utilities.copy


trait Misc extends CanvasOps{
    var graphics: Graphics2D
    var imageBuffer: BufferedImage

    def width: Int
    def width_=(w: Int)

    def height: Int
    def height_=(w: Int)

    def crop(x: Int, y: Int, w: Int, h: Int){
        translate(-x,-y)
        width = w
        height = h
    }

    private[this] var titleNum = 0

    def show(){
        show(f"image ${titleNum}")
        titleNum += 1
    }
    def show(title: String){
        AwtCanvas.showImage(copy(imageBuffer), title)
    }
}
