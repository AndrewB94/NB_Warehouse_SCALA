package com.qa.loader

import com.qa.dbConnector.SQL
import java.sql.ResultSet
import scalafx.collections.ObservableBuffer

/**
 * @author abutcher
 * @date 09/11/201
 */
class LogInLoad {
  var connector: SQL = new SQL

  /**
   * Function that takes input and check if the user ID and password are both contained in one record of the MySQL database
   * @param idUser : int idUser
   * @param password : String password
   * @return : Returns a boolean; true if ResulSet only contains one record, false if not
   */
  def checkDetails(idUser: Int, password: String): Boolean = {
    connector.openSQLCon
    var rowCount: Int = 0
    try {
      var rSet: ResultSet = connector.querySQLDB("SELECT idUser, user.password FROM nbgardensdata.user where idUser =  " + idUser + "  and user.password = '" + password + "' and isEmployee = 1")
      if (rSet.next()) {
        rowCount = rowCount.+(1)
      }
    } catch {
      case e: Exception => println("Error Executing Query")
    } finally {
      connector closeSQLCon
    }
    var boo: Boolean = false
    if (rowCount == 1) {
      boo = true
    }
    boo
  }

}