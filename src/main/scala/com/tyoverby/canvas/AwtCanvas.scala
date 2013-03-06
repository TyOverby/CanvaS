package com.tyoverby.canvas

import javax.swing.{JPanel, JFrame}
import java.awt.{Dimension, BorderLayout, Graphics}
import java.awt.image.BufferedImage


class AWTPane(parent: JFrame, img: BufferedImage) extends JPanel {

    def updateSize() {
        this.setSize(img.getWidth, img.getHeight)
    }

    override def paint(g: Graphics) {
        g.drawImage(img, 0, 0, img.getWidth, img.getHeight, null)
    }

    override def getPreferredSize: Dimension = {
        new Dimension(img.getWidth, img.getHeight)
    }
}

object AwtCanvas {
    private def createAndShowGui(img: BufferedImage, title: String) {
        val frame = new JFrame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
        frame.getContentPane.setLayout(new BorderLayout)
        frame.setTitle(title)

        val awtPane = new AWTPane(frame, img)
        frame.getContentPane.add(awtPane, BorderLayout.CENTER)
        frame.setResizable(false)
        frame.pack()
        frame.setVisible(true)
    }

    def showImage(img: BufferedImage, title: String) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            def run() {
                AwtCanvas.createAndShowGui(img, title)
            }
        })
    }
}
