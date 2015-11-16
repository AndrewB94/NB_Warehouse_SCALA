package com.qa.loader

import com.qa.dbconnector.SQL
import com.qa.entities.PurchaseOrderLine
import java.sql.ResultSet
import scalafx.collections.ObservableBuffer
import java.sql.Connection

/**
 * @author abutcher
 * @date 10/11/2015
 * An object to load information relivent a purchase order line 
 */
object PurchaseOrderLineLoad {
  var connector: SQL = new SQL

  /**
   * Function to iterate through a result set and create purchase order entities
   * @param sql String to be executed
   */
  def constructResults(sql: String):ObservableBuffer[PurchaseOrderLine] = {

    var purchaseOrderLineList: ObservableBuffer[PurchaseOrderLine] = new ObservableBuffer[PurchaseOrderLine]

    val conn = connector.openSQLCon
    try {
      val rs: ResultSet = connector querySQLDB (sql, conn)
      def scanResultSet: Unit = {
        if (rs.next) {
          val pOID = rs.getInt("idpurchaseorder")
          val itemId = rs.getInt("idItem")
          val itemName = getItemNameByItemID(itemId)
          val quantity = rs.getInt("quantity")
          val quantityDamaged = rs.getInt("quantityDamaged")
          val pOL = new PurchaseOrderLine(pOID, itemId, itemName, quantity, quantityDamaged)
          purchaseOrderLineList += pOL
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
    purchaseOrderLineList
  }

    /**
   * A function that gets all purchase order lines of a given purchase order id
   * @param id : Int the purchase order to find the lines for
   * @return ObservableBuffer[PurchaseOrderLine] : the buffer of customer order lines
   */
  def getPurchaseOrderLinesByPurchaseOrderID(id: Int): ObservableBuffer[PurchaseOrderLine] = {
    val sql: String = "SELECT * FROM nbgardensdata.purchaseorderline WHERE idpurchaseorder =" + id + ";"
    constructResults(sql)
  }

    /**
   * A function to get the name of an item
   * @param id : Int the ID of the item to get the name
   * @return String : the name of the item 
   */
  def getItemNameByItemID(id: Int): String = {
    val sql: String = "SELECT * FROM nbgardensdata.item WHERE itemID =" + id + ";"
    var iN: String = null
    val conn = connector.openSQLCon
    try {

      val rs: ResultSet = connector querySQLDB (sql, conn)
      def scanResultSet: Unit = {
        if (rs.next) {
          iN = rs getString ("itemName")
        }
      }
      scanResultSet
      rs.close
    } catch {
      case e: Exception => println("Error Executing Name Query")
    } finally {
      connector closeSQLCon (conn)
    }
    iN
  }
}