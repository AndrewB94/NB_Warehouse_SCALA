package com.qa.gui

import com.qa.entities.CustomerOrder
import com.qa.entities.CustomerOrderLine
import scalafx.stage.Stage
import scalafx.scene.Node
import com.qa.loader.CustomerOrderLoad
import com.qa.loader.CustomerOrderLineLoad
import scalafx.scene.control._
import scalafx.scene.layout.BorderPane
import scalafx.scene.layout.GridPane
import javafx.geometry.Insets
import scalafx.scene.text.Font
import scalafx.scene.text.FontWeight
import scalafx.event.ActionEvent
import scalafx.scene.control.Alert.AlertType
import java.util.Optional
import javafx.scene.control.Alert
import scalafx.scene.Scene
import scalafx.stage.StageStyle
import scalafx.Includes._
import scalafx.scene.paint.Color
import com.qa.logic.CustomerOrderLogic
import scalafx.collections.ObservableBuffer

/**
 * @author abutcher
 * @date 11/11/2015
 * An object that displays all the information about a customer order
 */
object IndividualCustomerOrderStage {

  /**
   * Function to create the scene for a customer order
   * @param selectedCO : CustomerOrder the currently selected order
   * @param stage : Stage the stage on which the scene is built
   * @param employee : String employee currently logged in
   * @return Node : the node containing the scene
   */
  def getScene(selectedCO: CustomerOrder, stage: Stage, employee: String): Node = {

    val secondaryLayout: BorderPane = new BorderPane
    val secondLabel: Label = new Label("Purchase Order - ID: " + selectedCO.idCustomerOrder_)
    secondLabel setFont (Font.font("Verdana", 30))

    secondaryLayout.top = secondLabel
    secondaryLayout.center = createContentPane(selectedCO, stage, employee)

    var grid: GridPane = new GridPane
    grid setHgap (10)
    grid setVgap (10)
    grid setPadding (new Insets(0, 10, 10, 10))
    grid.add(secondaryLayout, 1, 1)
    grid

  }

  /**
   * Function to create a table of customer order lines
   * @param selectedCO : CustomerOrder the currently selected order
   * @param lines : ObservableBuffer[CustomerOrderLine] the customer order lines
   * @return TableView[CustomerOrderLine] : the table of lines
   */
  def createTable(SelectedCO: CustomerOrder, lines: ObservableBuffer[CustomerOrderLine]): TableView[CustomerOrderLine] = {

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

    val lineTable = new TableView[CustomerOrderLine](lines) {
      columns ++= List(itemIDCollumn, itemNmaeCollumn, quantityCollumn)
    }
    lineTable
  }

  /**
   * function to create a gridPane containing control buttons
   * @param selectedCO : CustomerOrder the currently selected order
   * @param stage : Stage the stage on which the scene is built
   * @param employee : String employee currently logged in
   * @return GridPane : containing the buttons
   */
  def createButtonPane(selectedCO: CustomerOrder, stage: Stage, employee: String): GridPane = {
    val grid: GridPane = new GridPane
    grid setHgap (10)
    grid setVgap (10)
    grid setPadding (new Insets(0, 10, 0, 10))
    val travelB: Button = new Button("Find Products")
    val closeB: Button = new Button("Close")
    val updateB: Button = new Button("Update State")
    val checkInB: Button = new Button("Check In Order")
    val checkOutB: Button = new Button("Check Out Order")

    travelB onAction = { ae: ActionEvent => TravelScene.open(selectedCO, employee) }
    closeB onAction = { ae: ActionEvent => stage.hide }
    updateB onAction = { ae: ActionEvent => CustomerOrderLogic.updateStatus(selectedCO, stage, employee) }
    checkOutB onAction = { ae: ActionEvent =>
      if (CustomerOrderLogic.checkOut(selectedCO, employee)) {
        stage.hide
        IndividualCustomerOrderStage.Open(CustomerOrderLoad.getCustomerOrderByID(selectedCO idCustomerOrder_)(0), employee)
      } else {
        CustomerOrderLogic.showAlert("Error - Can't check out", "This customer order has already been checked out!")
      }
    }
    checkInB onAction = { ae: ActionEvent => if(!CustomerOrderLogic.checkIn(selectedCO)) {
      stage.hide
      IndividualCustomerOrderStage.Open(CustomerOrderLoad.getCustomerOrderByID(selectedCO idCustomerOrder_)(0), employee)
    } }

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

    grid.add(travelB, 1, 1, 2, 1)
    grid.add(checkInB, 1, 2)
    grid.add(checkOutB, 2, 2)
    grid.add(updateB, 1, 3)
    grid.add(closeB, 2, 3)
    grid
  }
  /**
   * Function to create the content for the frame
   * @param selectedCO : CustomerOrder the currently selected order
   * @param stage : Stage the stage on which the scene is built
   * @param employee : String employee currently logged in
   * @return GridPane : containing the content
   */
  def createContentPane(selectedCO: CustomerOrder, stage: Stage, employee: String): GridPane = {
    val secondLabel: Label = new Label("Customer Order - ID: " + selectedCO.idCustomerOrder_)
    val secondaryLayout: BorderPane = new BorderPane
    val lines = CustomerOrderLineLoad.getCustomerOrderLinesByCustomerOrderID(selectedCO.idCustomerOrder_)
    val lineTable = createTable(selectedCO, lines)

    val contentPane: GridPane = new GridPane
    contentPane setHgap (10)
    contentPane setVgap (10)
    contentPane setPadding (new Insets(0, 10, 0, 10))

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

    val idV: Label = new Label("" + selectedCO.idCustomerOrder_)
    idV setFont (Font.font("Arial", 20))
    val statusV: Label = new Label(selectedCO.customerOrderStatus_)
    statusV setFont (Font.font("Arial", 20))
    val datePlacedV: Label = new Label("" + selectedCO.datePlaced_)
    datePlacedV setFont (Font.font("Arial", 20))
    val customerV: Label = new Label("" + selectedCO.customer_)
    customerV setFont (Font.font("Arial", 20))
    val employeeV: Label = new Label(CustomerOrderLoad.getUserByID(selectedCO.employee_))
    employeeV setFont (Font.font("Arial", 20))

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
    contentPane add (createButtonPane(selectedCO, stage, employee), 1, 6, 2, 1)
    secondLabel setFont (Font.font("Verdana", 30))

    contentPane
  }

  /**
   * open a new frame showing a purchase order's details
   * @param selectedCO : CustomerOrder the currently selected order
   * @param employee : String employee currently logged in
   */
  def Open(selectedCO: CustomerOrder, employee: String): Unit = {
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