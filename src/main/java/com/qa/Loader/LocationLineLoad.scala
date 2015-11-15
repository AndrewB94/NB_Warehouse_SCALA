package com.qa.Loader

import scalafx.collections.ObservableBuffer
import com.qa.DBConnector.SQL
import com.qa.Entities.LocationLine
import java.sql.ResultSet

/**
 * @author abutcher
 */
object LocationLineLoad {
  var connector: SQL = new SQL

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

  def getLocationLineByItemID(id: Int): ObservableBuffer[LocationLine] = {
    val sql: String = "SELECT * FROM nbgardensdata.locationline WHERE idItem =" + id + ";"
    constructResults(sql)
  }

  def getLocationLineByItemIDandLoaction(itemID: Int, x: String, y: String): ObservableBuffer[LocationLine] = {
    val sql: String = "SELECT * FROM nbgardensdata.locationline WHERE idItem =" + itemID + " && locationX =" + x + " && locationY = " + y + ";"
    constructResults(sql)
  }

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