package com.qa.logic

import scalafx.scene.control.TextField
import scalafx.scene.control.PasswordField
import scalafx.scene.control.Alert
import scalafx.scene.control.Alert.AlertType
import com.qa.loader.LogInLoad

/**
 * @author abutcher
 * @date 15/11/2015
 * An object used to check if log in details are valid
 */
object LogInLogic {
  
  /**
   * function that checks two strings for if they are empty
   * @param username : the user name entered by the user
   * @param password : the password entered by the user
   * @retur Boolean of if the strings are empty
   */
  def checkLoginEntered(user: String, password: String): Boolean = {
    var returner: Boolean = true
    if (user.equals("")) {
      returner = false
    }

    if (password.equals("")) {
      returner = false
    }
    returner
  }

  /**
   * function that checks the log in credentials are valid
   * @param username the user name entered by the user
   * @param password the password entered by the user  
   * @retur Boolean of if the strings are valid
   */
  def checkAgainstDB(username: String, password: String): Boolean = {

    var returner = true
    val userID: Int = Integer.parseInt(username)
    val encryptedPassword: String = EncryptPassword.convertToSHA1(password)
    if (!LogInLoad.checkDetails(userID, encryptedPassword)) {
      returner = false
    }
    returner
  }

}