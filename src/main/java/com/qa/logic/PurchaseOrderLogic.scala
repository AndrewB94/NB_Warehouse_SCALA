package com.qa.logic

import com.qa.loader.PurchaseOrderLoad
import com.qa.Entities.PurchaseOrder
import scala.collection.mutable.ArrayBuffer

/**
 * @author abutcher
 * @date 04/11/2015
 */
class PurchaseOrderLogic {
  var poL:PurchaseOrderLoad = new PurchaseOrderLoad
  
  def fetchAllPurchaseOrders: Array[Array[String]] = {
    var poS:ArrayBuffer[PurchaseOrder] = poL getAllPurchaseOrders
    val purchaseOrders: Array[Array[String]] = Array.ofDim[String](poS size,2)
    val currentPO:PurchaseOrder = poS(0)
    purchaseOrders(0)(0) = ""+currentPO.idPurchaseOrder
    purchaseOrders(0)(1) = ""+currentPO.purchaseOrderStatus
//    purchaseOrders(0)(1) = "2"
//    purchaseOrders(1)(1) = "Avaliable"      
    purchaseOrders
  }
}