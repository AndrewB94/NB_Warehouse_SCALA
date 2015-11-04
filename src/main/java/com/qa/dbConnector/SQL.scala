package com.qa.dbConnector

import java.sql._

/**
 * @author abutcher
 */
class SQL {
  
    val ip:String = "localhost"
    val databaseURL:String = "jdbc:mysql//" + ip + ":3306/nbgardensdata"
    val userName:String = "root"
    val password:String = "root"
    val driver:String = "com.mysql.jdbc.Driver"

    
    var conn:Connection = null
    var stmt:Statement = null
    var pstmt:PreparedStatement = null
    
    /**
     * @author abutcher
     * Function to open a connection to the SQL database
     */
    def openSQLCon:Unit = {
//      try {
//        conn = DriverManager getConnection(databaseURL, userName, passwaord)
//      } catch {
//        case e: Exception=> println("Error Connecting to SQL Database")
//      }
      
      Class.forName("com.mysql.jdbc.Driver");
      conn = DriverManager.getConnection(databaseURL,userName, password);
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
         case e: Exception=> println("Error Connecting to SQL Database")
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