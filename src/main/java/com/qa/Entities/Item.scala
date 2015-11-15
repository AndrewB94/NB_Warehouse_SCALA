package com.qa.Entities

import scalafx.beans.property.StringProperty

/**
 * @author abutcher
 * @date 12/11/2015
 * Class that contains all the information that makes up a Item
 */
case class Item(itemID_ : Int, itemName_ : String, quantity_ : Int, supplier_ : String) {
    /**
   * String properties used in GUI
   */
  val name = new StringProperty(this, "name", itemName_)
  val id = new StringProperty(this, "ID", "" + itemID_)
  val quantity = new StringProperty(this, "quantity", "" + quantity_)
  val supplier = new StringProperty(this, "supplier", supplier_)
}