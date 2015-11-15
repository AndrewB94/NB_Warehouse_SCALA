package com.qa.Password

import com.qa.Logic.EncryptPassword

/**
 * @author abutcher
 * @date 10/11/2015
 * A claas with a main for converting a string into SHA1 encryption and then printing out the result for entry into database
 */
object PasswordToEncrypt {
  def main(args: Array[String]) {
    println(EncryptPassword.checkSHA1("1"))
  }
}