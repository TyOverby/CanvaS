package com.prealpha.canvas

import javax.swing.{JPanel, JFrame}
import java.awt.{Dimension, BorderLayout, Graphics}


class AWTPane(parent: JFrame) extends JPanel {
    val c = new MyCanvas
    val img = c.imageBuffer

    def updateSize() {
        this.setSize(img.getWidth, img.getHeight)
    }

    override def paint(g: Graphics) {
        g.drawImage(img, 0, 0, img.getWidth, img.getHeight, null)
    }

    override def  getPreferredSize: Dimension = {
        new Dimension(img.getWidth, img.getHeight)
    }
}

object AwtCanvas extends App {
    def createAndShowGui() {
        val frame = new JFrame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
        frame.getContentPane.setLayout(new BorderLayout)

        val awtPane = new AWTPane(frame)
        frame.getContentPane.add(awtPane, BorderLayout.CENTER)
        frame.setResizable(false)
        frame.pack()
        frame.setVisible(true)
    }

    javax.swing.SwingUtilities.invokeLater(new Runnable() {
        def run() {
            AwtCanvas.createAndShowGui()
        }
    })
}
