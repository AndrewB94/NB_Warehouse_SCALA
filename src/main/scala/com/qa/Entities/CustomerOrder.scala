package com.qa.entities

import java.util.Date;
import scalafx.beans.property.StringProperty

/**
 * @author abutcher
 * @date 06/11/2015
 * class that contains all the information that makes up a customer order
 */
case class CustomerOrder(customerOrderStatus_ :String, datePlaced_ :Date, customer_ :String, employee_ :Int, isPaid_ :Boolean, addressId_ :Int, serialVersionUID:Long, idCustomerOrder_ :Int, dateShipped:Date, isCheckedOut:Boolean, customerOrderStatusID:Int) {
  
  /**
   * String properties used in GUI
   */
  val status = new StringProperty(this, "Status", customerOrderStatus_)
  val id = new StringProperty(this, "ID",""+ idCustomerOrder_)
  val datePlaced = new StringProperty(this, "datePlaced", datePlaced_.toString)
  val customer = new StringProperty(this, "customer", customer_)
}