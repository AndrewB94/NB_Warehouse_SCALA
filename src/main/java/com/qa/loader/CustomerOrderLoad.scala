package com.qa.loader

import com.qa.dbConnector.SQL
import com.qa.Entities.CustomerOrder
import java.sql.ResultSet
import scalafx.collections.ObservableBuffer

/**
 * @author abutcher
 * @date 06/11/2015
 * Class that uses the DB connector to manipulate customer orders
 */
class CustomerOrderLoad {
  var connector: SQL = new SQL
  var customerOrderList: ObservableBuffer[CustomerOrder] = new ObservableBuffer[CustomerOrder]

  /**
   * Function to iterate through a result set and create customer order entities
   * @param sql String to be executed
   */
  def constructResults(sql: String): Unit = {

    customerOrderList clear ()
    try {
      connector openSQLCon
      val rs: ResultSet = connector querySQLDB (sql)
      def scanResultSet: Unit = {
        if (rs.next) {
          val cOS = rs getInt ("idCustomerOrderStatus")
          val cOSS = getCustomerOrderStatusByID(cOS)
          val cOID = rs getInt ("idCustomerOrder")
          val datePlaced = rs getDate ("datePlaced")
          val dateShipped = rs getDate ("dateShipped")
          val isPaid = rs getBoolean ("isPaid")
          val idAddress = rs getInt ("idAddress")
          val idEmployee = rs getInt ("idEmployee")
          val idCistomer = rs getInt ("idCustomer")
          val isCheckedIn = rs getBoolean("isCheckedOut")
          val customer = getUserByID(idCistomer)
          var cO: CustomerOrder = new CustomerOrder(cOSS, datePlaced, customer, idEmployee, isPaid, idAddress, 1L, cOID, dateShipped, isCheckedIn, cOS)
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
  /**
   * function that retrieves all the customer orders in the database
   * @return customerOrderLine an observable buffer containing every customer order
   */
  def getAllCustomerOrders: ObservableBuffer[CustomerOrder] = {
    val sql: String = "SELECT * FROM nbgardensdata.customerorder;"
    constructResults(sql)
    customerOrderList
  }

    /**
   * function that retrieves all the customer orders in the database excluding dispatched orders
   * @return purchaseOrderLine an observable buffer containing every customer order
   */
  def getAllNotStoredCustomerOrders: ObservableBuffer[CustomerOrder] = {
    val sql: String = "SELECT * FROM nbgardensdata.customerorder WHERE idCustomerOrderStatus !=6"
    constructResults(sql)
    customerOrderList
  }
  
    /**
   * function that retrieves a specific customer order from the database
   * @param id the customer order id of the order to be retrieved
   * @return customerOrderLine an observable buffer containing the customer order
   */
  def getCustomerOrderByID(id: Int): ObservableBuffer[CustomerOrder] = {
    val sql: String = "SELECT * FROM nbgardensdata.customerorder WHERE idCustomerOrder ="+id+";"
    constructResults(sql)
    customerOrderList
  }
  
  /**
   * function that retrieves the status of the order
   * @param id the ID of the status to be retrieved
   * @return string of the status assigned to the given ID
   */
  def getCustomerOrderStatusByID(id: Int): String = {
    val sql: String = "SELECT * FROM nbgardensdata.customerorderstatus WHERE idCustomerOrderStatus =" + id + ";"
    var cOS: String = null
    try {
      connector openSQLCon
      val rs: ResultSet = connector querySQLDB (sql)
      def scanResultSet: Unit = {
        if (rs.next) {
          cOS = rs getString ("status")
        }
      }
      scanResultSet
      rs.close
    } catch {
      case e: Exception => println("Error Executing Status Query")
    } finally {
      connector closeSQLCon
    }
    cOS
  }

  /**
   * function that retrieves the user's name
   * @param id the ID of the user to be retrieved
   * @return string of the users name assigned to the given ID
   */
  def getUserByID(id: Int): String = {
    val sql: String = "SELECT * FROM nbgardensdata.user WHERE idUser =" + id + ";"
    var user: String = null
    try {
      connector openSQLCon
      val rs: ResultSet = connector querySQLDB (sql)
      def scanResultSet: Unit = {
        if (rs.next) {
          user = rs getString ("forename")
          user = user.+(" ")
          user = user.+(rs getString ("surname"))
        }
      }
      scanResultSet
      rs.close
    } catch {
      case e: Exception => println("Error Executing Status Query")
    } finally {
      connector closeSQLCon
    }
    user
  }
  /**
   * Function to update the state of a customer order
   * @param orderID the ID of the customer order to be updated
   * @param statusID the ID of the new status for the order
   */
  def updateState(orderID: Int, statusID: Int) = {
    val sql: String = "UPDATE `nbgardensdata`.`customerorder` SET `idCustomerOrderStatus`='" + statusID + "' WHERE `idCustomerOrder`='" + orderID + "';"
    try {
      connector.openSQLCon
      connector updateSQLDB (sql)
    } catch {
      case e: Exception => println("Error Executing Status Query")
    } finally {
      connector closeSQLCon
    }
  }
 
    def updateCheckedOut(orderID: Int, newCheckedOut: Int) = {
    val sql: String = "UPDATE `nbgardensdata`.`customerorder` SET `isCheckedOut`='" + newCheckedOut + "' WHERE `idcustomerorder`='" + orderID + "';"
    try {
      connector.openSQLCon
      connector.updateSQLDB(sql)
    } catch {
      case e: Exception => println("Error Executing Update checked out Query")
    } finally {
      connector closeSQLCon
    }
  }
  
      def updateCheckOutBy(orderID: Int, employeeID: String) = {
    println(employeeID)
    val sql: String = "UPDATE `nbgardensdata`.`customerorder` SET `idEmployee`='" + employeeID + "' WHERE `idcustomerorder`='" + orderID + "';"

    try {
      connector.openSQLCon
      connector.updateSQLDB(sql)
    } catch {
      case e: Exception => println("Error Executing update checked out by Query")
    } finally {
      connector closeSQLCon
    }
  }
    
}