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
 */
class CustomerOrderStage(stage: PrimaryStage) {
  stage.setTitle("Customer Orders")

  def createPane: Node = {
    val col: CustomerOrderLoad = new CustomerOrderLoad
    val orders = col.getAllCustomerOrders
    val label: Label = new Label("Customer Orders");
    val button:Button = new Button("Purchase Orders")
    var table = new TableView[CustomerOrder](orders)

    button.onAction = { ae: ActionEvent => new PurchaseOrderStage(stage)}
    
    //
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
    table = new TableView[CustomerOrder](orders) {
      columns ++= List(idColumn, statusCollumn, customerCollumn, datePlacedCollumn)
    }
    table
  }

}