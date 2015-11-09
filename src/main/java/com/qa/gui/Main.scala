package com.qa.gui

import com.qa.gui._
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import com.qa.logic.EncryptPassword

/**
 * @author abutcher
 * @date 05/11/2015
 * An object that creates a frame that stages are passed into
 */
object Main extends JFXApp {
  stage = new PrimaryStage()
  val LoginStage = new LoginStage(stage)
}