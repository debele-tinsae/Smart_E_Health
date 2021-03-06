package org.eclipse.om2m.SmartSecurity.controller;
import java.io.UnsupportedEncodingException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * 
 * @author tinsae
 * thie class is responsible for encrypting and decrypt 
 *
 */
 
public class EncryptAndDecrypt {         
 
    private static SecretKeySpec secretKey;
    private static byte[] key;
   
    public static void setKey() 
    { 	
    	String myKey = "12345678901234567890";
        MessageDigest sha = null;
        try {
            key = myKey.getBytes("UTF-8");
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16); 
            secretKey = new SecretKeySpec(key, "AES");
        } 
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } 
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
 /**
  * Encrypt the sensative inforamtion before send to the server 
  * @param strToEncrypt
  * @return
  */
    public static String encrypt(String strToEncrypt)    
    {
        try
        { 
        	setKey();
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
        } 
        catch (Exception e) 
        {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }
    /**
     * decrypt the message after it reciever from the server
     * @param strToDecrypt
     * @return the decrpted sensative inforamtions 
     */
 
    public static String decrypt(String strToDecrypt) 
    {
        try
        {
        	setKey(); 
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        } 
        catch (Exception e) 
        {
            System.out.println("Error while decrypting: " + e.toString());
        }
        return null;
    }
}