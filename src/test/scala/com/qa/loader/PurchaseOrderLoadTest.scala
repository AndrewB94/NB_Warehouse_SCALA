package com.qa.loader

import org.scalatest._
import com.qa.TestBase
import com.qa.loader.PurchaseOrderLoad
import scalafx.collections.ObservableBuffer
import com.qa.entities.PurchaseOrder
import com.qa.entities.PurchaseOrder

/**
 * @author abutcher
 * @date 15/11/2015
 * A test class testing the Purchase Order loader
 */
class PurchaseOrderLoadTest extends TestBase {
  "A PurchaseOrderLoad" should "create an observable buffer of purchase orders when given a SELECT query" in {
    val sqlString: String = "SELECT * FROM nbgardensdata.purchaseOrder;"
    assert(PurchaseOrderLoad.constructResults(sqlString).isInstanceOf[ObservableBuffer[PurchaseOrder]])
  }

  it should "create an observable buffer of customer orders" in {
    assert(PurchaseOrderLoad.getAllPurchaseOrders.isInstanceOf[ObservableBuffer[PurchaseOrder]])
  }

  it should "create an observable buffer of purchase orders not including stored orders" in {
    val result = PurchaseOrderLoad.getAllNotStoredPurchaseOrders
    assert(result.isInstanceOf[ObservableBuffer[PurchaseOrder]])
    var i = 0
    var isNotStored = true
    iterate
    def iterate {
      if (i < result.length) {
        if (result(i).purchaseOrderStatus_ == 4)
          isNotStored = false
        i.+=(1)
        iterate
      }
    }
    assert(isNotStored)
  }

  it should "create an observable buffer of one purchase order" in {
    val result = PurchaseOrderLoad.getPurchaseOrderByID(2)
    assert(result.isInstanceOf[ObservableBuffer[PurchaseOrder]])
    assert(result.length < 2)
    var i = 0
    var isNotStored = true
    iterate
    def iterate {
      if (i < result.length) {
        if (result(i).idPurchaseOrder_ != 2)
          isNotStored = false
        i.+=(1)
        iterate
      }
    }
    assert(isNotStored)
  }

  it should "get a string representing the status of a given ID" in {
    val result = PurchaseOrderLoad.getPurchaseOrderStatusByID(1)
    assert(result.isInstanceOf[String])
    assert(result == "CREATED")
  }

  it should "get a string representing the name of a supplier given ID" in {
    val result = PurchaseOrderLoad.getSupplierByID(1)
    assert(result.isInstanceOf[String])
    assert(result == "Van Hage")
  }

  it should "uspdate the status of a customer order" in {
    val testEntry = PurchaseOrderLoad.getPurchaseOrderByID(2)
    PurchaseOrderLoad.updateState(2, 1)
    assert(PurchaseOrderLoad.getPurchaseOrderByID(2)(0).idStatus == 1)
    PurchaseOrderLoad.updateState(2, testEntry(0).idStatus)
  }

  it should "update the isCheckOut boolen " in {
    val testEntry = PurchaseOrderLoad.getPurchaseOrderByID(2)
    PurchaseOrderLoad.updateCheckedOut(2, true)
    assert(PurchaseOrderLoad.getPurchaseOrderByID(2)(0).isCheckedOut == true)
    PurchaseOrderLoad.updateCheckedOut(2, testEntry(0).isCheckedOut)
  }

  it should "update the id of employee lastCheckedOutBy" in {
    val testEntry = PurchaseOrderLoad.getPurchaseOrderByID(2)
    PurchaseOrderLoad.updateCheckOutBy(2, 1)
    assert(PurchaseOrderLoad.getPurchaseOrderByID(2)(0).employee == 1)
    PurchaseOrderLoad.updateCheckOutBy(2, testEntry(0).employee)
  }
}