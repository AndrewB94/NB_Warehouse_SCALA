package com.qa.clUI

import com.qa.loader._
import com.qa.dbConnector.SQL

/**
 * @author abutcher
 */
object Main {
//  var pol: PurchaseOrderLogic = new PurchaseOrderLogic
//  var col: CustomerOrderLogic = new CustomerOrderLogic

  def printGrid(grid: Array[Array[String]]): Unit = {
  for (row <- 0 to grid.length-1) {
      for (col <- 0 to grid(0).length-1) {
        print(" " + grid(row)(col))
      }
      println(".")
    }
    
  }

  def printPurchaseOrders: Unit = {
    println("=====PURCHASE ORDERS=====")
//    printGrid(pol fetchAllPurchaseOrders)
  }

  def printCustomerOrders: Unit = {
    println("=====CUSTOMER ORDERS=====")
//    printGrid(col fetchAllCustomerOrders)
  }

  def main(args: Array[String]) {
//    printCustomerOrders
    printPurchaseOrders
    
  }
}