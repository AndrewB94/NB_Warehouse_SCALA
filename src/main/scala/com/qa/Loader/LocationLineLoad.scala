package com.qa.loader

import scalafx.collections.ObservableBuffer
import com.qa.dbConnector.SQL
import com.qa.entities.LocationLine
import java.sql.ResultSet

/**
 * @author abutcher
 * @date 11/11/2015
 * An object to load information relivent a location line 
 */
object LocationLineLoad {
  var connector: SQL = new SQL

  /**
 * Function to iterate through a result set and create Location Line entities
 * @param sql : String to be executed
 * @return  ObservableBuffer[LocationLine]
   */
  def constructResults(sql: String): ObservableBuffer[LocationLine] = {
    var LocationList: ObservableBuffer[LocationLine] = new ObservableBuffer[LocationLine]
    val conn = connector.openSQLCon
    try {
      val rs: ResultSet = connector querySQLDB (sql, conn)
      def scanResultSet: Unit = {
        if (rs.next) {
          val itemID = rs.getInt("idItem")
          val locationX = rs.getInt("locationX")
          val locationY = rs.getInt("locationY")
          val quant = rs.getInt("quantity")
          val ll = new LocationLine(itemID, locationX, locationY, quant)
          LocationList += ll
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
    LocationList
  }

  /**
   * Function to return all the locations an item is stored
   * @param id : Int the id of the item to find
   * @return  ObservableBuffer[LocationLine] of locationLines
   */
  def getLocationLineByItemID(id: Int): ObservableBuffer[LocationLine] = {
    val sql: String = "SELECT * FROM nbgardensdata.locationline WHERE idItem =" + id + ";"
    constructResults(sql)
  }

    /**
   * Function to return a location if a specific item is stored there
   * @param itemID : Int the id of the item to find
   * @param x : String x coordinate
   * @param y : String y coordinate
   * @return  ObservableBuffer[LocationLine] of locationLine
   */
  def getLocationLineByItemIDandLoaction(itemID: Int, x: String, y: String): ObservableBuffer[LocationLine] = {
    val sql: String = "SELECT * FROM nbgardensdata.locationline WHERE idItem =" + itemID + " && locationX =" + x + " && locationY = " + y + ";"
    constructResults(sql)
  }
  
    /**
   * Function to check if a location has a specific item is stored there
   * @param itemID : Int the id of the item to find
   * @param x : String x coordinate
   * @param y : String y coordinate
   * @return  Int : 0 if not found
   */
  def checkLocationLineIDByItemIDandLoaction(itemID: Int, x: String, y: String): Int = {
    val sql: String = "SELECT * FROM nbgardensdata.locationline WHERE idItem =" + itemID + " && locationX =" + x + " && locationY = " + y + ";"
    var id = 0
    val conn = connector.openSQLCon
    try {
      val rs: ResultSet = connector querySQLDB (sql, conn)
      def scanResultSet: Unit = {
        if (rs.next) {
          id = rs.getInt("idItem")
        }
      }
      scanResultSet
      rs.close()
    } catch {
      case e: Exception => println("Error Executing Query")
    } finally {
      connector closeSQLCon (conn)
    }
    id
  }

  /**
   * Function update a location lines quantity
   * @param itemID : Int the id of the item to find
   * @param x : Int x coordinate
   * @param y : Int y coordinate
   * @param newQuantity : the new quantity of the line
     */
  def updateQuantity(lineX: Int, lineY: Int, itemID: Int, newQuantity: Int) = {
    val sql: String = "UPDATE `nbgardensdata`.`locationline` SET `quantity`='" + newQuantity + "' WHERE `idItem`='" + itemID + "' and`locationX`='" + lineX + "' and`locationY`='" + lineY + "';"
    val conn = connector.openSQLCon
    try {
      connector.updateSQLDB(sql, conn)
    } catch {
      case e: Exception => println("Error Executing Query")
    } finally {
      connector closeSQLCon (conn)
    }
  }

   /**
   * Function to create a location line
   * @param itemID : Int the id of the item
   * @param x : Int x coordinate
   * @param y : Int y coordinate
   * @param newQuantity : the quantity of the line
     */
  def createLine(lineX: Int, lineY: Int, itemID: Int, newQuantity: Int) = {
    val sql = "INSERT INTO `nbgardensdata`.`locationline` (`idItem`, `locationX`, `locationY`, `quantity`) VALUES ('" + itemID + "', '" + lineX + "', '" + lineY + "', '" + newQuantity + "');"
    val conn = connector.openSQLCon
    try {
      connector.updateSQLDB(sql, conn)
    } catch {
      case e: Exception => println("Error Executing Query")
    } finally {
      connector closeSQLCon (conn)
    }
  }
}