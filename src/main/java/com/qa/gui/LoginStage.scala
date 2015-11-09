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

/**
 * @author abutcher
 */
class LoginStage(stage: PrimaryStage) {
  stage.setTitle("LOG IN")

  def createScene: Scene = {
    val userNameL: Label = new Label("Username:")
    val passwordL: Label = new Label("Password:")

    val userT: TextField = new TextField
    val passT: PasswordField = new PasswordField

    userT setPromptText ("username")
    passT setPromptText ("Password")

    userNameL setFont (Font.font("Arial", FontWeight.BOLD, 20))
    passwordL setFont (Font.font("Arial", FontWeight.BOLD, 20))

    val logInB: Button = new Button("Log in") {
      onAction = { ae: ActionEvent => new MainStage(stage) }
    }

    val cancelB: Button = new Button {
      text = "Cancel"
      cancelButton = true
      onAction = { ae: ActionEvent => println("some message") }
    }

    var scene: Scene = new Scene {
      val grid: GridPane = new GridPane
      grid setHgap (10);
      grid setVgap (10);
      grid setPadding (new Insets(0, 10, 0, 10));

      grid add (userNameL, 0, 2)
      grid add (passwordL, 0, 3)
      grid add (userT, 1, 2, 2, 1)
      grid add (passT, 1, 3, 2, 1)
      grid add (logInB, 1, 4)
      grid add (cancelB, 2, 4)

      content.addAll(grid)
    }
    scene
  }

  stage.setScene(createScene)
}