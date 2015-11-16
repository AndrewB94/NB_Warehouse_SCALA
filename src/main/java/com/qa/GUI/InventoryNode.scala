package com.qa.GUI

import com.qa.GUI._
import scalafx.Includes._
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.control.Label
import scalafx.scene.control.TableView
import scalafx.scene.control.TableColumn
import scalafx.scene.layout.BorderPane
import scalafx.scene.layout.HBox
import scalafx.scene.control.Button
import scalafx.event.ActionEvent
import scalafx.scene.Node
import javafx.geometry.Insets
import com.qa.Loader.ItemLoad
import com.qa.Entities.Item

/**
 * @author abutcher
 * @date 12/11/2015
 * An object to display a table showing the inventory of the warehouse
 */
object InventoryNode {
  var layout: BorderPane = new BorderPane

  /**
   * Function to create a table of customer items
   */
  def createTable() = {
    var orders = ItemLoad.getAllItems
    var table = new TableView[Item](orders)
    var idColumn = new TableColumn[Item, String] {
      text = "ID"
      cellValueFactory = { _.value id }
      prefWidth = 100
    }

    var statusCollumn = new TableColumn[Item, String]() {
      text = "Name"
      cellValueFactory = { _.value name }
      prefWidth = 250
    }

    var customerCollumn = new TableColumn[Item, String] {
      text = "Supplier"
      cellValueFactory = { _.value supplier }
      prefWidth = 200
    }

    var datePlacedCollumn = new TableColumn[Item, String] {
      text = "Stock Level"
      cellValueFactory = { _.value quantity }
      prefWidth = 200
    }

    table = new TableView[Item](orders) {
      columns ++= List(idColumn, statusCollumn, customerCollumn, datePlacedCollumn)
    }
    layout.setCenter(table)
  }

  /**
   * function that creates a node containning the content
   * @retgurn Node the node containning the purchase orders table
   */
  def createPane(pane: BorderPane): Unit = {
    createTable()
    pane.center = layout
  }
}