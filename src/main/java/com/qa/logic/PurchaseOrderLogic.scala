package com.qa.logic

/**
 * @author abutcher
 * @date 04/11/2015
 */
class PurchaseOrderLogic {
  
  def fetchAllPurchaseOrders: Array[Array[String]] = {
    val purchaseOrders: Array[Array[String]] = Array.ofDim[String](2,2)
    purchaseOrders(0)(0) = "1"
    purchaseOrders(1)(0) = "Checked Out"
    purchaseOrders(0)(1) = "2"
    purchaseOrders(1)(1) = "Avaliable"      
    purchaseOrders
  }
}