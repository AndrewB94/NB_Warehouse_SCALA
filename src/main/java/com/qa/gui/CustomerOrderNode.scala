package com.qa.gui

import com.qa.gui._
import scalafx.Includes._
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import com.qa.loader.CustomerOrderLoad
import com.qa.Entities.CustomerOrder
import scalafx.scene.control.Label
import scalafx.scene.control.TableView
import scalafx.scene.control.TableColumn
import scalafx.scene.layout.BorderPane
import scalafx.scene.layout.HBox
import scalafx.scene.control.Button
import scalafx.event.ActionEvent
import scalafx.scene.Node
import javafx.geometry.Insets

/**
 * @author abutcher
 * @date 09/11/2015
 * class that creates a node contains a table of customer orders
 */
class CustomerOrderNode(stage: PrimaryStage, employee: String, pane: BorderPane) {
  var layout: BorderPane = new BorderPane

  def createTable(showStored: Boolean) = {
    val col: CustomerOrderLoad = new CustomerOrderLoad
    var orders = col.getAllNotStoredCustomerOrders

    if (showStored) {
      orders = col.getAllCustomerOrders
    }

    var table = new TableView[CustomerOrder](orders)
    val selectB: Button = new Button("Select Order")
    val toggleB: Button = new Button("Show/Hide - Dispatched Orders")

    /**
     * set up table collumns
     */
    var idColumn = new TableColumn[CustomerOrder, String] {
      text = "ID"
      cellValueFactory = { _.value id }
      prefWidth = 100
    }

    var statusCollumn = new TableColumn[CustomerOrder, String]() {
      text = "Status"
      cellValueFactory = { _.value status }
      prefWidth = 150
    }

    var customerCollumn = new TableColumn[CustomerOrder, String] {
      text = "Customer"
      cellValueFactory = { _.value customer }
      prefWidth = 200
    }

    var datePlacedCollumn = new TableColumn[CustomerOrder, String] {
      text = "Date Placed"
      cellValueFactory = { _.value datePlaced }
      prefWidth = 200
    }

    /**
     * Create table and add collumns to it
     */
    table = new TableView[CustomerOrder](orders) {
      columns ++= List(idColumn, statusCollumn, customerCollumn, datePlacedCollumn)
    }
    layout.setCenter(table)

    def addButtons = {
      /**
       * set up action event on select button
       */
      selectB.onAction = { ae: ActionEvent =>
        val sCO: CustomerOrder = table.getSelectionModel.getSelectedItem
        val iCOS: IndividualCustomerOrderStage = new IndividualCustomerOrderStage

        iCOS.Open(sCO, employee)
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