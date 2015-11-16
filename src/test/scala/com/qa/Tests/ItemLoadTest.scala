package com.qa.tests

import com.qa.TestBase
import com.qa.Loader.ItemLoad
import scalafx.collections.ObservableBuffer
import com.qa.Entities.Item

/**
 * @author abutcher
 * @date 14/11/2015
 * A test class testing the Item Loader
 */
class ItemLoadTest extends TestBase {
  "An ItemLoad" should "create an observable buffer of  when given a SELECT query" in {
    val sqlString: String = "SELECT * FROM nbgardensdata.item;"
    assert(ItemLoad.constructResults(sqlString).isInstanceOf[ObservableBuffer[Item]])
  }

  it should "create an observable buffer of Items" in {
    assert(ItemLoad.getAllItems.isInstanceOf[ObservableBuffer[Item]])
  }

  it should "get a string representing the supplier of a given ID" in {
    val result = ItemLoad.getSupplierByID(1)
    assert(result.isInstanceOf[String])
    assertResult("Van Hage")(result)
  }

  it should "return a value represnting the quantity of an item stored in the warehouse" in {
    val result = ItemLoad.getStock(1)
    assert(result.isInstanceOf[Int])
  }
}