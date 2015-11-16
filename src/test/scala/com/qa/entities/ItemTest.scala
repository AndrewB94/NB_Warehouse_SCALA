package com.qa.entities

import com.qa.TestBase

/**
 * @author abutcher
 * @date 16/11/2015
 */
class ItemTest extends TestBase {
  "An Item" should "be initialized with the correct values entered into the constructors" in {
    val item:Item = new Item(1, "Green Gnome", 5, "2")
    
    assertResult(1)(item.itemID_)
    assertResult("Green Gnome")(item.itemName_)
    assertResult(5)(item.quantity_)
    assertResult("2")(item.supplier_)
  }
}