package com.qa.logic

import com.qa.TestBase
import com.qa.entities.PurchaseOrder
import com.qa.loader.PurchaseOrderLoad
import java.util.Date

/**
 * @author abutcher
 */
class PurchaseOrderLogicTest extends TestBase {
    "A PurchaseOrderLogic" should "check in an order" in {
    val date: Date = new Date()
    val test: PurchaseOrder = new PurchaseOrder("", "", 1L, 1, date, date, 1, 1, true)
    PurchaseOrderLogic.checkIn(test)
    assert(PurchaseOrderLoad.getPurchaseOrderByID(2)(0).isCheckedOut)
  }

  it should "check an order out" in {
    val date: Date = new Date()
    val test: PurchaseOrder = new PurchaseOrder("", "", 1L, 1, date, date, 1, 1, false)
    PurchaseOrderLogic.checkOut(test, "1")
    assert(PurchaseOrderLoad.getPurchaseOrderByID(2)(0).isCheckedOut)
    PurchaseOrderLogic.checkIn(test)
  }
}