package com.dream.example.utils;

import android.util.Base64;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author ngh AES128 算法
 *
 *         CBC 模式
 *
 *         PKCS7Padding 填充模式
 *
 *         CBC模式需要添加一个参数iv
 */
public class AESUtil
{
	// 算法名称
	final static String KEY_ALGORITHM = "AES";
	// 加解密算法/模式/填充方式
	final static String algorithmStr = "AES/CBC/PKCS5Padding";
	//
	private static Key key;
	private static Cipher cipher;
//	private static boolean isInited = false;

	private static byte[] iv = { 0x30, 0x33, 0x39, 0x32, 0x30, 0x33, 0x39, 0x32, 0x30, 0x33, 0x39, 0x32, 0x30, 0x33, 0x30, 0x30 };

	private static void init(byte[] keyBytes)
	{
		// 如果密钥不足16位，那么就补足. 这个if 中的内容很重要
		int base = 16;
		if (keyBytes.length % base != 0)
		{
			int groups = keyBytes.length / base
					+ (keyBytes.length % base != 0 ? 1 : 0);
			byte[] temp = new byte[groups * base];
			Arrays.fill(temp, (byte) 0);
			System.arraycopy(keyBytes, 0, temp, 0, keyBytes.length);
			keyBytes = temp;
		}
		// 转化成JAVA的密钥格式
		key = new SecretKeySpec(keyBytes, KEY_ALGORITHM);
		try
		{
			// 初始化cipher
			cipher = Cipher.getInstance(algorithmStr);
		}
		catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
		catch (NoSuchPaddingException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 加密方法
	 *
	 * @param content
	 *            要加密的字符串
	 * @param keyBytes
	 *            加密密钥
	 * @return
	 */
	public static byte[] encrypt(byte[] content, byte[] keyBytes)
	{
		byte[] encryptedText = null;
		init(keyBytes);
		try
		{
			cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(iv));
			encryptedText = cipher.doFinal(content);
		
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return encryptedText;
	}

	/**
	 * 解密方法
	 *
	 * @param encryptedData
	 *            要解密的字符串
	 * @param keyBytes
	 *            解密密钥
	 * @return
	 */
	public static String decrypt(byte[] encryptedData, byte[] keyBytes)
	{
		byte[] encryptedText = null;
		init(keyBytes);
		try
		{
			cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));
			encryptedText = cipher.doFinal(encryptedData);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return new String(encryptedText);
	}

	public static void main(String args[]) throws Exception
	{
		// 加解密 密钥
		byte[] keybytes = { 0x73 ,0X6D ,0X6B ,0X6C ,0X64 ,0X6F ,0X73 ,0X70 ,0X64 ,0X6F ,0X73 ,0X6C ,0X64 ,0X61 ,0X61 ,0X61 };
		String content = "123456";
		// 加密字符串
		System.out.println("加密前的：" + content);
		System.out.println("加密密钥：" + new String(keybytes));
		// 加密方法
		byte[] enc = AESUtil.encrypt(content.getBytes(), keybytes);
		System.out.println("加密后的内容：" + Base64.encodeToString(enc, Base64.DEFAULT));
		// 解密方法
		String dec = AESUtil.decrypt(enc, keybytes);
		System.out.println("解密后的内容：" + dec);

	}
}