package com.qa.loader

import com.qa.dbConnector.SQL
import com.qa.Entities.PurchaseOrderLine
import java.sql.ResultSet
import scalafx.collections.ObservableBuffer

/**
 * @author abutcher
 */
class purchaseOrderLineLoad {
  var connector: SQL = new SQL
  var purchaseOrderLineList: ObservableBuffer[PurchaseOrderLine] = new ObservableBuffer[PurchaseOrderLine]

  /**
   * Function to iterate through a result set and create purchase order entities
   * @param sql String to be executed
   */
  def constructResults(sql: String) = {

    purchaseOrderLineList clear ()
    try {
      connector openSQLCon
      val rs: ResultSet = connector querySQLDB (sql)
      def scanResultSet: Unit = {
        if (rs.next) {
          val pOID = rs.getInt("idpurchaseorder")
          val itemId = rs.getInt("idItem")
          val itemName = null
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
      connector closeSQLCon
    }
  }

  def getPurchaseOrderLinesByPurchaseOrderID(id: Int): ObservableBuffer[PurchaseOrderLine] = {
    val sql: String = "SELECT * FROM nbgardensdata.purchaseorderline WHERE idpurchaseorder =" + id + ";"
    constructResults(sql)
    purchaseOrderLineList
  }
}