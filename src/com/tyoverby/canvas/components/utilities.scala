package com.tyoverby.canvas.components

import java.awt.image.BufferedImage

package object utilities {
    def copy(from: BufferedImage): BufferedImage = {
        val to = new BufferedImage(from.getWidth, from.getHeight, BufferedImage.TYPE_INT_RGB)
        val toG = to.createGraphics
        toG.drawImage(from,0,0,null)
        to
    }
}
