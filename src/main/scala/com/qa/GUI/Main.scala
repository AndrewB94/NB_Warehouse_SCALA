package com.qa.gui

import com.qa.gui._
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import com.qa.logic.EncryptPassword
import scalafx.stage.StageStyle


/**
 * @author abutcher
 * @date 05/11/2015
 * An object that creates a frame that stages are passed into
 */
object Main extends JFXApp {
  var xOffset: Double = 0;
  var yOffset: Double = 0;

  stage = new PrimaryStage()
//  stage.initStyle(StageStyle.UNDECORATED);

  val LoginStage = new LoginNode(stage)

}