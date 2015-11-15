package com.qa.GUI

import com.qa.GUI._
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.layout.GridPane
import javafx.geometry.Insets
import scalafx.scene.text._
import scalafx.scene.control._
import scalafx.event.ActionEvent
import scalafx.event.EventHandler
import scalafx.Includes._
import com.qa.Logic.EncryptPassword
import scalafx.scene.control.Alert.AlertType
import com.qa.Loader.LogInLoad
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
import com.qa.Logic.LogInLogic

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
        if (LogInLogic.checkLoginEntered(userT.getText, passT.getText) && LogInLogic.checkAgainstDB(userT getText, passT getText)) {
          MainNode.createScene(stage, userT.getText)
        } else {
          val alert: Alert = new Alert(AlertType.Information);
          alert.setTitle("Information");
          alert.setHeaderText(null);
          alert.setContentText("Invalid user name or password");
          alert.showAndWait()
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
   * set the cene
   */
  stage.setScene(createScene)
}