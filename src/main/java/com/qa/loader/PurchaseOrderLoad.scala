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
          val pOSS:String = getPurchaseOrderStatusByID(pOS)
          val supplier:Int = rs getInt ("idSupplier")
          val supplierS:String = getSupplierByID(supplier)
          var pO: PurchaseOrder = new PurchaseOrder(pOSS, supplier);
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
       val sql: String = "SELECT * FROM nbgardensdata.purchaseorderstatus WHERE idPurchaseOrderStatus ="+id+";"
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
      case e: Exception => println("Error Executing Status Query")
    } finally {
      connector closeSQLCon
    }
    pOS
  }
  
   def getSupplierByID(id:Int): String  ={
       val sql: String = "SELECT * FROM nbgardensdata.supplier WHERE idSupplier ="+id+";"
       var s: String = null
    try {
      connector openSQLCon
      val rs: ResultSet = connector querySQLDB (sql)
      def scanResultSet: Unit = {
        if (rs.next) {
          s = rs getString("supplierName")
        }
      }
      scanResultSet
      rs.close
    } catch {
      case e: Exception => println("Error Executing Supplier Name Query")
    } finally {
      connector closeSQLCon
    }
    s
  }
  
}