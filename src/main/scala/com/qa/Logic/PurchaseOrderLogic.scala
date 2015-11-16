package com.qa.logic

import com.qa.entities.PurchaseOrder
import com.qa.loader.PurchaseOrderLoad
import scalafx.application.JFXApp.PrimaryStage
import com.qa.gui.IndividualPurchaseOrderStage
import javafx.scene.control.Alert
import scalafx.scene.control.Alert.AlertType
import java.util.Optional
import com.qa.gui.LocationStage
import scalafx.collections.ObservableBuffer
import com.qa.entities.PurchaseOrderLine
import scalafx.stage.Stage

/**
 * @author abutcher
 * @date 15/11/2015
 * a object used in updating a purchase order
 */
object PurchaseOrderLogic {
  /**
   * function to check in an order
   * @param selectedPO : purchaseOrder to be changed
   * @param stage : Stage being used
   * @param employee : String id of the employee
   */
  def checkIn(selectedPO: PurchaseOrder): Boolean = {
    var newCheckedOut = 0
    if (selectedPO.isCheckedOut) {
      PurchaseOrderLoad.updateCheckedOut(selectedPO.idPurchaseOrder_, false)
      true
    } else {
      false
    }
  }

  /**
   * function to check out an order
   * @param selectedPO : purchaseOrder to be changed
   * @param stage : Stage being used
   * @param employee : String id of the employee
   */
  def checkOut(selectedPO: PurchaseOrder, employee: String): Boolean = {
    var newCheckedOut = 0
    if (selectedPO.isCheckedOut) {
      false
    } else {
      PurchaseOrderLoad updateCheckedOut (selectedPO idPurchaseOrder_, true)
      PurchaseOrderLoad.updateCheckOutBy(selectedPO idPurchaseOrder_, Integer.parseInt(employee))
      true
    }
  }

  /**
   * Function that updates the purchaseOrderStatus
   * @param selectedPO : purchaseOrder to be changed
   * @param stage : Stage being used
   * @param employee : String id of the employee
   * @param lines : the order lines
   */
  def updateStatus(selectedPO: PurchaseOrder, stage: Stage, employee: String, lines: ObservableBuffer[PurchaseOrderLine]): Unit = {
    var newStateID = 0
    selectedPO.idStatus match {
      case 0 => newStateID = 1
      case 1 => newStateID = 2
      case 2 => {
        newStateID = 3
        //TODO update no of damaged
        PurchaseOrderLoad.updateDeliverd(selectedPO.idPurchaseOrder_)
      }
      case _ => {
        newStateID = 4
      }
    }
    var alert: Alert = new Alert(AlertType.Confirmation)
    alert.setTitle("Update Status")
    alert.setHeaderText(null)
    alert.setContentText("New status: " + PurchaseOrderLoad.getPurchaseOrderStatusByID(newStateID) + "\nAre you ok with this?")

    var result: Optional[javafx.scene.control.ButtonType] = alert.showAndWait()
    if (result.get() == javafx.scene.control.ButtonType.OK) {
      // ... user chose OK        
      PurchaseOrderLoad.updateState(selectedPO.idPurchaseOrder_, newStateID)
      stage.hide
      IndividualPurchaseOrderStage.Open(PurchaseOrderLoad.getPurchaseOrderByID(selectedPO.idPurchaseOrder_)(0), employee)

      if (newStateID == 4) {
        val storer: LocationStage = new LocationStage
        lines.foreach { x => storer.open(x.itemID_, x.quantity_) }
      }
    }
  }

  /**
   * Function to show an alert box
   * @param alertTitle : String the title of the frame
   * @param alertString : String to display
   */
  def showAlert(alertTitle: String, alertString: String): Unit = {
    val alert: Alert = new Alert(AlertType.Error)
    alert setTitle (alertTitle)
    alert setHeaderText (null)
    alert setContentText (alertString)
    alert showAndWait
  }
}