package com.qa.Tests

import org.scalatest._
import com.qa.TestBase
import com.qa.Loader.LocationLineLoad
import scalafx.collections.ObservableBuffer
import com.qa.Entities.LocationLine
import com.qa.DBConnector.SQL
import java.sql.Connection

/**
 * @author abutcher
 */
class LocationLineLoadTest extends TestBase {
  "A LocationLineLoad" should "create an observable bauffer of Location Lines when given a SELECT query" in {
    val sqlString: String = "SELECT * FROM nbgardensdata.locationline;"
    assert(LocationLineLoad.constructResults(sqlString).isInstanceOf[ObservableBuffer[LocationLine]])
  }

  it should "get an observable buffer of location lines relating to a given Item ID" in {
    val result = LocationLineLoad.getLocationLineByItemID(1)
    assert(result.isInstanceOf[ObservableBuffer[LocationLine]])
    var i = 0
    var isNotID1 = true
    iterate
    def iterate {
      if (i < result.length) {
        if (result(i).idItem != 1)
          isNotID1 = false
        i.+=(1)
        iterate
      }
    }
    assert(isNotID1)
  }

  it should "get an observable buffer of location lines relating to a given Item ID and location" in {
    val result = LocationLineLoad.getLocationLineByItemIDandLoaction(1, "1", "1")
    assert(result.isInstanceOf[ObservableBuffer[LocationLine]])
    var i = 0
    var isNotID1 = true
    iterate
    def iterate {
      if (i < result.length) {
        if (result(i).idItem != 1)
          isNotID1 = false
        i.+=(1)
        iterate
      }
    }
    assert(isNotID1)
  }

  it should "return the value zero if the itemID and location are not acurate" in {
    assertResult(0)(LocationLineLoad.checkLocationLineIDByItemIDandLoaction(1, "0", "0"))
  }

  it should "update the quantity in an loaction Line when given the location, itemID and quantity" in {
    val testEntity = LocationLineLoad.getLocationLineByItemIDandLoaction(1, "1", "1")
    LocationLineLoad.updateQuantity(1, 1, 1, 0)
    assertResult(0)(LocationLineLoad.getLocationLineByItemIDandLoaction(1, "1", "1")(0).quantity)
    LocationLineLoad.updateQuantity(1, 1, 1, testEntity(0).quantity)
  }

  it should "create a new line when given the location, itemID and quantity" in {
    LocationLineLoad.createLine(1, 2, 1, 5)
    assertResult(1)(LocationLineLoad.checkLocationLineIDByItemIDandLoaction(1, "1", "1"))
     val sql:SQL = new SQL()
    var conn:Connection = sql.openSQLCon
    sql.updateSQLDB("DELETE FROM `nbgardensdata`.`locationline` WHERE `idItem`='1' and`locationX`='1' and`locationY`='2';",conn)
    sql.closeSQLCon(conn)
  }
}