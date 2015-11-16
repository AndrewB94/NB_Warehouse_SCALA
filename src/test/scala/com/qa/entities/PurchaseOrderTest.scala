package com.qa.entities

import com.qa.TestBase
import java.util.Date

/**
 * @author abutcher
 * @Date 16/11/2015
 */
class PurchaseOrderTest extends TestBase {
    "A PurchaseOrder" should "be initialized with the correct values entered into the constructors" in {
      val date:Date = new Date
      val po:PurchaseOrder = new PurchaseOrder("CREATED", "Van Hage", 1L, 1, date, date, 1,1,false)
      
      assertResult("CREATED")(po.purchaseOrderStatus_)
      assertResult("Van Hage")(po.supplier_)
      assertResult(1L)(po.serialVersionUID)
      assertResult(1)(po.idPurchaseOrder_)
      assertResult(date)(po.datePlaced_)
      assertResult(date)(po.dateExpected)
      assertResult(1)(po.employee)
      assertResult(1)(po.idStatus)
      assertResult(false)(po.isCheckedOut)
    }
}