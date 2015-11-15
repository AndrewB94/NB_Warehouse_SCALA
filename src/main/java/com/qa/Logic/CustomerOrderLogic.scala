package com.qa.Logic

import com.qa.Entities.CustomerOrder
import javafx.scene.control.Alert
import scalafx.scene.control.Alert.AlertType
import java.util.Optional
import com.qa.GUI.LocationStage
import scalafx.collections.ObservableBuffer
import scalafx.stage.Stage
import com.qa.Entities.CustomerOrder
import com.qa.Loader.CustomerOrderLoad
import com.qa.GUI.IndividualCustomerOrderStage


/**
 * @author abutcher
 */
object CustomerOrderLogic {
    /**
     * Function that updates the purchaseOrderStatus
     */
    def updateStatus(selectedCO:CustomerOrder, stage:Stage, employee:String): Unit = {
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
      } else {
        // ... user chose CANCEL or closed the dialog
      }
    }

    /**
     * function to check out an order
     */
    def checkOut(selectedCO:CustomerOrder, stage:Stage, employee:String): Unit = {
      var newCheckedOut = 0
      if (selectedCO.isCheckedOut) {
        val alert: Alert = new Alert(AlertType.Error)
        alert setTitle ("Error - Can't check out")
        alert setHeaderText (null)
        alert setContentText ("This customer order has already been checked out!")

        alert showAndWait
      } else {
        CustomerOrderLoad updateCheckedOut (selectedCO idCustomerOrder_, true)
        CustomerOrderLoad.updateCheckOutBy(selectedCO idCustomerOrder_, Integer.parseInt(employee))
        stage.hide
        IndividualCustomerOrderStage.Open(CustomerOrderLoad.getCustomerOrderByID(selectedCO idCustomerOrder_)(0), employee)
      }
    }

    /**
     * function to check in an order
     */
    def checkIn(selectedCO:CustomerOrder, stage:Stage, employee:String): Unit = {
      var newCheckedOut = 0
      if (selectedCO.isCheckedOut) {
        CustomerOrderLoad.updateCheckedOut(selectedCO.idCustomerOrder_, false)
        stage.hide
        IndividualCustomerOrderStage.Open(CustomerOrderLoad.getCustomerOrderByID(selectedCO idCustomerOrder_)(0), employee)
      } else {
      }
    }

}