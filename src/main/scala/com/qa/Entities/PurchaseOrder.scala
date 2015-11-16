package com.qa.Entities

import java.util.Date;
import scalafx.beans.property.StringProperty

/**
 * @author abutcher
 * @date 03/11/2015
 * class that contains all the information that makes up a customer order
 */
case class PurchaseOrder(purchaseOrderStatus_ :String, supplier_ :String, serialVersionUID:Long, idPurchaseOrder_ :Int, datePlaced_ :Date,dateExpected:Date,  employee:Int, idStatus:Int, isCheckedOut:Boolean) {  
    /**
   * String properties used in GUI
   */
  val status = new StringProperty(this, "Status", purchaseOrderStatus_)
  val id = new StringProperty(this, "ID",""+ idPurchaseOrder_)
  val supplier = new StringProperty(this, "Supplier", supplier_)
  val datePlaced = new StringProperty(this, "datePlaced", datePlaced_.toString)
}