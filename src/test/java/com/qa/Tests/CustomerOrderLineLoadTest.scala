package com.qa.Tests

import org.scalatest._
import com.qa.TestBase
import scalafx.collections.ObservableBuffer
import com.qa.Entities.CustomerOrderLine
import com.qa.Loader.CustomerOrderLineLoad

/**
 * @author abutcher
 * @date 13/11/2015
 * A test class to test the customer order line loader
 */
class CustomerOrderLineLoadTest extends TestBase {
  "A CustomerOrderLineLoad" should "create an observable buffer of customer order lines" in {
    val sqlString: String = "SELECT * FROM nbgardensdata.customerorderLine;"
    assert(CustomerOrderLineLoad.constructResults(sqlString).isInstanceOf[ObservableBuffer[CustomerOrderLine]])
  }

  it should "create an observable buffer of customer order lines relating to a customer order ID" in {
    val result = CustomerOrderLineLoad.getCustomerOrderLinesByCustomerOrderID(2)
    assert(result.isInstanceOf[ObservableBuffer[CustomerOrderLine]])
    var i = 0
    var isOfID2 = true
    iterate
    def iterate {
      if (i < result.length) {
        if (result(i).customerOrderID_ != 2)
          isOfID2 = false
        i.+=(1)
        iterate
      }
    }
    assert(isOfID2)
  }

  it should "get a string of an Item name hen given an item ID" in {
    assert(CustomerOrderLineLoad.getItemNameByItemID(1) == "Grenn Hat Gnome")
  }
}