package com.qa.gui

import com.qa.gui._
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import com.qa.loader.PurchaseOrderLoad
import com.qa.Entities.PurchaseOrder
import scalafx.scene.control._
import scalafx.scene.Node
import scalafx.scene.layout.BorderPane
import scalafx.event.ActionEvent
import scalafx.Includes._
import scalafx.scene.layout.BorderPane
import scalafx.stage.Stage
import scalafx.scene.text.Font
import scalafx.scene.layout.GridPane
import scalafx.scene.text.FontWeight
import javafx.geometry.Insets
import com.qa.loader.CustomerOrderLoad

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
      val coL: CustomerOrderLoad = new CustomerOrderLoad
      val secondLabel: Label = new Label("Purchase Order - ID: " + sPO.idPurchaseOrder_)
      val secondaryLayout: BorderPane = new BorderPane
      /**
       * set up grid pane
       */
      val contentPane: GridPane = new GridPane
      contentPane setHgap (10);
      contentPane setVgap (10);
      contentPane setPadding (new Insets(0, 10, 0, 10));

      /**
       * Set up labels
       */
      val idLabel: Label = new Label("ID:")
      idLabel setFont (Font.font("Arial", FontWeight.BOLD, 20))
      val statusL: Label = new Label("Status:")
      statusL setFont (Font.font("Arial", FontWeight.BOLD, 20))
      val datePlacedL: Label = new Label("Date Placed:")
      datePlacedL setFont (Font.font("Arial", FontWeight.BOLD, 20))
      val dateDeliveredL: Label = new Label("Date Delivered:")
      dateDeliveredL setFont (Font.font("Arial", FontWeight.BOLD, 20))
      val supplierL: Label = new Label("Supplier:")
      supplierL setFont (Font.font("Arial", FontWeight.BOLD, 20))
      val employeeL: Label = new Label("Employee:")
      employeeL setFont (Font.font("Arial", FontWeight.BOLD, 20))

      /**
       * Set up value labels
       */
      val idV: Label = new Label("" + sPO.idPurchaseOrder_)
      idV setFont (Font.font("Arial", 20))
      val statusV: Label = new Label(sPO.purchaseOrderStatus_)
      statusV setFont (Font.font("Arial", 20))
      val datePlacedV: Label = new Label("" + sPO.datePlaced_)
      datePlacedV setFont (Font.font("Arial", 20))
      val dateDeliveredV: Label = new Label("" + sPO.dateExpected)
      dateDeliveredV setFont (Font.font("Arial", 20))
      val supplierV: Label = new Label(sPO.supplier_)
      supplierV setFont (Font.font("Arial", 20))
      val employeeV: Label = new Label(coL.getUserByID(sPO.employee))
      employeeV setFont (Font.font("Arial", 20))

      /**
       * add componenents to grid pane
       */
      contentPane.add(idLabel, 1, 1)
      contentPane.add(statusL, 1, 2)
      contentPane.add(datePlacedL, 1, 3)
      contentPane.add(dateDeliveredL, 1, 4)
      contentPane.add(supplierL, 1, 5)
      contentPane.add(employeeL, 1, 6)

      contentPane.add(idV, 2, 1)
      contentPane.add(statusV, 2, 2)
      contentPane.add(datePlacedV, 2, 3)
      contentPane.add(dateDeliveredV, 2, 4)
      contentPane.add(supplierV, 2, 5)
      contentPane.add(employeeV, 2, 6)

      secondLabel.setFont(Font.font("Verdana", 30))

      /**
       * add componenents to border pane
       */
      secondaryLayout.top = secondLabel
      secondaryLayout.center = contentPane
      
      /**
       * set up and show stage
       */
      val secondScene: Scene = new Scene(secondaryLayout)
      val secondStage: Stage = new Stage()
      secondStage setTitle ("Purchase Order")
      secondStage setScene (secondScene)

      secondStage setX (stage.getX() + 250)
      secondStage setY (stage.getY() + 100)

      secondStage show ()
    }

    /**
     * add content to a border pane
     */
    var layout: BorderPane = new BorderPane
    layout.setCenter(table)
    layout.setBottom(selectB)
    var scene: Scene = new Scene {

    }
    layout
  }
}