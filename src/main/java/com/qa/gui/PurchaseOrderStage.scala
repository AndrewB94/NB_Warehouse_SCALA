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


/**
 * @author abutcher
 * @date 09/11/2015
 * class that creates a node containing the purchase orders
 */
class PurchaseOrderStage(stage: PrimaryStage) {

  /**
   * function that creates a node containning the content
   * @retgurn Node the node containning the purchase orders table
   */
  def createPane: Node = {
    /**
     * Initialize values / variables
     */
    val pol: PurchaseOrderLoad = new PurchaseOrderLoad
    val orders = pol.getAllPurchaseOrders
    var table = new TableView[PurchaseOrder](orders)
    val selectB: Button = new Button("Select Order")

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

    /**
     * Create table and add collumns to it
     */
    table = new TableView[PurchaseOrder](orders) {
      columns ++= List(idColumn, statusCollumn, supplierCollumn, datePlacedCollumn)
    }

    /**
     * set up action event on select button
     */
    selectB.onAction = { ae: ActionEvent =>
      /**
       * Initialize values
       */
      val sPO: PurchaseOrder = table.getSelectionModel.getSelectedItem
      val iPOS: IndividualPurchaseOrderScene = new IndividualPurchaseOrderScene

      iPOS.Open(sPO)
    }

    /**
     * add content to a border pane
     */
    var layout: BorderPane = new BorderPane
    layout.setCenter(table)
    layout.setBottom(selectB)

    layout
  }
}