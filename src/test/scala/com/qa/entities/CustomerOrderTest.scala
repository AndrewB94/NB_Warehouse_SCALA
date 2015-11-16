package com.qa.entities

import com.qa.TestBase
import java.util.Date

/**
 * @author abutcher
 * @date 16/11/2015
 */
class CustomerOrderTest extends TestBase {
  "A CustomerOrder" should "be initialized with the correct values entered into the constructors" in {
    val date: Date = new Date()
    val customer:CustomerOrder = new CustomerOrder("PLACED", date, "Will Smith", 1, false, 1, 1L, 1,date,  false, 1  )
    
    assertResult("PLACED")(customer.customerOrderStatus_)
    assertResult(date)(customer.datePlaced_)
    assertResult("Will Smith")(customer.customer_)
    assertResult(1)(customer.employee_)
    assertResult(false)(customer.isPaid_)
    assertResult(1)(customer.addressId_)
    assertResult(1L)(customer.serialVersionUID)
    assertResult(1)(customer.idCustomerOrder_)
    assertResult(date)(customer.dateShipped)
    assertResult(false)(customer.isCheckedOut)
    assertResult(1)(customer.customerOrderStatusID)
  }
}