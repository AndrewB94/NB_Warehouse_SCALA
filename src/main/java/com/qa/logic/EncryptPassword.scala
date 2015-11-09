package com.qa.logic

import java.io.UnsupportedEncodingException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.Formatter;
import java.util.Arrays;
import java.lang.Byte

/**
 * @author abutcher
 */
class EncryptPassword {
  var crypt: MessageDigest  = null;
  
  def EncryptPassword() {
    
  }

  /**
   *
   * Method to convert a given string into SHA1 for comparison against SHA1 password stored in MySQL database
   *
   * @param passwordString: Takes on a string to convert into SHA1
   * @return returns string encrypted in SHA1
   */
  def checkSHA1(passwordString:String):String  ={
    
    try {
      crypt = MessageDigest.getInstance("SHA1");
     } catch {
         case e: Exception=> println("Error")
      }
    crypt.reset();
    try {
      crypt.update(passwordString.getBytes("UTF-8"));
    } catch {
         case e: Exception=> println("Error")
      }
    var sha1:String  = byteToHex(crypt.digest());
    
    return sha1;
  }
  
def  byteToHex( hash:Array[scala.Byte] ):String  ={
   val formatter:Formatter  = new Formatter()
    
   
    for(b <- hash) {
      formatter format("%02x", b.asInstanceOf[Object])
    }
    val result:String  = formatter.toString()
    formatter.close()
     result
  }
}