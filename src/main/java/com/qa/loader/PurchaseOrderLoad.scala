package com.qa.loader

import com.qa.dbConnector.SQL
import com.qa.Entities.PurchaseOrder
import java.sql.ResultSet
import scalafx.collections.ObservableBuffer

/**
 * @author abutcher
 * @date 0/11/201
 * Class that uses the DB connector to manipulate purchase orders
 */
class PurchaseOrderLoad {
  var connector: SQL = new SQL
  var purchaseOrderList: ObservableBuffer[PurchaseOrder] = new ObservableBuffer[PurchaseOrder]

  /**
   * Function to iterate through a result set and create purchase order entities
   * @param sql String to be executed
   */
  def constructResults(sql: String) = {

    purchaseOrderList clear ()
    try {
      connector openSQLCon
      val rs: ResultSet = connector querySQLDB (sql)
      def scanResultSet: Unit = {
        if (rs.next) {
          val pOS: Int = rs getInt ("idPurchaseOrderStatus")
          val pOSS: String = getPurchaseOrderStatusByID(pOS)
          val supplier: Int = rs getInt ("idSupplier")
          val supplierS: String = getSupplierByID(supplier)
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

  /**
   * function that retrieves all the purchase orders in the database
   * @return purchaseOrderLine an observable buffer containing every purchase order
   */
  def getAllPurchaseOrders: ObservableBuffer[PurchaseOrder] = {
    val sql: String = "SELECT * FROM nbgardensdata.purchaseorder;"
    constructResults(sql)
    purchaseOrderList
  }

  /**
   * function that retrieves a specific purchase order from the database
   * @param id the purchase order id of the order to be retrieved
   * @return purchaseOrderLine an observable buffer containing the purchase order
   */
  def getPurchaseOrderByID(id: Int): ObservableBuffer[PurchaseOrder] = {
    val sql: String = "SELECT * FROM nbgardensdata.purchaseorder WHERE nbgardensdata.purchaseorder.idpurchaseorder =" + id + ";"
    constructResults(sql)
    purchaseOrderList
  }

  /**
   * function that retrieves the status of the order
   * @param id the ID of the status to be retrieved
   * @return string of the status assigned to the given ID
   */
  def getPurchaseOrderStatusByID(id: Int): String = {
    val sql: String = "SELECT * FROM nbgardensdata.purchaseorderstatus WHERE idPurchaseOrderStatus =" + id + ";"
    var pOS: String = null
    try {
      connector openSQLCon
      val rs: ResultSet = connector querySQLDB (sql)
      def scanResultSet: Unit = {
        if (rs.next) {
          pOS = rs getString ("status")
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

  /**
   * function that retrieves the supplier's name
   * @param id the ID of the supplier to be retrieved
   * @return string of the supplier's name assigned to the given ID
   */
  def getSupplierByID(id: Int): String = {
    val sql: String = "SELECT * FROM nbgardensdata.supplier WHERE idSupplier =" + id + ";"
    var s: String = null
    try {
      connector openSQLCon
      val rs: ResultSet = connector querySQLDB (sql)
      def scanResultSet: Unit = {
        if (rs.next) {
          s = rs getString ("supplierName")
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