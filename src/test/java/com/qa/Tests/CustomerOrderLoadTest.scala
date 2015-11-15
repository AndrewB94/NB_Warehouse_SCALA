package com.qa.Tests

import org.scalatest._
import com.qa.TestBase
import com.qa.Loader.CustomerOrderLoad
import scalafx.collections.ObservableBuffer
import com.qa.Entities.CustomerOrder

/**
 * @author abutcher
 * @date 13/11/2015
 * A test class testing the Customer Order Loader
 */
class CustomerOrderLoadTest extends TestBase {
  "A CustomerOrderLoad" should "create an observable buffer of customer orders when given a SELECT query" in {
    val sqlString: String = "SELECT * FROM nbgardensdata.customerorder;"
    assert(CustomerOrderLoad.constructResults(sqlString).isInstanceOf[ObservableBuffer[CustomerOrder]])
  }

  it should "create an observable buffer of customer orders" in {
    assert(CustomerOrderLoad.getAllCustomerOrders.isInstanceOf[ObservableBuffer[CustomerOrder]])
  }

  it should "create an observable buffer of customer orders not including dispatched orders" in {
    val result = CustomerOrderLoad.getAllNotDispatchedCustomerOrders
    assert(result.isInstanceOf[ObservableBuffer[CustomerOrder]])
    var i = 0
    var isNotDispatched = true
    iterate
    def iterate {
      if (i < result.length) {
        if (result(i).customerOrderStatusID == 6)
          isNotDispatched = false
        i.+=(1)
        iterate
      }
    }
    assert(isNotDispatched)
  }

  it should "create an observable buffer of one customer order" in {
    val result = CustomerOrderLoad.getCustomerOrderByID(2)
    assert(result.isInstanceOf[ObservableBuffer[CustomerOrder]])
    assert(result.length < 2)
    var i = 0
    var isNotDispatched = true
    iterate
    def iterate {
      if (i < result.length) {
        if (result(i).idCustomerOrder_ != 2)
          isNotDispatched = false
        i.+=(1)
        iterate
      }
    }
    assert(isNotDispatched)
  }

  it should "get a string representing the status of a given ID" in {
    val result = CustomerOrderLoad.getCustomerOrderStatusByID(1)
    assert(result.isInstanceOf[String])
    assert(result == "PLACED")
  }

  it should "get a string representing the name of a user given ID" in {
    val result = CustomerOrderLoad.getUserByID(1)
    assert(result.isInstanceOf[String])
    assert(result == "Al Stock")
  }

  it should "uspdate the status of a customer order" in {
    val testEntry = CustomerOrderLoad.getCustomerOrderByID(2)
    CustomerOrderLoad.updateState(2, 1)
    assert(CustomerOrderLoad.getCustomerOrderByID(2)(0).customerOrderStatusID == 1)
    CustomerOrderLoad.updateState(2, testEntry(0).customerOrderStatusID)
  }

  it should "update the isCheckOut boolen " in {
    val testEntry = CustomerOrderLoad.getCustomerOrderByID(2)
    CustomerOrderLoad.updateCheckedOut(2, true)
    assert(CustomerOrderLoad.getCustomerOrderByID(2)(0).isCheckedOut == true)
    CustomerOrderLoad.updateCheckedOut(2, testEntry(0).isCheckedOut)
  }

  it should "update the id of employee lastCheckedOutBy" in {
    val testEntry = CustomerOrderLoad.getCustomerOrderByID(2)
    CustomerOrderLoad.updateCheckOutBy(2, 1)
    assert(CustomerOrderLoad.getCustomerOrderByID(2)(0).employee_ == 1)
    CustomerOrderLoad.updateCheckOutBy(2,testEntry(0).employee_)
  }
}