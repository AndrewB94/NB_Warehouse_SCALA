package com.qa.gui

import com.qa.gui._
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.layout.GridPane
import javafx.geometry.Insets
import scalafx.scene.text._
import scalafx.scene.control._
import scalafx.event.ActionEvent
import scalafx.event.EventHandler
import scalafx.Includes._
import com.qa.logic.EncryptPassword
import scalafx.scene.control.Alert.AlertType
import com.qa.loader.LogInLoad
import scalafx.stage.StageStyle
import scalafx.scene.layout.BorderPane
import scalafx.event.ActionEvent
import scalafx.scene.Node
import scalafx.scene.control._
import scalafx.scene.input.MouseEvent
import scalafx.scene.layout._
import scalafx.scene.{ Scene, Group, Node }
import scalafx.Includes._
import scalafx.beans.property.BooleanProperty
import scalafx.scene.paint.Color

/**
 * @author abutcher
 * @09/11/2015
 * stage that displays log in form
 */
class LoginNode(stage: PrimaryStage) {
  stage.setTitle("LOG IN")

  /**
   * function that creates the log in scene
   */
  def createScene: Scene = {
    /**
     * initialize values
     */
    val userNameL: Label = new Label("Username:")
    val passwordL: Label = new Label("Password:")
    val userT: TextField = new TextField
    val passT: PasswordField = new PasswordField

    /**
     * set textField prompts
     */
    userT setPromptText ("username")
    passT setPromptText ("Password")

    /**
     * set label fonts
     */
    userNameL setFont (Font.font("Arial", FontWeight.BOLD, 20))
    passwordL setFont (Font.font("Arial", FontWeight.BOLD, 20))

    /**
     * set up action events for buttons
     */
    val logInB: Button = new Button("Log In") {
      onAction = { ae: ActionEvent =>
        if (checkLogin(userT.getText, passT.getText)) {
          new MainNode(stage, userT.getText)
        }
      }
    }

    val cancelB: Button = new Button {
      text = "Close"
      cancelButton = true
      onAction = { ae: ActionEvent => stage.hide() }
    }

    /**
     * create the scene
     */
    var scene: Scene = new Scene {
      /**
       * Initialize grid pane
       */
      var grid: GridPane = new GridPane
      grid setHgap (10)
      grid setVgap (10)
      grid setPadding (new Insets(0, 10, 10, 10))

      /**
       * add components to grid pane
       */
      grid add (userNameL, 0, 2)
      grid add (passwordL, 0, 3)
      grid add (userT, 1, 2, 2, 1)
      grid add (passT, 1, 3, 2, 1)
      grid add (logInB, 1, 4)
      grid add (cancelB, 2, 4)

      /**
       * add gridpane to scene
       */
      content.addAll(grid)
      
    }
   scene stylesheets = List(getClass.getResource("/controlStyle2.css").toExternalForm)
    scene.fill = Color.rgb(109, 158, 104)
    scene
  }

  /**
   * function that checks the log in credentials are valid
   * @param username the user name entered by the user
   * @param password the password entered by the user
   */
  def checkLogin(username: String, password: String): Boolean = {
    val encryption: EncryptPassword = new EncryptPassword
    var returner: Boolean = true
    /**
     * check for empty user name
     */
    if (username.equals("")) {
      returner = false
      /*
       * Alert pop up to tell the user to enter a user name
       */
      val alert: Alert = new Alert(AlertType.Information);
      alert.setTitle("Information");
      alert.setHeaderText(null);
      alert.setContentText("Please enter a valid user name");

      alert.showAndWait()
    }

    /**
     * check for empty password
     */
    if (password.equals("")) {
      returner = false
      /*
       * Alert pop up to tell the user to enter a password
       */
      val alert: Alert = new Alert(AlertType.Information);
      alert.setTitle("Information");
      alert.setHeaderText(null);
      alert.setContentText("Please enter a valid user password");

      alert.showAndWait()
    }

    /**
     * Convert userID to Int
     */
    val userID: Int = Integer.parseInt(username)

    /**
     * Encrypt the password
     */
    val encryptedPassword: String = encryption.checkSHA1(password)

    /**
     * check the credentials
     */
    if (!LogInLoad.checkDetails(userID, encryptedPassword)) {
      returner = false
      /**
       * Alert the user the information given was not valid
       */
      val alert: Alert = new Alert(AlertType.Information);
      alert.setTitle("Information");
      alert.setHeaderText(null);
      alert.setContentText("Incorrect user name or password");

      alert.showAndWait()
    }
    returner
  }
  
    def makeDraggable(node: Node): Node = {

    val dragContext = new DragContext()

    new Group(node) {
      filterEvent(MouseEvent.Any) {
        (me: MouseEvent) =>
          if (true) {
            me.eventType match {
              case MouseEvent.MousePressed =>
                dragContext.mouseAnchorX = me.x
                dragContext.mouseAnchorY = me.y
                dragContext.initialTranslateX = node.translateX()
                dragContext.initialTranslateY = node.translateY()
              case MouseEvent.MouseDragged =>
                stage.setX(dragContext.initialTranslateX + me.x - dragContext.mouseAnchorX)
                stage.setY (dragContext.initialTranslateY + me.y - dragContext.mouseAnchorY)
              case _ =>
            }
            me.consume()
          }
      }
    }
  }

  private final class DragContext {
    var mouseAnchorX: Double = 0d
    var mouseAnchorY: Double = 0d
    var initialTranslateX: Double = 0d
    var initialTranslateY: Double = 0d
  }
  
  /**
   * set the cene
   */
  stage.setScene(createScene)
}