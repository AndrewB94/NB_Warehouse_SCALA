package com.qa.Logic

import scalafx.scene.control.TextField
import scalafx.scene.control.PasswordField
import scalafx.scene.control.Alert
import scalafx.scene.control.Alert.AlertType
import com.qa.Loader.LogInLoad

/**
 * @author abutcher
 */
object LogInLogic {
  def checkLoginEntered(user: TextField, password: PasswordField): Boolean = {
    var returner: Boolean = true

    var string = ""

    if (user.getText.equals("")) {
      returner = false
      string = string + "Please enter a user"
    }

    if (password.getText.equals("")) {
      returner = false
      string = string + "\nPlease enter a password"
    }
    if (!returner) {
      val alert: Alert = new Alert(AlertType.Information)
      alert.setTitle("Information")
      alert.setHeaderText(null)
      alert setContentText (string)
      alert showAndWait ()
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
    val encryptedPassword: String = EncryptPassword.checkSHA1(password)
    if (!LogInLoad.checkDetails(userID, encryptedPassword)) {
      returner = false
      val alert: Alert = new Alert(AlertType.Information);
      alert.setTitle("Information");
      alert.setHeaderText(null);
      alert.setContentText("Incorrect user name or password");

      alert.showAndWait()
    }
    returner
  }

}