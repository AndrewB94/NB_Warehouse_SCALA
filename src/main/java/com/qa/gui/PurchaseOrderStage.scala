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
 */
class PurchaseOrderStage(stage: PrimaryStage) {

  def createPane: Node = {
    val pol: PurchaseOrderLoad = new PurchaseOrderLoad
    val orders = pol.getAllPurchaseOrders
    var table = new TableView[PurchaseOrder](orders)
    val selectB: Button = new Button("Select Order")

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

    table = new TableView[PurchaseOrder](orders) {
      columns ++= List(idColumn, statusCollumn, supplierCollumn, datePlacedCollumn)
    }

    selectB.onAction = { ae: ActionEvent =>
      val sPO: PurchaseOrder = table.getSelectionModel.getSelectedItem
      val coL: CustomerOrderLoad = new CustomerOrderLoad
      val secondLabel: Label = new Label("Purchase Order - ID: " + sPO.idPurchaseOrder_)
      val secondaryLayout: BorderPane = new BorderPane
      val contentPane: GridPane = new GridPane
      contentPane setHgap (10);
      contentPane setVgap (10);
      contentPane setPadding (new Insets(0, 10, 0, 10));

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
      secondaryLayout.top = secondLabel
      secondaryLayout.center = contentPane
      val secondScene: Scene = new Scene(secondaryLayout)
      val secondStage: Stage = new Stage()
      secondStage setTitle ("Purchase Order")
      secondStage setScene (secondScene)

      //Set position of second window, related to primary window.
      secondStage setX (stage.getX() + 250)
      secondStage setY (stage.getY() + 100)

      secondStage show ()
    }

    var layout: BorderPane = new BorderPane
    layout.setCenter(table)
    layout.setBottom(selectB)
    var scene: Scene = new Scene {

    }
    layout
  }
}