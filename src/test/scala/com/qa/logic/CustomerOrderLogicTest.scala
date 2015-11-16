package com.qa.logic

import com.qa.TestBase
import com.qa.entities.CustomerOrder
import com.qa.loader.CustomerOrderLoad
import java.util.Date

/**
 * @author abutcher
 * @date 16/11/2015
 * A class to test customer order logic class
 */
class CustomerOrderLogicTest extends TestBase {
  "A CustomerOrderLogic" should "check in an order" in {
    val date: Date = new Date()
    val test: CustomerOrder = new CustomerOrder("", date, "", 0, true, 0, 1L, 1, null, true, 2)
    CustomerOrderLogic.checkIn(test)
    assert(!CustomerOrderLoad.getCustomerOrderByID(2)(0).isCheckedOut)
  }

  it should "check an order out" in {
    val date: Date = new Date()
    val test: CustomerOrder = new CustomerOrder("", date, "", 0, true, 0, 1L, 1, null, false, 2)
    CustomerOrderLogic.checkOut(test, "1")
    assert(!CustomerOrderLoad.getCustomerOrderByID(2)(0).isCheckedOut)
    CustomerOrderLogic.checkIn(test)
  }
}