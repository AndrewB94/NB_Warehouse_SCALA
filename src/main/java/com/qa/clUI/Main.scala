package com.qa.clUI

import com.qa.logic._

/**
 * @author abutcher
 */
object Main {
  var pol: PurchaseOrderLogic = new PurchaseOrderLogic
  var col: CustomerOrderLogic = new CustomerOrderLogic

  def printGrid(grid: Array[Array[String]]): Unit = {
    var currentX: Int = 0
    var currentY: Int = 0
    def printer {
      if (currentX == grid.length) {
        currentX = 0
        currentY = currentY + 1
        println(".")
      } else if (currentX != 0) {
        print(", ")
      }
      if (currentY != grid.length) {
        print(grid(currentX)(currentY))
        currentX.+=(1)
        printer
      }
    }
    printer
  }

  def printPurchaseOrders: Unit = {
    println("=====PURCHASE ORDERS=====")
    printGrid(pol fetchAllPurchaseOrders)
  }

  def printCustomerOrders: Unit = {
    println("=====CUSTOMER ORDERS=====")
    printGrid(col fetchAllCustomerOrders)
  }

  def main(args: Array[String]) {
    printCustomerOrders
    printPurchaseOrders
  }
}