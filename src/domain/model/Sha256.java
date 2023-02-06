package domain.model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public abstract class Sha256 {
	
	public abstract String calculateHash();
	protected static String bytesToHex(byte[] hash) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
