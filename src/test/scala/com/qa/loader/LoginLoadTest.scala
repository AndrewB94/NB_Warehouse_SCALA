package com.qa.loader

import org.scalatest._
import com.qa.TestBase

/**
 * @author abutcher
 * @date 14/11/2105
 * A class that tests the login loader
 */
class LoginLoadTest extends TestBase {
  "A LoginLoad" should "allow a user with the correct credentials" in {
    assertResult(true)(LogInLoad.checkDetails(1, "5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8"))
  }

  it should "not allow a user with the incorrect credentials" in {
    assertResult(false)(LogInLoad.checkDetails(1, "password"))
  }
}