package br.mil.fab.consigext.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.xerces.impl.dv.util.Base64;

public class PasswordUtil {
	public PasswordUtil() {
		super();
	}

	public static String sha1x64(String input){
		MessageDigest mDigest;
		try {
			mDigest = MessageDigest.getInstance("SHA1");
			byte[] result = mDigest.digest(input.getBytes());
			return "{SHA}" + Base64.encode(result).toString();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "";
	}
	
	public static boolean passwordPattern(String input) throws NoSuchAlgorithmException {

		String regex = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-Z\\d\\s]).{8,})";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(input);

		return matcher.matches();
	}

	public static String getRandomValue(int min, int max) {

		String allowedChars = "abcdefghijklmnopqrstuvwxyzABCDEFGRHIJKLMNOPQRSTUVWXYZ0123456789@#$%";
		Random random = new Random();
		int length = random.nextInt(max - min + 1) + min;
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < length; i++) {
			sb.append(allowedChars.charAt(random.nextInt(allowedChars.length())));
		}

		return sb.toString();
	}
	
}