
package com.qa.gui

import com.qa.gui._
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene

/**
 * @author abutcher
 * 05/11/2015
 * An object that creates a frame that scenes are passed into
 */
object Main extends JFXApp {
    var poScene: PurchaseOrdersScene = new PurchaseOrdersScene
    stage = new PrimaryStage {
      title = "NB Gardens - Warehouse Order Tracking System"
      scene = poScene.getScene
    }
}