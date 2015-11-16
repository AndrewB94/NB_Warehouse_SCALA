package com.qa.entities

import scalafx.beans.property.StringProperty

/**
 * @author abutcher
 * @date 10/11/2105
 * Class that contains all the information that makes up a customer order line
 */
case class CustomerOrderLine(customerOrderID_ : Int, itemID_ : Int, itemName_ : String, quantity_ : Int) {
  /**
   * String properties used in GUI
   */
  val customerOrderID = new StringProperty(this, "ID", "" + customerOrderID_)
  val itemID = new StringProperty(this, "Item ID", "" + itemID_)
  val itemName = new StringProperty(this, "Item Name", itemName_)
  val quantity = new StringProperty(this, "Quantity", "" + quantity_)
}