package com.example.workjob.until;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

import com.example.workjob.entity.UserInfo;

public class GeneralMethod {
	 private static final String DES_ALGORITHM = "DES";
	//MD5加密
	public final static String MD5(String s) {
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F' };
        try {
            byte[] btInput = s.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
	
	public static String getMd5(String str){
		String result = "";		
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update((str).getBytes("UTF-8"));
			byte b[] = md5.digest();
			int i;
			StringBuffer buf = new StringBuffer("");
			 
			for(int offset=0; offset<b.length; offset++){
				i = b[offset];
				if(i<0){
					i+=256;
				}
				if(i<16){
					buf.append("0");
				}
				buf.append(Integer.toHexString(i));
			}
			 
			result = buf.toString();
			return result;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}
	
	public static String addSaltMD5(UserInfo userInfo){
		
		String hashAlgorithmName = "MD5";//加密方式
		Object crdentials = userInfo.getPassword();//密码原值
		ByteSource salt = ByteSource.Util.bytes(userInfo.getUid() + "--" + userInfo.getUsername());//以uid--账号作为盐值
		int hashIterations = 1;//加密1024次
		SimpleHash result = new SimpleHash(hashAlgorithmName,crdentials,salt,hashIterations);
		return result.toString();
	}
	
	
	//获得当前时间date类型
	public static Date getDate(){
		
		SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date=null;
		try {
			date=dfs.parse(dfs.format(new Date()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}
	
	

	public static String getDateString(){
		
		SimpleDateFormat dfs = new SimpleDateFormat("yyyyMMddHHmmss");
		String date=null;
		date=dfs.format(new Date());		
		return date;
	}
	
	public static String getDayString(){
		
		SimpleDateFormat dfs = new SimpleDateFormat("yyyyMMdd");
		String date=null;
		date=dfs.format(new Date());		
		return date;
	}
	
	public static void writeFile(String filePath,String fileName,String content) {
		FileWriter fw = null;
		// 如果文件存在，则追加内容；如果文件不存在，则创建文件
		File f = new File(filePath);
        if(!f.exists()){    
            f.mkdirs();    
        }
        File realFile=new File(filePath+fileName);
		try {
			fw = new FileWriter(realFile, true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		PrintWriter pw = new PrintWriter(fw);
		pw.println(content);
		pw.flush();
		try {
			fw.flush();
			pw.close();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//获得两个时间相差的分钟数
	public static long getDifferMinute(long serverDate,Date currentDate){
		return Math.abs( serverDate - currentDate.getTime()) /(1000*60);
	}
	
	//获得时间戳
	public static Long getTimestamp(Date date){
		
		return date.getTime();
		
	}
	
	public static Date  getDate(String timestamp) throws ParseException{
		SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date=null;
	
		date=dfs.parse(timestamp);

		return date;
	}

	
	 public static String encryption(String plainData, String secretKey) throws Exception {

	        Cipher cipher = null;
	        try {
	            cipher = Cipher.getInstance(DES_ALGORITHM);
	            cipher.init(Cipher.ENCRYPT_MODE, generateKey(secretKey));

	        } catch (NoSuchAlgorithmException e) {
	            e.printStackTrace();
	        } catch (NoSuchPaddingException e) {
	            e.printStackTrace();
	        } catch (InvalidKeyException e) {

	        }

	        try {
	            // 为了防止解密时报javax.crypto.IllegalBlockSizeException: Input length must
	            // be multiple of 8 when decrypting with padded cipher异常，
	            // 不能把加密后的字节数组直接转换成字符串
	            byte[] buf = cipher.doFinal(plainData.getBytes());

	            return Base64Utils.encode(buf);

	        } catch (IllegalBlockSizeException e) {
	            e.printStackTrace();
	            throw new Exception("IllegalBlockSizeException", e);
	        } catch (BadPaddingException e) {
	            e.printStackTrace();
	            throw new Exception("BadPaddingException", e);
	        }

	    }


	    /**
	     * DES解密
	     * @param secretData 密码字符串
	     * @param secretKey 解密密钥
	     * @return 原始字符串
	     * @throws Exception
	     */
	    public static String decryption(String secretData, String secretKey) throws Exception {

	        Cipher cipher = null;
	        try {
	            cipher = Cipher.getInstance(DES_ALGORITHM);
	            cipher.init(Cipher.DECRYPT_MODE, generateKey(secretKey));

	        } catch (NoSuchAlgorithmException e) {
	            e.printStackTrace();
	            throw new Exception("NoSuchAlgorithmException", e);
	        } catch (NoSuchPaddingException e) {
	            e.printStackTrace();
	            throw new Exception("NoSuchPaddingException", e);
	        } catch (InvalidKeyException e) {
	            e.printStackTrace();
	            throw new Exception("InvalidKeyException", e);

	        }

	        try {

	            byte[] buf = cipher.doFinal(Base64Utils.decode(secretData.toCharArray()));

	            return new String(buf);

	        } catch (IllegalBlockSizeException e) {
	            e.printStackTrace();
	            throw new Exception("IllegalBlockSizeException", e);
	        } catch (BadPaddingException e) {
	            e.printStackTrace();
	            throw new Exception("BadPaddingException", e);
	        }
	    }

	    /**
	     * 获得秘密密钥
	     * 
	     * @param secretKey
	     * @return
	     * @throws NoSuchAlgorithmException
	     * @throws InvalidKeySpecException
	     * @throws InvalidKeyException
	     */
	    private static SecretKey generateKey(String secretKey)
	            throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException {
	        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES_ALGORITHM);
	        DESKeySpec keySpec = new DESKeySpec(secretKey.getBytes());
	        keyFactory.generateSecret(keySpec);
	        return keyFactory.generateSecret(keySpec);
	    }

	    static private class Base64Utils {

	        static private char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/="
	                .toCharArray();
	        static private byte[] codes = new byte[256];

	        static {
	            for (int i = 0; i < 256; i++)
	                codes[i] = -1;
	            for (int i = 'A'; i <= 'Z'; i++)
	                codes[i] = (byte) (i - 'A');
	            for (int i = 'a'; i <= 'z'; i++)
	                codes[i] = (byte) (26 + i - 'a');
	            for (int i = '0'; i <= '9'; i++)
	                codes[i] = (byte) (52 + i - '0');
	            codes['+'] = 62;
	            codes['/'] = 63;
	        }

	        /**
	         * 将原始数据编码为base64编码
	         */
	        static private String encode(byte[] data) {
	            char[] out = new char[((data.length + 2) / 3) * 4];
	            for (int i = 0, index = 0; i < data.length; i += 3, index += 4) {
	                boolean quad = false;
	                boolean trip = false;
	                int val = (0xFF & (int) data[i]);
	                val <<= 8;
	                if ((i + 1) < data.length) {
	                    val |= (0xFF & (int) data[i + 1]);
	                    trip = true;
	                }
	                val <<= 8;
	                if ((i + 2) < data.length) {
	                    val |= (0xFF & (int) data[i + 2]);
	                    quad = true;
	                }
	                out[index + 3] = alphabet[(quad ? (val & 0x3F) : 64)];
	                val >>= 6;
	                out[index + 2] = alphabet[(trip ? (val & 0x3F) : 64)];
	                val >>= 6;
	                out[index + 1] = alphabet[val & 0x3F];
	                val >>= 6;
	                out[index + 0] = alphabet[val & 0x3F];
	            }

	            return new String(out);
	        }

	        /**
	         * 将base64编码的数据解码成原始数据
	         */
	        static private byte[] decode(char[] data) {
	            int len = ((data.length + 3) / 4) * 3;
	            if (data.length > 0 && data[data.length - 1] == '=')
	                --len;
	            if (data.length > 1 && data[data.length - 2] == '=')
	                --len;
	            byte[] out = new byte[len];
	            int shift = 0;
	            int accum = 0;
	            int index = 0;
	            for (int ix = 0; ix < data.length; ix++) {
	                int value = codes[data[ix] & 0xFF];
	                if (value >= 0) {
	                    accum <<= 6;
	                    shift += 6;
	                    accum |= value;
	                    if (shift >= 8) {
	                        shift -= 8;
	                        out[index++] = (byte) ((accum >> shift) & 0xff);
	                    }
	                }
	            }
	            if (index != out.length)
	                throw new Error("miscalculated data length!");
	            return out;
	        }
	    }
}
