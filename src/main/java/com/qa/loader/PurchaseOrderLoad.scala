package com.qa.loader

import com.qa.dbConnector.SQL
import com.qa.Entities.PurchaseOrder
import java.sql.ResultSet
import scalafx.collections.ObservableBuffer

/**
 * @author abutcher
 */
class PurchaseOrderLoad {
  var connector: SQL = new SQL
  var purchaseOrderList: ObservableBuffer[PurchaseOrder] = new ObservableBuffer[PurchaseOrder]

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
          val datePlaced = rs getDate ("purchaseorder.datePlaced")
          val employee = rs getInt ("idEmployee")
          val id = rs getInt ("purchaseorder.idPurchaseOrder")
          val dateExpected = rs getDate ("purchaseorder.dateExpected")
          var pO: PurchaseOrder = new PurchaseOrder(pOSS, supplierS, 1L, id, datePlaced, dateExpected, employee);
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

  def getAllPurchaseOrders: ObservableBuffer[PurchaseOrder] = {
    val sql: String = "SELECT * FROM nbgardensdata.purchaseorder;"
    constructResults(sql)
    purchaseOrderList
  }

  def getPurchaseOrderByID(id: Int): ObservableBuffer[PurchaseOrder] = {
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
          println (s)
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