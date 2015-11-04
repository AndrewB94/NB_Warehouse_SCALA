package com.qa.logic

/**
 * @author abutcher
 */
class CustomerOrderLogic {
    def fetchAllCustomerOrders: Array[Array[String]] = {
    val customerOrders: Array[Array[String]] = Array.ofDim[String](2,2)
    customerOrders(0)(0) = "1"
    customerOrders(1)(0) = "PLACED"
    customerOrders(0)(1) = "2"
    customerOrders(1)(1) = "PICKING"      
    customerOrders
  }
}