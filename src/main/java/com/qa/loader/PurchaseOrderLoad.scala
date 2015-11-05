package com.qa.loader

import com.qa.dbConnector.SQL
import com.qa.Entities.PurchaseOrder
import java.sql.ResultSet
import scala.collection.mutable.ArrayBuffer

/**
 * @author abutcher
 */
class PurchaseOrderLoad {
  var connector: SQL = new SQL
  var purchaseOrderList: ArrayBuffer[PurchaseOrder] = new ArrayBuffer[PurchaseOrder]

  def constructResults(sql: String) = {

    purchaseOrderList clear ()
    try {
      connector openSQLCon
      val rs: ResultSet = connector querySQLDB (sql)
      def scanResultSet: Unit = {
        if (rs.next) {
          val pOS: Int = rs getInt ("idPurchaseOrderStatus")
          val supplier: Int = rs getInt ("idSupplier")
          var pO: PurchaseOrder = new PurchaseOrder(pOS, supplier);
          pO datePlaced = rs getDate ("purchaseorder.datePlaced")
          pO employee = rs getInt ("idEmployee")
          pO idPurchaseOrder = rs getInt ("purchaseorder.idPurchaseOrder")
          pO dateExpected = rs getDate ("purchaseorder.dateExpected")
          purchaseOrderList += pO
          scanResultSet
        }
      }
      scanResultSet
      rs.close()
    } catch {
      case e: Exception => println("Error Executing Query")
    } finally {
      connector closeSQLCon
    }
  }

  def getAllPurchaseOrders: ArrayBuffer[PurchaseOrder] = {
    val sql: String = "SELECT * FROM nbgardensdata.purchaseorder;"
    constructResults(sql)
    purchaseOrderList
  }

  def getPurchaseOrderByID(id: Int): ArrayBuffer[PurchaseOrder] = {
    val sql: String = "SELECT * FROM nbgardensdata.purchaseorder WHERE nbgardensdata.purchaseorder.idpurchaseorder ="+id+";"
    constructResults(sql)
    purchaseOrderList
  }

  def getPurchaseOrderStatusByID(id:Int): String  ={
       val sql: String = "SELECT * FROM nbgardensdata.purchaseorderstate WHERE nbgardensdata.purchaseorder.idpurchaseorderstate ="+id+";"
       var pOS: String = null
    try {
      connector openSQLCon
      val rs: ResultSet = connector querySQLDB (sql)
      def scanResultSet: Unit = {
        if (rs.next) {
          pOS = rs getString("status")
        }
      }
      scanResultSet
      rs.close
    } catch {
      case e: Exception => println("Error Executing Query")
    } finally {
      connector closeSQLCon
    }
    pOS
  }
  
}