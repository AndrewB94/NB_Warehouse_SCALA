package com.qa.dbConnector

import java.sql._
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource

/**
 * @author abutcher
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
          datasource.setDatabaseName("nbgardensdata")
          datasource.setUser("root")
          datasource.setPassword("root")
          datasource.setServerName("localhost")
          conn=datasource.getConnection
          }catch {
            case e: Exception=> println("Error Connecting to SQL Database")
          }
    }

    
   /**
     * @author abutcher
     * Function to close a connection to the SQL database
     */
    def closeSQLCon:Unit = {
      try {
        if (stmt != null)
          conn close()
      } catch {
         case e: Exception=> println("Error closing connection to SQL Database")
      }
    }
    
    def querySQLDB(sql:String):ResultSet  =  {
      stmt =  conn createStatement
      val rs:ResultSet = stmt executeQuery(sql)
      rs
    }
    
    def updateSQLDB(sql:String):Unit = {
      stmt = conn createStatement()
      stmt executeUpdate(sql)
  }
}