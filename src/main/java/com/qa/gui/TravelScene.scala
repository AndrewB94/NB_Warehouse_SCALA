package com.qa.gui

import com.qa.Entities.CustomerOrder
import scalafx.scene.Scene
import scalafx.stage.Stage
import scalafx.scene.Node
import com.qa.loader.CustomerOrderLoad
import com.qa.loader.CustomerOrderLineLoad
import scalafx.scene.layout.BorderPane
import scalafx.stage.StageStyle
import javafx.scene.control._
import scalafx.scene.layout.GridPane
import scalafx.Includes._
import scalafx.scene.shape.Rectangle
import scalafx.scene.paint.Color
import scalafx.scene.text.Font
import scalafx.scene.layout.VBox
import javafx.geometry.Insets
import scalafx.scene.control.Button
import com.qa.logic.TravelingSales

/**
 * @author abutcher
 */
class TravelScene {

  val recs = Array(
      Array(3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3,3),
      Array(3, 1, 1, 1, 1, 1, 1, 1, 1, 1, 3,3),
      Array(3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,3),
      Array(3, 1, 0, 1, 1, 1, 1, 1, 1, 0, 1,3),
      Array(3, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1,3),
      Array(3, 1, 0, 1, 1, 1, 1, 1, 1, 0, 1,3),
      Array(3, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1,3),
      Array(3, 1, 0, 1, 1, 1, 1, 1, 1, 0, 1,3),
      Array(3, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1,3),
      Array(3, 1, 0, 1, 1, 1, 1, 1, 1, 0, 1,3),
      Array(3, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0,3),
      Array(3, 3, 1, 1, 1, 1, 1, 1, 1, 1, 1,3),
      Array(3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3,3))
  
  def getScene(selectedCO: CustomerOrder, stage: Stage, employee: String): Node = {
    val coL: CustomerOrderLoad = new CustomerOrderLoad
    val coll: CustomerOrderLineLoad = new CustomerOrderLineLoad
    val layout: BorderPane = new BorderPane
    val label: Label = new Label("Customer Order - ID: " + selectedCO.idCustomerOrder_)
    
    label setFont (Font.font("Verdana", 30))
    
    layout.top = label
    layout.center = createModel
    layout.left = sideBar
    
    val alg:TravelingSales = new TravelingSales(recs)
//    alg.calculate
    
         var grid: GridPane = new GridPane
      grid setHgap (10)
      grid setVgap (10)
      grid setPadding (new Insets(0, 10, 10, 10))
      grid.add(layout, 1, 1)
    grid
  }

  def sideBar: VBox = {
    val sideBar: VBox = new VBox
    sideBar.setPadding(new Insets(10));
    sideBar.setSpacing(8);

    val startRB: Button = new Button("Start parth")
    val nextSB: Button = new Button("Next step")
    val clearPB: Button = new Button("Clear parth")

    sideBar.children add (startRB)
    sideBar.children add (nextSB)
    sideBar.children add (clearPB)

    sideBar
  }

  def createModel: GridPane = {
    val grid: GridPane = new GridPane
    grid.gridLinesVisible = true
        

    def createSquare(x: Int, y: Int): Rectangle = {
      val square: Rectangle = new Rectangle
      square.setStroke(Color.BLACK);
      var colour: Color = Color.White
      recs(x)(y) match {
        case 1 => colour = Color.Green //Avalliable storage location 
        case 2 => colour = Color.Yellow //full storage location
        case 3 => colour = Color.Red //Not acessable
        case 4 => colour = Color.Blue //Path
        case _ => colour = Color.White //Walkable area
      }

      square.width = 60
      square.height = 60
      square.fill = colour
      square
    }

    def createMap {
      var tileMap: Array[Array[Rectangle]] = Array.ofDim[Rectangle](recs.length, recs(0).length)
      def addTiles(x: Int) {
        def singleTile(y: Int) {
          if (y < recs(x).length) {
            grid.add(createSquare(x, y), y, x)
            singleTile(y + (1))
          }
        }
        if (x < recs.length) {
          singleTile(0)
          addTiles(x + (1))
        }
      }
      addTiles(0)
    }
    createMap
    grid
  }

  def open(selectedCO: CustomerOrder, employee: String): Unit = {

    /**
     * set up and show stage
     */
    val secondScene: Scene = new Scene
    secondScene stylesheets = List(getClass.getResource("controlStyle2.css").toExternalForm)
    secondScene.fill = Color.rgb(109, 158, 104)
    val secondStage: Stage = new Stage
    //    secondStage.initStyle(StageStyle.UNDECORATED)

    secondScene.getChildren.add(getScene(selectedCO, secondStage, employee))
    secondStage setTitle ("Purchase Order")
    secondStage setScene (secondScene)

    //      secondStage setX (stage.getX() + 250)
    //      secondStage setY (stage.getY() + 100)

    secondStage show ()
  }
}