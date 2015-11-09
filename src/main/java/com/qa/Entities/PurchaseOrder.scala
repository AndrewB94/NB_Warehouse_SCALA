package com.qa.Entities

import java.util.Date;
import scalafx.beans.property.StringProperty

/**
 * @author abutcher
 */
case class PurchaseOrder(purchaseOrderStatus_ :String, supplier_ :String, serialVersionUID:Long, idPurchaseOrder_ :Int, datePlaced_ :Date,dateExpected:Date,  employee:Int) {  
  val status = new StringProperty(this, "Status", purchaseOrderStatus_)
  val id = new StringProperty(this, "ID",""+ idPurchaseOrder_)
  val supplier = new StringProperty(this, "Supplier", supplier_)
  val datePlaced = new StringProperty(this, "datePlaced", datePlaced_.toString)
}