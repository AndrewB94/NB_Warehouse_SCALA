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

  def getScene: Scene  ={   
    val orders = pol.getAllPurchaseOrders  
    var scene = new Scene {
      content = new TableView[PurchaseOrder](orders) {
        columns ++= List(
          new TableColumn[PurchaseOrder, String] {
            text = "ID"
            cellValueFactory = { _.value.id }
            prefWidth = 100
          },
          new TableColumn[PurchaseOrder, String]() {
            text = "Status"
            cellValueFactory = { _.value.status }
            prefWidth = 100
          },
          new TableColumn[PurchaseOrder, String] {
            text = "Supplier"
            cellValueFactory = { _.value.supplier }
            prefWidth = 100
          })
      }
    }
    scene
  }
}