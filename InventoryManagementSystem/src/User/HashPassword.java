package User;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author lambo
 */
public class HashPassword {
    
    public String hashPassword(String password) throws NoSuchAlgorithmException
    {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        
        byte[] hashedBytes = md.digest(password.getBytes());
        
        BigInteger bigInt = new BigInteger(1, hashedBytes);
        
        return bigInt.toString(16);
        
    }
}