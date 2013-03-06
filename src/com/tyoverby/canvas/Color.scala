package com.tyoverby.canvas

import java.awt.{Color => AWTColor}


case class Color(r: Int, g: Int, b: Int) extends AWTColor(r, g, b)
