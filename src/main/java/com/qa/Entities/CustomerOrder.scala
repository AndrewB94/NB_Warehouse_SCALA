package com.qa.Entities

import java.util.Date;
import scalafx.beans.property.StringProperty

/**
 * @author abutcher
 * @date 06/11/2015
 */
class CustomerOrder(customerOrderStatus_ :String, datePlaced_ :Date, customerID_ :Int, employee_ :Int, isPaid_ :Boolean, addressId_ :Int, serialVersionUID:Long, idCustomerOrder_ :Int, dateShipped:Date) {
  val status = new StringProperty(this, "Status", customerOrderStatus_)
  val id = new StringProperty(this, "ID",""+ idCustomerOrder_)
  val datePlaced = new StringProperty(this, "datePlaced", datePlaced_.toString)
  val customer = new StringProperty(this, "customer", customerID_ toString)
}