package com.qa.gui

import com.qa.gui._
import com.qa.loader.CustomerOrderLoad
import com.qa.loader.purchaseOrderLineLoad
import com.qa.Entities.PurchaseOrderLine
import com.qa.Entities.PurchaseOrder
import com.qa.loader.PurchaseOrderLoad
import scalafx.scene.control._
import scalafx.scene.layout.BorderPane
import scalafx.event.ActionEvent
import scalafx.Includes._
import scalafx.stage.Stage
import scalafx.scene.text.Font
import scalafx.scene.layout.GridPane
import scalafx.scene.text.FontWeight
import scalafx.scene.Node
import scalafx.scene.control.Alert.AlertType
import javafx.scene.control.Alert
import scalafx.scene.control.ButtonBar.ButtonData
import javafx.geometry.Insets
import java.util.Optional
import scalafx.scene.Scene
import scalafx.stage.StageStyle
import scalafx.scene.paint.Color

/**
 * @author abutcher
 * @date 10/11/2015
 * class for creating a pane for displaying purchase orders
 */
class IndividualPurchaseOrderScene {
  def getScene(selectedPO: PurchaseOrder, stage: Stage, employee: String): Node = {
    /**
     * Initialize values
     */
    val coL: CustomerOrderLoad = new CustomerOrderLoad
    val secondLabel: Label = new Label("Purchase Order - ID: " + selectedPO.idPurchaseOrder_)
    val secondaryLayout: BorderPane = new BorderPane
    val poll: purchaseOrderLineLoad = new purchaseOrderLineLoad
    val lines = poll.getPurchaseOrderLinesByPurchaseOrderID(selectedPO.idPurchaseOrder_)
    var lineTable = new TableView[PurchaseOrderLine](lines)

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
    val employeeL: Label = new Label("Last Checked Out By:")
    employeeL setFont (Font.font("Arial", FontWeight.BOLD, 20))

    /**
     * Set up value labels
     */
    val idV: Label = new Label("" + selectedPO.idPurchaseOrder_)
    idV setFont (Font.font("Arial", 20))
    val statusV: Label = new Label(selectedPO.purchaseOrderStatus_)
    statusV setFont (Font.font("Arial", 20))
    val datePlacedV: Label = new Label("" + selectedPO.datePlaced_)
    datePlacedV setFont (Font.font("Arial", 20))
    val dateDeliveredV: Label = new Label("" + selectedPO.dateExpected)
    dateDeliveredV setFont (Font.font("Arial", 20))
    val supplierV: Label = new Label(selectedPO.supplier_)
    supplierV setFont (Font.font("Arial", 20))
    val employeeV: Label = new Label(coL.getUserByID(selectedPO.employee))
    employeeV setFont (Font.font("Arial", 20))

    /**
     * set up table columns
     */
    var itemIDCollumn = new TableColumn[PurchaseOrderLine, String] {
      text = "Item ID"
      cellValueFactory = { _.value itemID }
      prefWidth = 100
    }

    var itemNmaeCollumn = new TableColumn[PurchaseOrderLine, String] {
      text = "Item Name"
      cellValueFactory = { _.value itemName }
      prefWidth = 200
    }

    var quantityCollumn = new TableColumn[PurchaseOrderLine, String] {
      text = "Quantity Ordered"
      cellValueFactory = { _.value quantity }
      prefWidth = 250
    }

    var quantityDamagedCollumn = new TableColumn[PurchaseOrderLine, String] {
      text = "Quantity Damaged"
      cellValueFactory = { _.value.quantityDamaged }
      prefWidth = 250
    }

    /**
     * Create table and add collumns to it
     */
    lineTable = new TableView[PurchaseOrderLine](lines) {
      columns ++= List(itemIDCollumn, itemNmaeCollumn, quantityCollumn, quantityDamagedCollumn)
    }

    /**
     * add componenents to grid pane
     */
    contentPane add (idLabel, 1, 1)
    contentPane add (statusL, 1, 2)
    contentPane add (datePlacedL, 1, 3)
    contentPane add (dateDeliveredL, 1, 4)
    contentPane add (supplierL, 1, 5)
    contentPane add (employeeL, 1, 6)

    contentPane add (idV, 2, 1)
    contentPane add (statusV, 2, 2)
    contentPane add (datePlacedV, 2, 3)
    contentPane add (dateDeliveredV, 2, 4)
    contentPane add (supplierV, 2, 5)
    contentPane add (employeeV, 2, 6)
    contentPane add (lineTable, 3, 1, 1, 7)
    contentPane add (buttonPane, 1, 7, 2, 1)
    secondLabel setFont (Font.font("Verdana", 30))

    /**
     * add componenents to border pane
     */
    secondaryLayout.top = secondLabel
    secondaryLayout.center = contentPane

    def buttonPane: Node = {
      /**
       * Initialize values
       */
      val grid: GridPane = new GridPane
      grid setHgap (10)
      grid setVgap (10)
      grid setPadding (new Insets(0, 10, 0, 10))
      val closeB: Button = new Button("Close")
      val updateB: Button = new Button("Update State")
      val checkInB: Button = new Button("Check In Order")
      val checkOutB: Button = new Button("Check Out Order")

      /**
       * set up action event on buttons
       */
      closeB onAction = { ae: ActionEvent => stage.hide }
      updateB onAction = { ae: ActionEvent => updateStatus }
      checkOutB onAction = { ae: ActionEvent => checkOut }
      checkInB onAction = { ae: ActionEvent => checkIn }

      if (!selectedPO.isCheckedOut)
        checkInB.setDisable(true)

      if (selectedPO.isCheckedOut || selectedPO.idStatus == 4)
        checkOutB.setDisable(true)

      if (!selectedPO.isCheckedOut || selectedPO.idStatus == 4)
        updateB.setDisable(true)

      if (selectedPO.isCheckedOut)
        closeB.setDisable(true)

      /**
       * add componenents to grid pane
       */
      grid.add(checkInB, 1, 1)
      grid.add(checkOutB, 2, 1)
      grid.add(updateB, 1, 2)
      grid.add(closeB, 2, 2)
      grid
    }

    /**
     * Function that updates the purchaseOrderStatus
     */
    def updateStatus: Unit = {
      var newStateID = 0
      val pol: PurchaseOrderLoad = new PurchaseOrderLoad
      selectedPO.idStatus match {
        case 0 => newStateID = 1
        case 1 => newStateID = 2
        case 2 => {
          newStateID = 3
          //TODO update no of damaged
          pol.updateDeliverd(selectedPO.idPurchaseOrder_)
        }
        case _ => {
          //TODO add item to inventory Lines
          newStateID = 4
        }
      }

      var alert: Alert = new Alert(AlertType.Confirmation)
      alert.setTitle("Update Status")
      alert.setHeaderText(null)
      alert.setContentText("New status: " + pol.getPurchaseOrderStatusByID(newStateID) + "\nAre you ok with this?")

      var result: Optional[javafx.scene.control.ButtonType] = alert.showAndWait()
      if (result.get() == javafx.scene.control.ButtonType.OK) {
        // ... user chose OK        
        pol.upadteState(selectedPO.idPurchaseOrder_, newStateID)
        stage.hide
        Open(pol.getPurchaseOrderByID(selectedPO.idPurchaseOrder_)(0), employee)
      } else {
        // ... user chose CANCEL or closed the dialog
      }
    }

    /**
     * function to check out an order
     */
    def checkOut: Unit = {
      var newCheckedOut = 0
      val pol: PurchaseOrderLoad = new PurchaseOrderLoad
      if (selectedPO.isCheckedOut) {
        val alert: Alert = new Alert(AlertType.Error)
        alert setTitle ("Error - Can't check out")
        alert setHeaderText (null)
        alert setContentText ("This purchase order has already been checked out!")

        alert showAndWait
      } else {
        pol updateCheckedOut (selectedPO idPurchaseOrder_, 1)
        pol.updateCheckOutBy(selectedPO idPurchaseOrder_, employee)
        stage.hide
        Open(pol.getPurchaseOrderByID(selectedPO idPurchaseOrder_)(0), employee)
      }
    }

    /**
     * function to check in an order
     */
    def checkIn: Unit = {
      var newCheckedOut = 0
      val pol: PurchaseOrderLoad = new PurchaseOrderLoad
      if (selectedPO.isCheckedOut) {
        pol.updateCheckedOut(selectedPO.idPurchaseOrder_, 0)
        stage.hide
        Open(pol.getPurchaseOrderByID(selectedPO.idPurchaseOrder_)(0), employee)
      } else {
      }
    }

    secondaryLayout
  }

  /**
   * open a new frame showing a purchase order's details
   */
  def Open(selectedPO: PurchaseOrder, employee: String): Unit = {
    /**
     * set up and show stage
     */
    val secondScene: Scene = new Scene
    secondScene stylesheets = List(getClass.getResource("controlStyle2.css").toExternalForm)
    secondScene.fill = Color.rgb(109, 158, 104)
    val secondStage: Stage = new Stage
//    secondStage.initStyle(StageStyle.UNDECORATED)
    

    secondScene.getChildren.add(getScene(selectedPO, secondStage, employee))
    secondStage setTitle ("Purchase Order")
    secondStage setScene (secondScene)

    //      secondStage setX (stage.getX() + 250)
    //      secondStage setY (stage.getY() + 100)

    secondStage show ()
  }

}