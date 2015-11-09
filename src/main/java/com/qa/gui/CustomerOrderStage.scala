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

/**
 * @author abutcher
 * @date 09/11/2015
 * class thatcreates a node contains a table of customer orders
 */
class CustomerOrderStage(stage: PrimaryStage) {

  /**
   * function that creates a node containning the content
   * @retgurn Node the node containning the customer orders table
   */
  def createPane: Node = {
    /**
     * Initialize values / variables
     */
    val col: CustomerOrderLoad = new CustomerOrderLoad
    val orders = col.getAllCustomerOrders
    val label: Label = new Label("Customer Orders")
    var table = new TableView[CustomerOrder](orders)    
    
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
      prefWidth = 100
    }

    var customerCollumn = new TableColumn[CustomerOrder, String] {
      text = "Customer"
      cellValueFactory = { _.value customer }
      prefWidth = 100
    }

    var datePlacedCollumn = new TableColumn[CustomerOrder, String] {
      text = "Date Placed"
      cellValueFactory = { _.value datePlaced }
      prefWidth = 100
    }
    
    /**
     * Create table and add collumns to it
     */
    table = new TableView[CustomerOrder](orders) {
      columns ++= List(idColumn, statusCollumn, customerCollumn, datePlacedCollumn)
    }
    table
  }

}