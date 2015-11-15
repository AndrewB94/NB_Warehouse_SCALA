package com.qa.Tests

import org.scalatest._
import com.qa.TestBase
import com.qa.Logic.EncryptPassword


/**
 * @author abutcher
 */
class EncryptPasswordTest extends TestBase {
  "A EncryptPassword" should "convert a string SHA1" in {
    val stringToEncrypt = "Hello"
    val encryptedString = "f7ff9e8b7bb2e09b70935a5d785e0cc5d9d0abf0"
    assertResult(encryptedString)(EncryptPassword.convertToSHA1(stringToEncrypt))
  }
}