package com.qa.Loader

import com.qa.DBConnector.SQL
import com.qa.Entities.PurchaseOrder
import java.sql.ResultSet
import scalafx.collections.ObservableBuffer

/**
 * @author abutcher
 * @date 0/11/201
 * Class that uses the DB connector to manipulate purchase orders
 */
object PurchaseOrderLoad {
  var connector: SQL = new SQL

  /**
   * Function to iterate through a result set and create purchase order entities
   * @param sql String to be executed
   */
  def constructResults(sql: String): ObservableBuffer[PurchaseOrder] = {

    var purchaseOrderList: ObservableBuffer[PurchaseOrder] = new ObservableBuffer[PurchaseOrder]
    val conn = connector.openSQLCon
    try {
      val rs: ResultSet = connector querySQLDB (sql, conn)
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
          val checkedOut = rs.getBoolean("isCheckedOut")
          var pO: PurchaseOrder = new PurchaseOrder(pOSS, supplierS, 1L, id, datePlaced, dateExpected, employee, pOS, checkedOut)
          purchaseOrderList += pO
          scanResultSet
        }
      }
      scanResultSet
      rs.close()
    } catch {
      case e: Exception => println("Error Executing Query")
    } finally {
      connector closeSQLCon (conn)
    }
    purchaseOrderList
  }

  /**
   * function that retrieves all the purchase orders in the database
   * @return purchaseOrderLine an observable buffer containing every purchase order
   */
  def getAllPurchaseOrders: ObservableBuffer[PurchaseOrder] = {
    val sql: String = "SELECT * FROM nbgardensdata.purchaseorder;"
    constructResults(sql)
  }

  /**
   * function that retrieves all the purchase orders in the database
   * @return purchaseOrderLine an observable buffer containing every purchase order
   */
  def getAllNotStoredPurchaseOrders: ObservableBuffer[PurchaseOrder] = {
    val sql: String = "SELECT * FROM nbgardensdata.purchaseorder WHERE idPurchaseOrderStatus !=4;"
    constructResults(sql)
  }

  /**
   * function that retrieves a specific purchase order from the database
   * @param id the purchase order id of the order to be retrieved
   * @return purchaseOrderLine an observable buffer containing the purchase order
   */
  def getPurchaseOrderByID(id: Int): ObservableBuffer[PurchaseOrder] = {
    val sql: String = "SELECT * FROM nbgardensdata.purchaseorder WHERE nbgardensdata.purchaseorder.idpurchaseorder =" + id + ";"
    constructResults(sql)
  }

  /**
   * function that retrieves the status of the order
   * @param id the ID of the status to be retrieved
   * @return string of the status assigned to the given ID
   */
  def getPurchaseOrderStatusByID(id: Int): String = {
    val sql: String = "SELECT * FROM nbgardensdata.purchaseorderstatus WHERE idPurchaseOrderStatus =" + id + ";"
    var pOS: String = null
    val conn = connector.openSQLCon
    try {
      val rs: ResultSet = connector querySQLDB (sql, conn)
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
      connector closeSQLCon (conn)
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
    val conn = connector.openSQLCon
    try {
      val rs: ResultSet = connector querySQLDB (sql, conn)
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
      connector closeSQLCon (conn)
    }
    s
  }

    /**
   * Function to update the state of a purchase order
   * @param orderID the ID of the purchase order to be updated
   * @param statusID the ID of the new status for the order
   */
  def updateState(purchaseOrderID: Int, newStatusID: Int) = {
    val sql: String = "UPDATE `nbgardensdata`.`purchaseorder` SET `idPurchaseOrderStatus`='" + newStatusID + "' WHERE `idpurchaseorder`='" + purchaseOrderID + "';"
    val conn = connector.openSQLCon
    try {
      connector.updateSQLDB(sql, conn)
    } catch {
      case e: Exception => println("Error Executing Supplier Name Query")
    } finally {
      connector closeSQLCon (conn)
    }
  }

    /**
   * Funtion to update the checked out boolean
   * @param orderID : Int the order to be updated
   * @param newCheckedOut : Boolean to be changed to
   */
  def updateCheckedOut(purchaseOrderID: Int, newCheckedOut: Boolean) = {
    var checkedOutInt = 1
    if (newCheckedOut) {
      checkedOutInt = 0
    }
    val sql: String = "UPDATE `nbgardensdata`.`purchaseorder` SET `isCheckedOut`='" + checkedOutInt + "' WHERE `idpurchaseorder`='" + purchaseOrderID + "';"
    val conn = connector.openSQLCon
    try {
      connector.updateSQLDB(sql, conn)
    } catch {
      case e: Exception => println("Error Executing Update checked out Query")
    } finally {
      connector closeSQLCon (conn)
    }
  }

    /**
   * Funtion to update the delivery
   * @param orderID : Int the order to be updated
   */
  def updateDeliverd(purchaseOrderID: Int) = {
    val sql: String = "UPDATE nbgardensdata.purchaseorder SET dateExpected = CURDATE() WHERE purchaseorder.idpurchaseorder=" + purchaseOrderID + ";"
    val conn = connector.openSQLCon
    try {
      connector.updateSQLDB(sql, conn)
    } catch {
      case e: Exception => println("Error Executing update delivered Query")
    } finally {
      connector closeSQLCon (conn)
    }
  }

    /**
   * Funtion to update the checked outBy id
   * @param orderID : Int the order to be updated
   * @param employeeID : Int to be changed to
   */
  def updateCheckOutBy(purchaseOrderID: Int, employeeID: Int) = {
    val sql: String = "UPDATE `nbgardensdata`.`purchaseorder` SET `idEmployee`='" + employeeID + "' WHERE `idpurchaseorder`='" + purchaseOrderID + "';"
    val conn = connector.openSQLCon
    try {
      connector.updateSQLDB(sql, conn)
    } catch {
      case e: Exception => println("Error Executing update checked out by Query")
    } finally {
      connector closeSQLCon (conn)
    }
  }

}