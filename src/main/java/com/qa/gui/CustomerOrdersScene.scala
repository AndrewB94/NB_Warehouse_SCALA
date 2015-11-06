package com.qa.gui

import com.qa.loader.CustomerOrderLoad
import scalafx.collections.ObservableBuffer
import scalafx.scene.Scene
import scalafx.scene.control.TableColumn._
import scalafx.scene.control.{ TableCell, TableColumn, TableView }
import scalafx.scene.paint.Color
import scalafx.scene.shape.Circle
import com.qa.Entities.CustomerOrder

/**
 * @author abutcher
 */
class CustomerOrdersScene {
  val col: CustomerOrderLoad = new CustomerOrderLoad

  /**
   * Function that returns a scene containing a table of purchase orders
   * @return Scene for use in main frame
   */
  def getScene: Scene  ={   
    val orders = col.getAllCustomerOrders
    
    //
    var idColumn = new TableColumn [CustomerOrder, String] {
            text = "ID"
            cellValueFactory = { _.value id }
            prefWidth = 100
          }
    
    var statusCollumn = new TableColumn[CustomerOrder, String]() {
            text = "Status"
            cellValueFactory = { _.value status }
            prefWidth = 100
          }
    
    var csutomerCollumn = new TableColumn[CustomerOrder, String] {
            text = "Customer"
            cellValueFactory = { _.value customer }
            prefWidth = 100
    }
    
      var datePlacedCollumn = new TableColumn[CustomerOrder, String] {
            text = "Date Placed"
            cellValueFactory = { _.value datePlaced }
            prefWidth = 100
    }
    var scene = new Scene {
      content = new TableView[CustomerOrder](orders) {
        columns ++= List(idColumn, statusCollumn,csutomerCollumn, datePlacedCollumn)
      }
    }
    scene
  }
}