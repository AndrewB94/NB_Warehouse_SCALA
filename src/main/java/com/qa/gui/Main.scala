package com.qa.gui

import com.qa.gui.PurchaseOrdersScene
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage

import scalafx.scene.Scene



object Main extends JFXApp {
    var poScene: PurchaseOrdersScene = new PurchaseOrdersScene
    stage = new PrimaryStage {
      title = "NB Gardens - Warehouse Order Tracking System"
      scene = poScene.getScene
    }
}