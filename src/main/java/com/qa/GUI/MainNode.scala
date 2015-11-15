package com.qa.GUI

import com.qa.GUI._
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
class MainNode(stage: PrimaryStage, employee: String) {
  /**
   * initialize values
   */
  val titleS: String = "NB Gardens - Warehouse Order Tracking System - "
  stage.setTitle(titleS + "Inventory")
  val label: Label = new Label("Inventory")
  label.setFont(Font.font("Verdana", 30))
  val border: BorderPane = new BorderPane()
  val poB: Button = new Button("Purchase Orders")
  val coB: Button = new Button("Customer Orders")
  val logOB: Button = new Button("Close")
  val invB: Button = new Button("Inventory")

  logOB prefWidth = 300
  invB prefWidth = 300
  coB prefWidth = 300
  poB prefWidth = 300

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
    new PurchaseOrderNode(stage, employee, border).createPane(true)
  }
  coB.onAction = { ae: ActionEvent =>
    stage.setTitle(titleS + "Customer Orders")
    label.setText("Customer Orders")
    new CustomerOrderNode(stage, employee, border).createPane(true)
  }

  invB.onAction = { ae: ActionEvent =>
    stage.setTitle(titleS + "Inventory")
    label.setText("Inventory")
    new InventoryNode(stage, employee, border).createPane
  }

  logOB.onAction = { ae: ActionEvent => stage.hide }

  /**
   * add buttons to side bar
   */
  sideBar.children add (poB)
  sideBar.children add (coB)
  sideBar.children add (invB)
  sideBar.children add (logOB)

  /**
   * function that stores all the components in a scene
   */
  def createScene: Scene = {

    var scene: Scene = new Scene {
      border setTop (label)
      new InventoryNode(stage, employee, border).createPane
      border setLeft (sideBar)
      var grid: GridPane = new GridPane
      grid setHgap (10)
      grid setVgap (10)
      grid setPadding (new Insets(0, 10, 10, 10))
      grid.add(border, 1, 1)
      content addAll (grid)

    }
    scene stylesheets = List(getClass.getResource("/controlStyle2.css").toExternalForm)
    scene.fill = Color.rgb(109, 158, 104)
    scene
  }

  /**
   * set the main scene
   */
  stage.setScene(createScene)
  stage.minWidth = 1120
  stage.maxWidth = 1120
  stage.minHeight = 550
  stage.maxHeight = 550
}
