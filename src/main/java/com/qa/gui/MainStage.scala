package com.qa.gui

import com.qa.gui._
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.layout.GridPane
import javafx.geometry.Insets
import scalafx.scene.text._
import scalafx.scene.control._
import scalafx.event.ActionEvent
import scalafx.Includes._
import scalafx.scene.layout.BorderPane
import scalafx.scene.layout.VBox
import scalafx.scene.paint.Color

/**
 * @author abutcher
 * Class that surrounds the content
 */
class MainStage(stage: PrimaryStage, employee: String) {
  /**
   * initialize values
   */
  val titleS: String = "NB Gardens - Warehouse Order Tracking System - "
  stage.setTitle(titleS + "Purchase Orders")
  val label: Label = new Label("Purchase Orders")
  label.setFont(Font.font("Verdana", 30))
  val border: BorderPane = new BorderPane()
  val poB: Button = new Button("Purchase Orders")
  val coB: Button = new Button("Customer Orders")
  val logOB: Button = new Button("Logout")

  logOB.prefWidth = 300
  coB.prefWidth = 300
  poB.prefWidth = 300
  
  /**
   * Initialize the sidebar
   */
  val sideBar: VBox = new VBox
  sideBar.setPadding(new Insets(10));
  sideBar.setSpacing(8);

  /**
   * set up action events for buttons
   */
  poB.onAction = { ae: ActionEvent =>
    stage.setTitle(titleS + "Purchase Orders")
    label.setText("Purchase Orders")
    new PurchaseOrderStage(stage, employee, border).createPane(true)
  }
  coB.onAction = { ae: ActionEvent =>
    stage.setTitle(titleS + "Customer Orders")
    label.setText("Customer Orders")
    new CustomerOrderStage(stage, employee, border).createPane(true)
  }

  logOB.onAction = { ae: ActionEvent => new LoginStage(stage) }

  /**
   * add buttons to side bar
   */
  sideBar.children add (poB)
  sideBar.children add (coB)
  sideBar.children add (logOB)

  /**
   * function that stores all the components in a scene
   */
  def createScene: Scene = {

    var scene: Scene = new Scene {
      border setTop (label)
      new CustomerOrderStage(stage, employee, border).createPane(true)
      content addAll (border)
      border setLeft (sideBar)
    }
    scene stylesheets = List(getClass.getResource("controlStyle2.css").toExternalForm)
    scene.fill = Color.rgb(109, 158, 104)
    scene
  }

  /**
   * set the main scene
   */
  stage.setScene(createScene)
}
