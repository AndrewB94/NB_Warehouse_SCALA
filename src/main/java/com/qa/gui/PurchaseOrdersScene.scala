package com.qa.gui

import com.qa.loader.PurchaseOrderLoad
import scalafx.collections.ObservableBuffer
import scalafx.scene.Scene
import scalafx.scene.control.TableColumn._
import scalafx.scene.control.{ TableCell, TableColumn, TableView }
import scalafx.scene.paint.Color
import scalafx.scene.shape.Circle
import com.qa.Entities.PurchaseOrder

/**
 * @author abutcher
 */
class PurchaseOrdersScene {
  val pol: PurchaseOrderLoad = new PurchaseOrderLoad

  /**
   * Function that returns a scene containing a table of purchase orders
   * @return Scene for use in main frame
   */
  def getScene: Scene  ={   
    val orders = pol.getAllPurchaseOrders
    
    //
    var idColumn = new TableColumn [PurchaseOrder, String] {
            text = "ID"
            cellValueFactory = { _.value.id }
            prefWidth = 100
          }
    
    var statusCollumn = new TableColumn[PurchaseOrder, String]() {
            text = "Status"
            cellValueFactory = { _.value.status }
            prefWidth = 100
          }
    
    var supplierCollumn = new TableColumn[PurchaseOrder, String] {
            text = "Supplier"
            cellValueFactory = { _.value.supplier }
            prefWidth = 100
    }
    
      var datePlacedCollumn = new TableColumn[PurchaseOrder, String] {
            text = "Date Placed"
            cellValueFactory = { _.value.datePlaced }
            prefWidth = 100
    }
    var scene = new Scene {
      content = new TableView[PurchaseOrder](orders) {
        columns ++= List(idColumn, statusCollumn,supplierCollumn, datePlacedCollumn)
      }
    }
    scene
  }
}