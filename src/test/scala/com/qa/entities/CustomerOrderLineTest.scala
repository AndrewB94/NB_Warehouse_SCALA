package com.qa.entities

import com.qa.TestBase

/**
 * @author abutcher
 * @date 16/11/2015
 */
class CustomerOrderLineTest extends TestBase {
  "A CustomerOrderLine" should "be initialized with the correct values entered into the constructors" in {
    val line: CustomerOrderLine = new CustomerOrderLine(1, 1, "Green Gnome", 5)
    assertResult(1)(line.customerOrderID_)
    assertResult(1)(line.itemID_)
    assertResult("Green Gnome")(line.itemName_)
    assertResult(5)(line.quantity_)
  }
}