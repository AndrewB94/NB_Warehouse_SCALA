package com.qa.Loader

import com.qa.DBConnector.SQL
import java.sql.ResultSet
import scalafx.collections.ObservableBuffer

/**
 * @author abutcher
 * @date 09/11/201
 * An object load any information relivent to logging in
 */
object LogInLoad {
  var connector: SQL = new SQL

  /**
   * Function that takes input and check if the user ID and password are both contained in one record of the MySQL database
   * @param idUser : Int idUser
   * @param password : String password
   * @return : Returns a boolean; true if ResulSet only contains one record, false if not
   */
  def checkDetails(idUser: Int, password: String): Boolean = {
    val sql = "SELECT idUser, user.password FROM nbgardensdata.user where idUser =  " + idUser + "  and user.password = '" + password + "' and isEmployee = 1"
    val conn = connector.openSQLCon
    var rowCount: Int = 0
    try {
      var rSet: ResultSet = connector.querySQLDB(sql ,conn)
      if (rSet.next()) {
        rowCount = rowCount.+(1)
      }
    } catch {
      case e: Exception => println("Error Executing Query")
    } finally {
      connector closeSQLCon(conn)
    }
    var boo: Boolean = false
    if (rowCount == 1) {
      boo = true
    }
    boo
  }

}