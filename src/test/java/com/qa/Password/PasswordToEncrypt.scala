package com.qa.Password

import com.qa.logic.EncryptPassword

/**
 * @author abutcher
 */
object PasswordToEncrypt {
    def main(args: Array[String]) {
    val e:EncryptPassword = new EncryptPassword
    println(e.checkSHA1("1"))
  }
}