package com.qa.Entities

import java.util.Date;

/**
 * @author abutcher
 */
case class PurchaseOrder( var purchaseOrderStatus:Int, var supplier:Int) {
   val serialVersionUID:Long  = 1L
   var idPurchaseOrder:Int = 0
   var datePlaced:Date = null
   var  dateExpected:Date = null
   var employee:Int = 0
}