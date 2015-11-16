package com.qa.tests

import org.scalatest._
import com.qa.TestBase
import scalafx.scene.control.TextField
import scalafx.scene.control.PasswordField
import com.qa.Logic.LogInLogic

/**
 * @author abutcher
 * @date 15/11/2015
 * A class that tests the login logic class
 */
class LogInLogicTest extends TestBase {
  "A LogInLogic" should "check that a password and user have been entered" in {
    val user = ""
    val password = ""
    val user2 = "1"
    val password2 = "password"
    assert(!LogInLogic.checkLoginEntered(user, password))
    assert(LogInLogic.checkLoginEntered(user2, password2))
  }

  it should "check the details against the database" in {
    val user = "1"
    val password = "hdqjhdbsahn"
    val user2 = "1"
    val password2 = "password"
    assert(!LogInLogic.checkAgainstDB(user, password))
    assert(LogInLogic.checkAgainstDB(user2, password2))
  }
}