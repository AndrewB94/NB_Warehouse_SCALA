package com.qa.gui

import com.qa.loader.PurchaseOrderLoad
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.collections.ObservableBuffer
import scalafx.scene.Scene
import scalafx.scene.control.TableColumn._
import scalafx.scene.control.{TableCell, TableColumn, TableView}
import scalafx.scene.paint.Color
import scalafx.scene.shape.Circle
import com.qa.Entities.PurchaseOrder


object Main extends JFXApp {
  val pol: PurchaseOrderLoad = new PurchaseOrderLoad
    val orders = pol.getAllPurchaseOrders

    stage = new PrimaryStage {
      title = "TableView with custom color cell"
      scene = new Scene {
        content = new TableView[PurchaseOrder](orders) {
          columns ++= List(
            new TableColumn[PurchaseOrder, String] {
              text = "ID"
              cellValueFactory = { _.value.id }

            },
            new TableColumn[PurchaseOrder, String]() {
              text = "Status"
              cellValueFactory = { _.value.status }
            },
            new TableColumn[PurchaseOrder, String] {
              text = "Supplier"
              cellValueFactory = { _.value.supplier }
            })
        }
      }
    }

}