package com.qa.loader

import com.qa.dbConnector.SQL
import com.qa.entities.CustomerOrderLine
import java.sql.ResultSet
import scalafx.collections.ObservableBuffer

/**
 * @author abutcher
 * @date 10/11/2015
 * An object to load information relivent a customer order line 
 */
object CustomerOrderLineLoad {
  var connector: SQL = new SQL

  /**
   * Function to iterate through a result set and create customer order line entities
   * @param sql String to be executed
   */
  def constructResults(sql: String): ObservableBuffer[CustomerOrderLine] = {

    var customerOrderLineList: ObservableBuffer[CustomerOrderLine] = new ObservableBuffer[CustomerOrderLine]
    val conn = connector.openSQLCon
    try {
      val rs: ResultSet = connector querySQLDB (sql, conn)
      def scanResultSet: Unit = {
        if (rs.next) {
          val cOID = rs getInt ("idCustomerOrder")
          val itemID = rs getInt ("idItem")
          val itemName = getItemNameByItemID(itemID)
          val quantity = rs getInt ("quantity")
          val cOL = new CustomerOrderLine(cOID, itemID, itemName, quantity)
          customerOrderLineList += cOL
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
    customerOrderLineList
  }

  /**
   * A function that gets all customer order lines of a given customer order id
   * @param id : Int the customer order to find the lines for
   * @return ObservableBuffer[CustomerOrderLine] : the buffer of customer order lines
   */
  def getCustomerOrderLinesByCustomerOrderID(id: Int): ObservableBuffer[CustomerOrderLine] = {
    val sql: String = "SELECT * FROM nbgardensdata.customerorderline WHERE idCustomerOrder =" + id + ";"
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