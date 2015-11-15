package com.qa.Tests

import org.scalatest._
import com.qa.TestBase
import com.qa.Loader.PurchaseOrderLineLoad
import scalafx.collections.ObservableBuffer
import com.qa.Entities.PurchaseOrderLine


/**
 * @author abutcher
 */
class PurchaseOrderLineLoadTest extends TestBase {
  "A PurchaseOrderLineLoad" should "craete an observable buffer of PurchaseOrderLines" in {
      val sqlString: String = "SELECT * FROM nbgardensdata.purchaseorderline;"
    assert(PurchaseOrderLineLoad.constructResults(sqlString).isInstanceOf[ObservableBuffer[PurchaseOrderLine]])
  }
  
  it should "create an observable buffer of purchase order lines relating to a purchase order ID" in {
        val result = PurchaseOrderLineLoad.getPurchaseOrderLinesByPurchaseOrderID(2)
    assert(result.isInstanceOf[ObservableBuffer[PurchaseOrderLine]])
    var i = 0
    var isOfID2 = true
    iterate
    def iterate {
      if (i < result.length) {
        if (result(i).purchaseOrderID_ != 2)
          isOfID2 = false
        i.+=(1)
        iterate
      }
    }
    assert(isOfID2)
  }
  
    it should "get a string of an Item name hen given an item ID" in {
    assert(PurchaseOrderLineLoad.getItemNameByItemID(1) == "Grenn Hat Gnome")
  }
}