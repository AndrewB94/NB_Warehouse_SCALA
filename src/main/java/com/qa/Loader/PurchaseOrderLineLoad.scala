package com.qa.Loader

import com.qa.DBConnector.SQL
import com.qa.Entities.PurchaseOrderLine
import java.sql.ResultSet
import scalafx.collections.ObservableBuffer
import java.sql.Connection

/**
 * @author abutcher
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

  def getPurchaseOrderLinesByPurchaseOrderID(id: Int): ObservableBuffer[PurchaseOrderLine] = {
    val sql: String = "SELECT * FROM nbgardensdata.purchaseorderline WHERE idpurchaseorder =" + id + ";"
    constructResults(sql)
  }

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