package com.qa.loader

import com.qa.dbConnector.SQL
import com.qa.entities.Item
import java.sql.ResultSet
import scalafx.collections.ObservableBuffer

/**
 * @author abutcher
 * @date 12/11/2015
 * An object to load information relivent to an item
 */
object ItemLoad {
  val connector: SQL = new SQL
  
/**
 * Function to iterate through a result set and create Item entities
 * @param sql : String to be executed
 * @return  ObservableBuffer[Item]
 */
  def constructResults(sql: String):  ObservableBuffer[Item] = {

    var ItemList: ObservableBuffer[Item] = new ObservableBuffer[Item]
    val conn = connector.openSQLCon
    try {     
      val rs: ResultSet = connector querySQLDB (sql, conn)
      def scanResultSet: Unit = {
        if (rs.next) {
          val id = rs.getInt("itemID")
          val name = rs.getString("itemName")
//          val quantity = rs.getInt("numberInStock")
          val quantity = getStock(id)
          val supplierID = rs.getInt("supplierID")
          val supplier = getSupplierByID(supplierID)
          var nI: Item = new Item(id, name, quantity, supplier)
          ItemList += nI
          scanResultSet
        }
      }
      scanResultSet
      rs.close()
    } catch {
      case e: Exception => println("Error Executing Query")
    } finally {
      connector closeSQLCon(conn)
    }
    ItemList
  }
  
/**
 * Function to retrieve all items in the database
 * @return ObservableBuffer[Item] : All items
 */
  def getAllItems: ObservableBuffer[Item] = {
    val sql: String = "SELECT * FROM nbgardensdata.item;"
    constructResults(sql)
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
      connector closeSQLCon(conn)
    }
    s
  }
  
  /**
   * A function to calculate the current stock of an item
   * @param itemID : Int  ID of item to find stock level
   * @return Int : the total stock of an item
   */
  def getStock(itemID:Int):Int = {
    val buffer = LocationLineLoad.getLocationLineByItemID(itemID)
    var totalStock:Int = 0
    buffer.foreach { x => totalStock.+=(x.quantity) }
    totalStock
  }
}