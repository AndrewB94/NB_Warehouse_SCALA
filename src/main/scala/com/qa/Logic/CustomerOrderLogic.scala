package com.qa.logic

import com.qa.entities.CustomerOrder
import javafx.scene.control.Alert
import scalafx.scene.control.Alert.AlertType
import java.util.Optional
import com.qa.gui.LocationStage
import scalafx.collections.ObservableBuffer
import scalafx.stage.Stage
import com.qa.entities.CustomerOrder
import com.qa.loader.CustomerOrderLoad
import com.qa.gui.IndividualCustomerOrderStage

/**
 * @author abutcher
 * @date 15/11/2015
 * a object used in updating a customer order
 */
object CustomerOrderLogic {
  /**
   * Function that updates the purchaseOrderStatus
   * @param selectedCO : customerOrder to be changed
   * @param stage : Stage being used
   * @param employee : String id of the employee
   */
  def updateStatus(selectedCO: CustomerOrder, stage: Stage, employee: String): Unit = {
    var newStateID = 0
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
    alert.setContentText("New status: " + CustomerOrderLoad.getCustomerOrderStatusByID(newStateID) + "\nAre you ok with this?")

    var result: Optional[javafx.scene.control.ButtonType] = alert.showAndWait()
    if (result.get() == javafx.scene.control.ButtonType.OK) {
      // ... user chose OK        
      CustomerOrderLoad.updateState(selectedCO.idCustomerOrder_, newStateID)
      stage.hide
      IndividualCustomerOrderStage.Open(CustomerOrderLoad.getCustomerOrderByID(selectedCO.idCustomerOrder_)(0), employee)
    }
  }

  /**
   * function to check out an order
   * @param selectedCO : customerOrder to be changed
   * @param employee : String id of the employee
   * @return Boolean : true if updated
   */
  def checkOut(selectedCO: CustomerOrder, employee: String): Boolean = {
    var newCheckedOut = 0
    if (selectedCO.isCheckedOut) {
      false
    } else {
      CustomerOrderLoad updateCheckedOut (selectedCO idCustomerOrder_, true)
      CustomerOrderLoad.updateCheckOutBy(selectedCO idCustomerOrder_, Integer.parseInt(employee))
      true
    }
  }

  /**
   * function to check in an order
   * @param selectedCO : customerOrder to be changed
   * @return Boolean : true if updated
   */
  def checkIn(selectedCO: CustomerOrder): Boolean = {
    var newCheckedOut = 0
    if (selectedCO.isCheckedOut) {
      CustomerOrderLoad.updateCheckedOut(selectedCO.idCustomerOrder_, false)
      true
    } else {
      false
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