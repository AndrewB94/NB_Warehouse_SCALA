package com.qa.gui

import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import com.qa.loader.PurchaseOrderLoad
import com.qa.Entities.PurchaseOrder
import scalafx.scene.control.TableColumn
import scalafx.scene.control.TableView

/**
 * @author abutcher
 */
class PurchaseOrderStage(stage:PrimaryStage) {
  stage.setTitle("Customer Orders")
  
  def createScene:Scene = {
    val pol: PurchaseOrderLoad = new PurchaseOrderLoad
    val orders = pol.getAllPurchaseOrders
    
    var idColumn = new TableColumn [PurchaseOrder, String] {
            text = "ID"
            cellValueFactory = { _.value id }
            prefWidth = 100
    }
    
    var statusCollumn = new TableColumn[PurchaseOrder, String]() {
            text = "Status"
            cellValueFactory = { _.value status }
            prefWidth = 100
    }
    
    var supplierCollumn = new TableColumn[PurchaseOrder, String] {
            text = "Supplier"
            cellValueFactory = { _.value supplier }
            prefWidth = 100
    }
    
      var datePlacedCollumn = new TableColumn[PurchaseOrder, String] {
            text = "Date Placed"
            cellValueFactory = { _.value datePlaced }
            prefWidth = 100
    }
     var scene:Scene = new Scene{
      content = new TableView[PurchaseOrder](orders) {
        columns ++= List(idColumn, statusCollumn,supplierCollumn, datePlacedCollumn)
      }
    }
    scene
  }
  
   stage.setScene(createScene)
}