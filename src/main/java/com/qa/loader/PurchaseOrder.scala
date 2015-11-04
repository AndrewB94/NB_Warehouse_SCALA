package com.qa.loader

import com.qa.dbConnector.SQL
import java.sql.ResultSet

/**
 * @author abutcher
 */
class PurchaseOrder {
  var connector:SQL = new SQL
  
  def getAllPurchaseOrders = {
    val sql:String = "SELECT * FROM nbgardensdata.purchaseorder;"
    var rs:ResultSet = null
    connector.openSQLCon
    rs = connector.querySQLDB(sql)
    println(rs.getInt("idpurchaseorder"))
    connector.closeSQLCon
  }
  
  def getPurchaseOrderByID(id:Int):Unit = {
    val sql:String = "SELECT * FROM nbgardensdata.purchaseorder WHERE nbgardensdata.purchaseorder.idpurchaseorder =1;"
    var rs:ResultSet = null
    connector.openSQLCon
    rs = connector.querySQLDB(sql)
    connector.closeSQLCon
  }
  
}