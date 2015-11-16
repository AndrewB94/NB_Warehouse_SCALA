package com.qa.tests

import org.scalatest._
import com.qa.TestBase
import java.sql.ResultSet
import com.qa.dbconnector.SQL

/**
 * @author abutcher
 * @date 13/11/2015
 * A test class for testing the SQL connector class
 */
class SQLTest extends TestBase {

  "A SQL" should "open an SQL connection" in {
    val sql: SQL = new SQL
    val conn = sql.openSQLCon
    assert(!conn.isClosed)
    sql closeSQLCon(conn)
  }

  it should "close the connection " in {
    val sql: SQL = new SQL
    val conn = sql.openSQLCon
    sql closeSQLCon(conn)
    assert(conn.isClosed)
  }

  it should "return a result set when given an SELECT query string" in {
    val sql: SQL = new SQL
    val conn = sql.openSQLCon
    assert(sql.querySQLDB("SELECT * FROM nbgardensdata.customerorder;", conn).isInstanceOf[ResultSet])
    sql closeSQLCon(conn)
  }

  it should "add a new entry into the SQL database when given a INSERT query string" in {
    val sqlUpadteString: String = "UPDATE `nbgardensdata`.`customerorder` SET `isCheckedOut`='1' WHERE `idCustomerOrder`='5';"
    val sqlQueryString: String = "SELECT * FROM nbgardensdata.customerorder WHERE idCustomerOrder = 5;"
    val sqlRevertString: String = "UPDATE `nbgardensdata`.`customerorder` SET `isCheckedOut`='0' WHERE `idCustomerOrder`='5';"
    val sql: SQL = new SQL
    val conn = sql.openSQLCon

    sql updateSQLDB(sqlUpadteString, conn)
    val result: ResultSet = sql.querySQLDB(sqlQueryString, conn)
    result.next
    assert(result getBoolean("isCheckedOut"))
    sql updateSQLDB(sqlRevertString, conn)
    sql closeSQLCon(conn)
  }

}