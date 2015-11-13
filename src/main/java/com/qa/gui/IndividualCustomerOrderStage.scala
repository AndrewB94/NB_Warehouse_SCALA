package com.qa.gui

import com.qa.Entities.CustomerOrder
import com.qa.Entities.CustomerOrderLine
import scalafx.stage.Stage
import scalafx.scene.Node
import com.qa.loader.CustomerOrderLoad
import com.qa.loader.CustomerOrderLineLoad
import scalafx.scene.control._
import scalafx.scene.layout.BorderPane
import com.qa.loader.CustomerOrderLineLoad
import scalafx.scene.layout.GridPane
import javafx.geometry.Insets
import scalafx.scene.text.Font
import scalafx.scene.text.FontWeight
import scalafx.event.ActionEvent
import scalafx.scene.control.Alert.AlertType
import java.util.Optional
import javafx.scene.control.Alert
import scalafx.scene.Scene
import com.qa.Entities.CustomerOrder
import scalafx.stage.StageStyle
import scalafx.Includes._
import scalafx.scene.paint.Color

/**
 * @author abutcher
 */
class IndividualCustomerOrderStage {
  def getScene(selectedCO: CustomerOrder, stage: Stage, employee: String): Node = {
    /**
     * Initialize values
     */
    val coL: CustomerOrderLoad = new CustomerOrderLoad
    val coll: CustomerOrderLineLoad = new CustomerOrderLineLoad
    val secondLabel: Label = new Label("Customer Order - ID: " + selectedCO.idCustomerOrder_)
    val secondaryLayout: BorderPane = new BorderPane
    val lines = coll.getCustomerOrderLinesByCustomerOrderID(selectedCO.idCustomerOrder_)
    var lineTable = new TableView[CustomerOrderLine](lines)

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
    val customerL: Label = new Label("Customer:")
    customerL setFont (Font.font("Arial", FontWeight.BOLD, 20))
    val employeeL: Label = new Label("Last Checked Out By:")
    employeeL setFont (Font.font("Arial", FontWeight.BOLD, 20))

    /**
     * Set up value labels
     */
    val idV: Label = new Label("" + selectedCO.idCustomerOrder_)
    idV setFont (Font.font("Arial", 20))
    val statusV: Label = new Label(selectedCO.customerOrderStatus_)
    statusV setFont (Font.font("Arial", 20))
    val datePlacedV: Label = new Label("" + selectedCO.datePlaced_)
    datePlacedV setFont (Font.font("Arial", 20))
    val customerV: Label = new Label("" + selectedCO.customer_)
    customerV setFont (Font.font("Arial", 20))
    val employeeV: Label = new Label(coL.getUserByID(selectedCO.employee_))
    employeeV setFont (Font.font("Arial", 20))

    /**
     * set up table columns
     */
    var itemIDCollumn = new TableColumn[CustomerOrderLine, String] {
      text = "Item ID"
      cellValueFactory = { _.value itemID }
      prefWidth = 100
    }

    var itemNmaeCollumn = new TableColumn[CustomerOrderLine, String] {
      text = "Item Name"
      cellValueFactory = { _.value itemName }
      prefWidth = 250
    }

    var quantityCollumn = new TableColumn[CustomerOrderLine, String] {
      text = "Quantity"
      cellValueFactory = { _.value quantity }
      prefWidth = 200
    }

    /**
     * Create table and add collumns to it
     */
    lineTable = new TableView[CustomerOrderLine](lines) {
      columns ++= List(itemIDCollumn, itemNmaeCollumn, quantityCollumn)
    }

    /**
     * add componenents to grid pane
     */
    contentPane add (idLabel, 1, 1)
    contentPane add (statusL, 1, 2)
    contentPane add (datePlacedL, 1, 3)
    contentPane add (customerL, 1, 4)
    contentPane add (employeeL, 1, 5)

    contentPane add (idV, 2, 1)
    contentPane add (statusV, 2, 2)
    contentPane add (datePlacedV, 2, 3)
    contentPane add (customerV, 2, 4)
    contentPane add (employeeV, 2, 5)
    contentPane add (lineTable, 3, 1, 1, 6)
    contentPane add (buttonPane, 1, 6, 2, 1)
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
      val travelB: Button = new Button("Find Products")
      val closeB: Button = new Button("Close")
      val updateB: Button = new Button("Update State")
      val checkInB: Button = new Button("Check In Order")
      val checkOutB: Button = new Button("Check Out Order")

      /**
       * set up action event on buttons
       */
      travelB onAction = { ae: ActionEvent =>
        val ts = new TravelScene
        ts.open(selectedCO, employee)
      }
      closeB onAction = { ae: ActionEvent => stage.hide }
      updateB onAction = { ae: ActionEvent => updateStatus }
      checkOutB onAction = { ae: ActionEvent => checkOut }
      checkInB onAction = { ae: ActionEvent => checkIn }

      /**
       * Disable/Enable buttons
       */
      if (!selectedCO.isCheckedOut)
        checkInB.setDisable(true)

      if (selectedCO.isCheckedOut || selectedCO.customerOrderStatus_ == 6)
        checkOutB.setDisable(true)

      if (!selectedCO.isCheckedOut || selectedCO.customerOrderStatus_ == 6)
        updateB.setDisable(true)

      if (selectedCO.isCheckedOut)
        closeB.setDisable(true)

        travelB.prefWidth = 510
        closeB.prefWidth = 250
        checkInB.prefWidth = 250
        checkOutB.prefWidth = 250
        updateB.prefWidth = 250
        
      /**
       * add componenents to grid pane
       */
      grid.add(travelB, 1, 1, 2, 1)
      grid.add(checkInB, 1, 2)
      grid.add(checkOutB, 2, 2)
      grid.add(updateB, 1, 3)
      grid.add(closeB, 2, 3)
      grid
    }

    /**
     * Function that updates the purchaseOrderStatus
     */
    def updateStatus: Unit = {
      var newStateID = 0
      val col: CustomerOrderLoad = new CustomerOrderLoad
      selectedCO.customerOrderStatusID match {
        case 0 => newStateID = 1
        case 1 => newStateID = 2
        case 2 => newStateID = 3
        case 3 => newStateID = 4
        case 4 => newStateID = 5
        case _ => newStateID = 6
      }

      var alert: Alert = new Alert(AlertType.Confirmation)
      alert.setTitle("Update Status")
      alert.setHeaderText(null)
      alert.setContentText("New status: " + col.getCustomerOrderStatusByID(newStateID) + "\nAre you ok with this?")

      var result: Optional[javafx.scene.control.ButtonType] = alert.showAndWait()
      if (result.get() == javafx.scene.control.ButtonType.OK) {
        // ... user chose OK        
        col.updateState(selectedCO.idCustomerOrder_, newStateID)
        stage.hide
        Open(col.getCustomerOrderByID(selectedCO.idCustomerOrder_)(0), employee)
      } else {
        // ... user chose CANCEL or closed the dialog
      }
    }

    /**
     * function to check out an order
     */
    def checkOut: Unit = {
      var newCheckedOut = 0
      val col: CustomerOrderLoad = new CustomerOrderLoad
      if (selectedCO.isCheckedOut) {
        val alert: Alert = new Alert(AlertType.Error)
        alert setTitle ("Error - Can't check out")
        alert setHeaderText (null)
        alert setContentText ("This customer order has already been checked out!")

        alert showAndWait
      } else {
        col updateCheckedOut (selectedCO idCustomerOrder_, 1)
        col.updateCheckOutBy(selectedCO idCustomerOrder_, employee)
        stage.hide
        Open(col.getCustomerOrderByID(selectedCO idCustomerOrder_)(0), employee)
      }
    }

    /**
     * function to check in an order
     */
    def checkIn: Unit = {
      var newCheckedOut = 0
      val col: CustomerOrderLoad = new CustomerOrderLoad
      if (selectedCO.isCheckedOut) {
        col.updateCheckedOut(selectedCO.idCustomerOrder_, 0)
        stage.hide
        Open(col.getCustomerOrderByID(selectedCO idCustomerOrder_)(0), employee)
      } else {
      }
    }
     var grid: GridPane = new GridPane
      grid setHgap (10)
      grid setVgap (10)
      grid setPadding (new Insets(0, 10, 10, 10))
      grid.add(secondaryLayout, 1, 1)
    grid
  }

  /**
   * open a new frame showing a purchase order's details
   */
  def Open(selectedCO: CustomerOrder, employee: String): Unit = {
    /**
     * set up and show stage
     */
    val secondScene: Scene = new Scene
    secondScene stylesheets = List(getClass.getResource("/controlStyle2.css").toExternalForm)    
    secondScene.fill = Color.rgb(109, 158, 104)
    val secondStage: Stage = new Stage
//    secondStage.initStyle(StageStyle.UNDECORATED)

    secondScene.getChildren.add(getScene(selectedCO, secondStage, employee))
    secondStage setTitle ("Purchase Order")
    secondStage setScene (secondScene)

    //      secondStage setX (stage.getX() + 250)
    //      secondStage setY (stage.getY() + 100)

    secondStage show ()
  }
}