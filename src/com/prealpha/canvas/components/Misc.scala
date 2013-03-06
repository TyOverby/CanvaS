package com.prealpha.canvas.components

import java.awt.Graphics2D
import java.awt.image.BufferedImage


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
}
