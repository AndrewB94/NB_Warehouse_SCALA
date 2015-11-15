package com.qa

import org.scalatest._

/**
 * @author abutcher
 * @date 13/11/2015
 * An abstract class that test classes inherit from
 */
abstract class TestBase extends FlatSpec
with Matchers with OptionValues
with Inside with Inspectors
