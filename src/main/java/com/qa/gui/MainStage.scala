package com.qa.gui

import com.qa.gui._
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.layout.GridPane
import javafx.geometry.Insets
import scalafx.scene.text._
import scalafx.scene.control._
import scalafx.event.ActionEvent
import scalafx.event.EventHandler
import scalafx.Includes._
import scalafx.scene.layout.BorderPane
import scalafx.scene.layout.VBox

/**
 * @author abutcher
 */
class MainStage(stage: PrimaryStage) {
  stage.setTitle("LOG IN")
  val label: Label = new Label("Purchase Orders")
  val border: BorderPane = new BorderPane()
  val poB: Button = new Button("Purchase Orders")
  val coB: Button = new Button("Customer Orders")

  val sideBar:VBox = new VBox
    sideBar.setPadding(new Insets(10));
    sideBar.setSpacing(8);
  
  poB.onAction = { ae: ActionEvent => border.center = new PurchaseOrderStage(stage).createPane }
  coB.onAction = { ae: ActionEvent => border.center = new CustomerOrderStage(stage).createPane }
  
  sideBar.children.add(poB)
  sideBar.children.add(coB)
  
  def createScene: Scene = {

    var scene: Scene = new Scene {

      border setTop (label)
      border setCenter (new PurchaseOrderStage(stage).createPane)
      content.addAll(border)
      border.setLeft(sideBar)
    }
    scene
  }

  stage.setScene(createScene)
}
