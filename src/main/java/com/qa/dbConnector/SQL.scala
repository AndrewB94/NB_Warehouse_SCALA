package com.qa.dbConnector

import java.sql._
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource

/**
 * @author abutcher
 * @date 04/11/2015
 * A class used to connect to the SQL database
 */
class SQL {
      
    var conn:Connection = null
    var stmt:Statement = null
    var pstmt:PreparedStatement = null
    
    /**
     * @author abutcher
     * Function to open a connection to the SQL database
     */
    def openSQLCon:Unit = {
      try { 
        val datasource = new MysqlDataSource()
          datasource setDatabaseName("nbgardensdata")
          datasource setUser("root")
          datasource setPassword("root")
          datasource setServerName("localhost")
          conn=datasource getConnection
          }catch {
            case e: Exception=> println("Error Connecting to SQL Database")
          }
    }

    
   /**
     * @author abutcher
     * Function to close the connection to the SQL database
     */
    def closeSQLCon:Unit = {
      try {
        if (stmt != null)
          conn close()
      } catch {
         case e: Exception=> println("Error closing connection to SQL Database")
      }
    }
    
    /**
     * @author abutcher
     * Function to query the SQL Database
     * @param sql the string of SQL to execute
     * @return resultSet recieved from the SQL 
     */
    def querySQLDB(sql:String):ResultSet  =  {
      stmt =  conn createStatement
      val rs:ResultSet = stmt executeQuery(sql)
      rs
    }
    
    /**
     * @author abutcher
     * Function to update the SQL database
     * @param sql the string of SQL to execute
     */
    def updateSQLDB(sql:String):Unit = {
      stmt = conn createStatement()
      stmt executeUpdate(sql)
  }
}