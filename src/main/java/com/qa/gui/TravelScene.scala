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



/**
 * @author abutcher
 */
class TravelScene {
  def getScene(selectedCO: CustomerOrder, stage: Stage, employee:String): Node = {
        val coL: CustomerOrderLoad = new CustomerOrderLoad
    val coll:CustomerOrderLineLoad = new CustomerOrderLineLoad
    val Layout: BorderPane = new BorderPane
    val secondLabel: Label = new Label("Customer Order - ID: " + selectedCO.idCustomerOrder_)
    Layout
  }
  
  
    def Open(selectedCO: CustomerOrder, employee:String): Unit = {
      
    /**
     * set up and show stage
     */
    val secondScene: Scene = new Scene
    val secondStage: Stage = new Stage
    secondStage.initStyle(StageStyle.UNDECORATED)

    secondScene.getChildren.add(getScene(selectedCO, secondStage, employee))
    secondStage setTitle ("Purchase Order")
    secondStage setScene (secondScene)

    //      secondStage setX (stage.getX() + 250)
    //      secondStage setY (stage.getY() + 100)

    secondStage show ()
  }
}