package com.qa.Logic

import scalafx.scene.control.TextField
import scalafx.scene.control.PasswordField
import scalafx.scene.control.Alert
import scalafx.scene.control.Alert.AlertType
import com.qa.Loader.LogInLoad

/**
 * @author abutcher
 * @date 15/11/2015
 * An object used to check if log in details are valid
 */
object LogInLogic {
  def checkLoginEntered(user: String, password: String): Boolean = {
    var returner: Boolean = true
    if (user.equals("")) {
      returner = false
    }

    if (password.equals("")) {
      returner = false
    }
    if (!returner) {
    }
    returner
  }

  /**
   * function that checks the log in credentials are valid
   * @param username the user name entered by the user
   * @param password the password entered by the user
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