package com.qa.gui

import com.qa.gui._
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.control._
import scalafx.scene.shape.Rectangle
import scalafx.scene.Group
import scalafx.scene.text.Text;

/**
 * @author abutcher
 * 05/11/2015
 * An object that creates a frame that scenes are passed into
 */
object Main extends JFXApp {
  var poScene: PurchaseOrdersScene = new PurchaseOrdersScene
  var coScene: CustomerOrdersScene = new CustomerOrdersScene

   stage = new PrimaryStage()
  val CustomerOrderStage = new CustomerOrderStage(stage)


}