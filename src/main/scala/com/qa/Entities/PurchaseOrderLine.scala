package com.qa.entities

import scalafx.beans.property.StringProperty

/**
 * @author abutcher
 * @date 10/11/2015
 * class that contains all the information that makes up a purchase order Line
 */
case class PurchaseOrderLine(purchaseOrderID_ : Int, itemID_ : Int, itemName_ : String, quantity_ : Int, quantityDamaged_ : Int) {
  /**
   * String properties used in GUI
   */
  val purchaseOrderID = new StringProperty(this, "ID", "" + purchaseOrderID_)
  val itemID = new StringProperty(this, "Item ID",""+ itemID_)
  val itemName = new StringProperty(this, "Item Name", itemName_)
  val quantity = new StringProperty(this, "Quantity", ""+quantity_)
  val quantityDamaged = new StringProperty(this, "Quantity Damaged", ""+quantityDamaged_)
}