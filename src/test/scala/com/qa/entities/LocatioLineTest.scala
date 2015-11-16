package com.qa.entities

import com.qa.TestBase

/**
 * @author abutcher
 * @date 16/11/2015
 */
class LocatioLineTest extends TestBase {
  "An LocationLine" should "be initialized with the correct values entered into the constructors" in {
    val loc: LocationLine = new LocationLine(1, 1, 1, 1)

    assertResult(1)(loc.idItem)
    assertResult(1)(loc.locationX)
    assertResult(1)(loc.loactionY)
    assertResult(1)(loc.quantity)
  }
}