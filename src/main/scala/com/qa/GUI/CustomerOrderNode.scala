package com.qa.GUI

import com.qa.GUI._
import scalafx.Includes._
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import com.qa.Loader.CustomerOrderLoad
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
object CustomerOrderNode {

  /**
   * Function to create a table of customer orders
   * @param showStored : Boolean show dispatched or not
   * @return TableView[CustomerOrder]
   */
  private def createTable(showStored: Boolean) = {
    var orders = CustomerOrderLoad.getAllNotDispatchedCustomerOrders

    if (showStored) {
      orders = CustomerOrderLoad.getAllCustomerOrders
    }
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

    val table = new TableView[CustomerOrder](orders) {
      columns ++= List(idColumn, statusCollumn, customerCollumn, datePlacedCollumn)
    }
    table
  }

  /**
   * Function create a HBox containning buttons
   * @param table : TableView the table to select
   * @param employee : String employee currently logged in
   * @param border : BorderPane to add HBox too
   * @param showStored : Boolean show dispatched or not
   * @return HBox : containing buttons
   */
  private def addButtons(table: TableView[CustomerOrder], employee: String, pane: BorderPane, showStored: Boolean): HBox = {
    val selectB: Button = new Button("Select Order")
    val toggleB: Button = new Button("Show/Hide - Stored Orders")
    val box: HBox = new HBox
    box setPadding (new Insets(10));
    box setSpacing (8);
    /**
     * set up action event on select button
     */
    selectB.onAction = { ae: ActionEvent =>
      val sCO: CustomerOrder = table.getSelectionModel.getSelectedItem
      IndividualCustomerOrderStage.Open(sCO, employee)
    }

    toggleB.onAction = { ae: ActionEvent =>
      createPane(employee, pane, !showStored)
    }
    box.children add (selectB)
    box.children add (toggleB)

    box
  }

  /**
   * Function to add all the content to the main pane
      * @param employee : String employee currently logged in
   * @param showStored : Boolean show dispatched or not
   * @return BorderPane
   */
  private def createLayout(showStored: Boolean, pane: BorderPane, employee: String): BorderPane = {
    var layout: BorderPane = new BorderPane
    val table: TableView[CustomerOrder] = createTable(showStored)
    layout.center = table
    layout.bottom = addButtons(table, employee, pane, showStored)
    layout
  }

  /**
   * function that creates a node containning the content
   * @retgurn Node the node containning the purchase orders table
   */
  def createPane(employee: String, pane: BorderPane, showStored: Boolean): Unit = {
    pane.center = createLayout(showStored, pane, employee)
  }
}