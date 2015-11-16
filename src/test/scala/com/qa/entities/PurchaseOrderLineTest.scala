package com.qa.entities

import com.qa.TestBase

/**
 * @author abutcher
 * @date 16/11/2015
 */
class PurchaseOrderLineTest extends TestBase{
    "A PurchaseOrderLine" should "be initialized with the correct values entered into the constructors" in {
    val line: PurchaseOrderLine = new PurchaseOrderLine(1, 1, "Green Gnome", 5, 0)
    assertResult(1)(line.purchaseOrderID_)
    assertResult(1)(line.itemID_)
    assertResult("Green Gnome")(line.itemName_)
    assertResult(5)(line.quantity_)
    assertResult(0)(line.quantityDamaged_)
    }
}