package com.prealpha.canvas.components

import javax.imageio.ImageIO
import java.io.File
import java.net.URL
import java.awt.image.BufferedImage
import com.prealpha.canvas.Setting

trait IO {
    var imageBuffer: BufferedImage
    def setNewImage(newImage: BufferedImage)

    var width: Int
    var height: Int

    def writeToFile(path: String) {
        val allTypes = ImageIO.getWriterFormatNames
        val fileType = allTypes.find(ff => path.endsWith("."+ff))
        val (file, ft) = if (fileType.isDefined){
            (new File(path), fileType.get)
        }
        else{
            (new File(path+".png"), "png")
        }
        ImageIO.write(imageBuffer, ft, file)
    }

    def loadUrl(url: String) {
        val newBuff = ImageIO.read(new URL(url))
        setNewImage(newBuff)

        width = imageBuffer.getWidth
        height = imageBuffer.getHeight
    }

    def loadFile(file: String) {
        val newBuff = ImageIO.read(new File(file))
        setNewImage(newBuff)

        width = imageBuffer.getWidth
        height = imageBuffer.getHeight
    }

    def load(toLoad: String) {
        if (toLoad.startsWith("http://") || toLoad.startsWith("https://")) {
            loadUrl(toLoad)
        }
        else {
            loadFile(toLoad)
        }
    }
}
