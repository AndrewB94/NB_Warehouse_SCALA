package com.qa.gui

import com.qa.gui._
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import com.qa.loader.PurchaseOrderLoad
import com.qa.Entities.PurchaseOrder
import scalafx.scene.control._
import scalafx.scene.layout.BorderPane
import scalafx.event.ActionEvent
import scalafx.Includes._
import scalafx.stage.Stage
import scalafx.scene.text.Font
import scalafx.scene.layout.GridPane
import scalafx.scene.text.FontWeight
import javafx.geometry.Insets
import scalafx.scene.Node
import scalafx.stage.StageStyle
import scalafx.event.EventHandler
import scalafx.scene.layout.HBox

/**
 * @author abutcher
 * @date 09/11/2015
 * class that creates a node containing the purchase orders
 */
class PurchaseOrderStage(stage: PrimaryStage, employee: String, pane: BorderPane) {

  var layout: BorderPane = new BorderPane

  def createTable(showStored: Boolean) = {
    val pol: PurchaseOrderLoad = new PurchaseOrderLoad
    var orders = pol.getAllNotStoredPurchaseOrders

    if (showStored) {
      orders = pol.getAllPurchaseOrders
    }

    var table = new TableView[PurchaseOrder](orders)
    val selectB: Button = new Button("Select Order")
    val toggleB: Button = new Button("Show/Hide - Stored Orders")

    /**
     * set up table collumns
     */
    var idColumn = new TableColumn[PurchaseOrder, String] {
      text = "ID"
      cellValueFactory = { _.value id }
      prefWidth = 100
    }

    var statusCollumn = new TableColumn[PurchaseOrder, String]() {
      text = "Status"
      cellValueFactory = { _.value status }
      prefWidth = 150
    }

    var supplierCollumn = new TableColumn[PurchaseOrder, String] {
      text = "Supplier"
      cellValueFactory = { _.value supplier }
      prefWidth = 300
    }

    var datePlacedCollumn = new TableColumn[PurchaseOrder, String] {
      text = "Date Placed"
      cellValueFactory = { _.value datePlaced }
      prefWidth = 200
    }

    /**
     * Create table and add collumns to it
     */
    table = new TableView[PurchaseOrder](orders) {
      columns ++= List(idColumn, statusCollumn, supplierCollumn, datePlacedCollumn)
    }
    layout.setCenter(table)

    def addButtons = {
      /**
       * set up action event on select button
       */
      selectB.onAction = { ae: ActionEvent =>
        val sPO: PurchaseOrder = table.getSelectionModel.getSelectedItem
        val iPOS: IndividualPurchaseOrderScene = new IndividualPurchaseOrderScene

        iPOS.Open(sPO, employee)
      }

      toggleB.onAction = { ae: ActionEvent =>
        createPane(!showStored)
      }

    }
    addButtons

    val box: HBox = new HBox
    box setPadding (new Insets(10));
    box setSpacing (8);
    box.children add (selectB)
    box.children add (toggleB)

    layout.setBottom(box)
  }

  /**
   * function that creates a node containning the content
   * @retgurn Node the node containning the purchase orders table
   */
  def createPane(showStored: Boolean): Unit = {
    createTable(showStored)
    pane.center = layout
  }
}