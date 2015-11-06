package com.qa.loader

import com.qa.dbConnector.SQL
import com.qa.Entities.CustomerOrder
import java.sql.ResultSet
import scalafx.collections.ObservableBuffer

/**
 * @author abutcher
 * @date 06/11/2015
 */
class CustomerOrderLoad {
  var connector: SQL = new SQL
  var customerOrderList: ObservableBuffer[CustomerOrder] = new ObservableBuffer[CustomerOrder]

  def constructResults(sql: String) = {

    customerOrderList clear ()
    try {
      connector openSQLCon
      val rs: ResultSet = connector querySQLDB (sql)
      def scanResultSet: Unit = {
        if (rs.next) {
          val cOS= rs getInt("idCustomerOrderStatus")
          val cOID = rs getInt("idCustomerOrder")
          val datePlaced = rs getDate("datePlaced")
          val dateShipped = rs getDate("dateShipped")
          val isPaid = rs getBoolean("isPaid")
          val idAddress = rs getInt("idAddress")
          val idEmployee = rs getInt("idEmployee")
          val idCistomer = rs getInt("idCustomer")
          var cO: CustomerOrder = new CustomerOrder(""+cOS, datePlaced, cOID, idEmployee, isPaid, idAddress, 1L,cOID, dateShipped) 
          customerOrderList += cO
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
  
  def getAllCustomerOrders:ObservableBuffer[CustomerOrder] = {
    val sql:String = "SELECT * FROM nbgardensdata.customerorder;"
    constructResults(sql)
    customerOrderList
  }
  
  
  
}