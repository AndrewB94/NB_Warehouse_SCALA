package com.qa.gui

import com.qa.gui._
import com.qa.loader.CustomerOrderLoad
import com.qa.loader.PurchaseOrderLineLoad
import com.qa.entities.PurchaseOrderLine
import com.qa.entities.PurchaseOrder
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
import scalafx.scene.Scene
import scalafx.stage.StageStyle
import scalafx.scene.paint.Color
import scalafx.collections.ObservableBuffer
import scalafx.application.JFXApp.PrimaryStage
import com.qa.logic.PurchaseOrderLogic

/**
 * @author abutcher
 * @date 10/11/2015
 * class for creating a frame for displaying purchase orders
 */
object IndividualPurchaseOrderStage {

  /**
   * Function to create the scene for a purchase order
   * @param selectedPO : PurchaseOrder the currently selected order
   * @param stage : Stage the stage on which the scene is built
   * @param employee : String employee currently logged in
   * @return Node : the node containing the scene
   */
  def getScene(selectedPO: PurchaseOrder, stage: Stage, employee: String): Node = {
    val secondaryLayout: BorderPane = new BorderPane
    val secondLabel: Label = new Label("Purchase Order - ID: " + selectedPO.idPurchaseOrder_)
    secondLabel setFont (Font.font("Verdana", 30))

    secondaryLayout.top = secondLabel
    secondaryLayout.center = createContentPane(selectedPO, stage, employee)

    var grid: GridPane = new GridPane
    grid setHgap (10)
    grid setVgap (10)
    grid setPadding (new Insets(0, 10, 10, 10))
    grid.add(secondaryLayout, 1, 1)
    grid
  }

  /**
   * Function to create a table of customer order lines
   * @param selectedPO : PurchaseOrder the currently selected order
   * @param lines : ObservableBuffer[PurchaseOrderLine] the customer order lines
   * @return TableView[CustomerOrderLine] : the table of lines
   */
  def createTable(selectedPO: PurchaseOrder, lines: ObservableBuffer[PurchaseOrderLine]): TableView[PurchaseOrderLine] = {
    var itemIDCollumn = new TableColumn[PurchaseOrderLine, String] {
      text = "Item ID"
      cellValueFactory = { _.value itemID }
      prefWidth = 100
    }

    var itemNmaeCollumn = new TableColumn[PurchaseOrderLine, String] {
      text = "Item Name"
      cellValueFactory = { _.value itemName }
      prefWidth = 250
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

    val lineTable = new TableView[PurchaseOrderLine](lines) {
      columns ++= List(itemIDCollumn, itemNmaeCollumn, quantityCollumn, quantityDamagedCollumn)
    }
    lineTable
  }

  /**
   * function to create a gridPane containing control buttons
   * @param selectedPO : PurchaseOrder the currently selected order
   * @param stage : Stage the stage on which the scene is built
   * @param employee : String employee currently logged in
   * @return GridPane : containing the buttons
   */
  def createButtonPane(selectedPO: PurchaseOrder, stage: Stage, employee: String, lines: ObservableBuffer[PurchaseOrderLine]): Node = {
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
    updateB onAction = { ae: ActionEvent => PurchaseOrderLogic updateStatus (selectedPO, stage, employee, lines) }
    checkOutB onAction = { ae: ActionEvent => PurchaseOrderLogic checkOut (selectedPO, stage, employee) }
    checkInB onAction = { ae: ActionEvent => PurchaseOrderLogic checkIn (selectedPO, stage, employee) }

    if (!selectedPO.isCheckedOut)
      checkInB.setDisable(true)

    if (selectedPO.isCheckedOut || selectedPO.idStatus == 4)
      checkOutB.setDisable(true)

    if (!selectedPO.isCheckedOut || selectedPO.idStatus == 4)
      updateB.setDisable(true)

    if (selectedPO.isCheckedOut)
      closeB.setDisable(true)

    grid.add(checkInB, 1, 1)
    grid.add(checkOutB, 2, 1)
    grid.add(updateB, 1, 2)
    grid.add(closeB, 2, 2)
    grid
  }

  /**
   * Function to create the content for the frame
   * @param selectedPO : PurchaseOrder the currently selected order
   * @param stage : Stage the stage on which the scene is built
   * @param employee : String employee currently logged in
   * @return GridPane : containing the content
   */
  def createContentPane(selectedPO: PurchaseOrder, stage: Stage, employee: String): GridPane = {
    val lines = PurchaseOrderLineLoad.getPurchaseOrderLinesByPurchaseOrderID(selectedPO.idPurchaseOrder_)
    val contentPane: GridPane = new GridPane
    val table = createTable(selectedPO, lines)
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
    val employeeL: Label = new Label("Last Checked Out By:")
    employeeL setFont (Font.font("Arial", FontWeight.BOLD, 20))

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
    val employeeV: Label = new Label(CustomerOrderLoad.getUserByID(selectedPO.employee))
    employeeV setFont (Font.font("Arial", 20))

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
    contentPane add (table, 3, 1, 1, 7)
    contentPane add (createButtonPane(selectedPO, stage, employee, lines), 1, 7, 2, 1)
    contentPane
  }

  /**
   * open a new frame showing a purchase order's details
   * @param selectedPO : PurchaseOrder the currently selected order
   * @param employee : String employee currently logged in
   */
  def Open(selectedPO: PurchaseOrder, employee: String): Unit = {
    val secondScene: Scene = new Scene
    secondScene stylesheets = List(getClass.getResource("/controlStyle2.css").toExternalForm)
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