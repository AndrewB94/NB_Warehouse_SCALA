package com.qa.Entities

import scalafx.beans.property.StringProperty

/**
 * @author abutcher
 */
class CustomerOrderLine(customerOrderID_ : Int, itemID_ : Int, itemName_ : String, quantity_ : Int) {
    /**
   * String properties used in GUI
   */
  val customerOrderID = new StringProperty(this, "ID", "" + customerOrderID_)
  val itemID = new StringProperty(this, "Item ID",""+ itemID_)
  val itemName = new StringProperty(this, "Item Name", itemName_)
  val quantity = new StringProperty(this, "Quantity", ""+quantity_)
}