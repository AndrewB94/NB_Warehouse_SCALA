package com.qa.gUI

import com.qa.entities.PurchaseOrder
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
import scalafx.stage.StageStyle
import com.qa.loader.LocationLineLoad
import com.qa.entities.LocationLine

/**
 * @author abutcher
 * @date 12/11/2015
 * An object to enter the location of an item in the warehouse
 */
class LocationStage {
  /**
   * Function create all the content for the scene and return it
   * @param selectedItem : Int the id of the selected item
   * @param stage : Stage the stage to display
   * @param quantity : Int the quantity on the order
   * @return Node : containnig the scene
   */
  def getScene(selectedItem: Int, stage: Stage, quantity: Int): Node = {
    val title: Label = new Label("Enter Stored Location of Item ID: " + selectedItem)
    val collumnL: Label = new Label("Collumn:")
    val rowL: Label = new Label("Row:")
    val xT: TextField = new TextField
    val yT: TextField = new TextField

    xT setPromptText ("1")
    yT setPromptText ("1")

    title setFont (Font.font("Arial", FontWeight.BOLD, 30))
    collumnL setFont (Font.font("Arial", FontWeight.BOLD, 20))
    rowL setFont (Font.font("Arial", FontWeight.BOLD, 20))

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

    var grid: GridPane = new GridPane
    grid setHgap (10)
    grid setVgap (10)
    grid setPadding (new Insets(0, 10, 10, 10))

    grid add (title, 0, 1, 2, 1)
    grid add (collumnL, 0, 2)
    grid add (rowL, 0, 3)
    grid add (xT, 1, 2, 2, 1)
    grid add (yT, 1, 3, 2, 1)
    grid add (confirmB, 1, 4)
    grid
  }
  
  /**
   * Open the stage with all the content
   * @param selectedItem : Int the id of the selected item
   * @param quantity : Int the quantity on the order
   * 
   */
  def open(itemId: Int, quantity: Int): Unit = {
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