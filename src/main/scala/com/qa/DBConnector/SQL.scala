package com.qa.dbconnector

import java.sql._
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource

/**
 * @author abutcher
 * @date 04/11/2015
 * A class used to connect to the SQL database
 */
class SQL(var stmt: Statement) {
  def this() = this(null)

  /**
   * Function to open a connection to the SQL database
   * @return conn : the connection that has been opened
   */
  def openSQLCon: Connection = {
    var conn: Connection = null
    try {
      val datasource = new MysqlDataSource()
      datasource setDatabaseName ("nbgardensdata")
      datasource setUser ("root")
      datasource setPassword ("root")
      datasource setServerName ("localhost")
      conn = datasource getConnection
    } catch {
      case e: Exception => println("Error Connecting to SQL Database")
    }
    conn
  }

  /**
   * Function to close the connection to the SQL database   
   * @param conn : the connection to close
   */
  def closeSQLCon(conn: Connection): Unit = {
    try {
        conn close ()
    } catch {
      case e: Exception => println("Error closing connection to SQL Database")
    }
  }

  /**
   * Function to query the SQL Database
   * @param sql : the string of SQL to execute
   * @param conn : the connection to use
   * @return resultSet : recieved from the SQL
   */
  def querySQLDB(sql: String, conn: Connection): ResultSet = {
    stmt = conn createStatement
    val rs: ResultSet = stmt executeQuery (sql)
    rs
  }

  /**
   * Function to update the SQL database
   * @param conn : the connection to use
   * @param sql the string of SQL to execute
   */
  def updateSQLDB(sql: String, conn: Connection): Unit = {
    stmt = conn createStatement ()
    stmt executeUpdate (sql)
  }
}