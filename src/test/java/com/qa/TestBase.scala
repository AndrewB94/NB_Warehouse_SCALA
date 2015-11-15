package com.qa

import org.scalatest._

/**
 * @author abutcher
 */
abstract class TestBase extends FlatSpec
with Matchers with OptionValues
with Inside with Inspectors
