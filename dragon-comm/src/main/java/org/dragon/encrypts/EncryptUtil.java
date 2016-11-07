package org.dragon.encrypts;

import java.security.MessageDigest;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.dragon.logs.LogManager;

public class EncryptUtil {
	
    protected final static String[] strDigits = { "0", "1", "2", "3", "4", "5" , "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };
    
    protected static String byteToArrayString(byte bByte) {
        int iRet = bByte;
        // System.out.println("iRet="+iRet);
        if (iRet < 0) {
            iRet += 256;
        }
        int iD1 = iRet / 16;
        int iD2 = iRet % 16; 
        return strDigits[iD1] + strDigits[iD2] ;
    }
    /**
     * MD5加密
     * @param strObj 需要加密的字符串
     * @return String 返回已经加密的数据
     * */
    public static String getMD5(String strObj) {
        StringBuffer resultString = new StringBuffer();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(strObj.getBytes()); 
            for(byte b:digest){
            	resultString.append( byteToArrayString( b ));
            }
        } catch (Exception ex){
        }
        return resultString.toString() ;
    }
	
    /*********************des 加密**************************/
    private static byte[] encrypt(byte[] data, byte[] key) throws Exception {
        SecureRandom sr = new SecureRandom();  
        DESKeySpec dks = new DESKeySpec(key);  
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);  
        SecretKey securekey = keyFactory.generateSecret(dks);  
        Cipher cipher = Cipher.getInstance(ALGORITHM);  
        cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);  
        return cipher.doFinal(data);  
    }  
    
    private static byte[] decrypt(byte[] data, byte[] key) throws Exception {  
        SecureRandom sr = new SecureRandom();  
        DESKeySpec dks = new DESKeySpec(key);  
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);  
        SecretKey securekey = keyFactory.generateSecret(dks);  
        Cipher cipher = Cipher.getInstance(ALGORITHM);  
        cipher.init(Cipher.DECRYPT_MODE, securekey, sr);  
        return cipher.doFinal(data);  
    }  
    protected static byte[] hex2byte(byte[] b) {  
        if ((b.length % 2) != 0)  
            throw new IllegalArgumentException("长度不是偶数");  
        byte[] b2 = new byte[b.length / 2];  
        for (int n = 0; n < b.length; n += 2) {  
            String item = new String(b, n, 2);  
            b2[n / 2] = (byte) Integer.parseInt(item, 16);  
        }  
        return b2;  
    }  
    protected static String byte2hex(byte[] b) {  
        String hs = "";  
        String stmp = "";  
        for (int n = 0; n < b.length; n++) {  
            stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));  
            if (stmp.length() == 1)  
                hs = hs + "0" + stmp;  
            else  
                hs = hs + stmp;  
        }  
        return hs.toUpperCase();  
    }
    
    private static final String PASSWORD_CRYPT_KEY = "091376ee255ec4a1a0ce755e4dce7386";  
    private final static String ALGORITHM = "DES" ;
    /** 
     * 对数据进行DES解密. 
     * @param data DES加密的数据 
     * @return 返回经过DES解密
     * @throws Exception 
     */  
    public final static String desDecrypt(String data){  
       try {
    	   return new String(decrypt(hex2byte(data.getBytes()),  
                   PASSWORD_CRYPT_KEY.getBytes()));  
		} catch (Exception e) {
			LogManager.err("desDecrypt error" , e);
		}
       return "" ;
    }  
    /** 
     * 对用DES加密过的数据进行加密 
     * @param data DES待加密数据 
     * @return 返回解密后的数据 
     */  
    public final static String desEncrypt(String data)  {  
        try {
        	return byte2hex(encrypt(data.getBytes(), PASSWORD_CRYPT_KEY  
                    .getBytes()));  
		} catch (Exception e){
			LogManager.err( "des-encrypt-error" , e);
		}
        return "" ;
    }
    
}
