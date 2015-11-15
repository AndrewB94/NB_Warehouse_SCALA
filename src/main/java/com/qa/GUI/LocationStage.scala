package com.qa.GUI

import com.qa.Entities.PurchaseOrder
import scalafx.stage.Stage
import scalafx.scene.Node
import scalafx.scene.control._
import scalafx.scene.text.Font
import scalafx.scene.text.FontWeight
import scalafx.Includes._
import scalafx.event.ActionEvent
import java.time.Year
import scalafx.scene.Scene
import scalafx.scene.layout.GridPane
import javafx.geometry.Insets
import scalafx.scene.paint.Color
import com.qa.Entities.PurchaseOrder
import scalafx.stage.StageStyle
import com.qa.Loader.LocationLineLoad
import com.qa.Entities.LocationLine

/**
 * @author abutcher
 * @date 12/11/2015
 * An object to enter the location of an item in the warehouse
 */
class LocationStage {
  def getScene(selectedItem: Int, stage: Stage, quantity: Int): Node = {

    /**
     * initialize values
     */
    val title: Label = new Label("Enter Stored Location of Item ID: " + selectedItem)
    val collumnL: Label = new Label("Collumn:")
    val rowL: Label = new Label("Row:")
    val xT: TextField = new TextField
    val yT: TextField = new TextField

    /**
     * set textField prompts
     */
    xT setPromptText ("1")
    yT setPromptText ("1")

    /**
     * set label fonts
     */
    title setFont (Font.font("Arial", FontWeight.BOLD, 30))
    collumnL setFont (Font.font("Arial", FontWeight.BOLD, 20))
    rowL setFont (Font.font("Arial", FontWeight.BOLD, 20))

    /**
     * set up action events for buttons
     */
    val confirmB: Button = new Button("Confirm") {
      onAction = { ae: ActionEvent =>
        if (xT.getText != "" && yT.getText != "") {
          //update line
          val locationLine = LocationLineLoad checkLocationLineIDByItemIDandLoaction (selectedItem, xT.getText, yT.getText)
          if (locationLine == 0) {
            //no Line found
            LocationLineLoad createLine(Integer.parseInt(xT.getText), Integer.parseInt(yT.getText), selectedItem, quantity)
          } else {
            val addToLine: LocationLine = LocationLineLoad.getLocationLineByItemIDandLoaction(selectedItem, xT.getText, yT.getText)(0)
            val newQuant = addToLine.quantity + quantity
            LocationLineLoad updateQuantity (Integer.parseInt(xT.getText), Integer.parseInt(yT.getText), selectedItem, newQuant)
          }
          stage.hide()
        }
      }
    }

    /**
     * Initialize grid pane
     */
    var grid: GridPane = new GridPane
    grid setHgap (10)
    grid setVgap (10)
    grid setPadding (new Insets(0, 10, 10, 10))

    /**
     * add components to grid pane
     */
    grid add (title, 0, 1, 2, 1)
    grid add (collumnL, 0, 2)
    grid add (rowL, 0, 3)
    grid add (xT, 1, 2, 2, 1)
    grid add (yT, 1, 3, 2, 1)
    grid add (confirmB, 1, 4)
    grid
  }
  def open(itemId: Int, quantity: Int): Unit = {

    /**
     * set up and show stage
     */
    val secondScene: Scene = new Scene
    secondScene stylesheets = List(getClass.getResource("/controlStyle2.css").toExternalForm)
    secondScene.fill = Color.rgb(109, 158, 104)
    val secondStage: Stage = new Stage
    secondStage.initStyle(StageStyle.UNDECORATED)

    secondScene.getChildren.add(getScene(itemId, secondStage, quantity))
    secondStage setTitle ("Purchase Order")
    secondStage setScene (secondScene)

    //      secondStage setX (stage.getX() + 250)
    //      secondStage setY (stage.getY() + 100)

    secondStage show ()
  }
}