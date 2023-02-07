package co.unicauca.utrueque.domain.model;

import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.Date;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class Transaction extends Sha256{
    private String fromAddress;
    private String toAddress;
    private double amount;
    private long timestamp;
    private byte[] signature;

    public Transaction(String fromAddress, String toAddress, double amount) {
        this.fromAddress = fromAddress;
        this.toAddress = toAddress;
        this.amount = amount;
        this.timestamp = new Date().getTime();
    }
    public String calculateHash() {
    	String data = fromAddress + toAddress + Double.toString(this.amount);
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] hash = messageDigest.digest(data.getBytes());
        return super.bytesToHex(hash);
    }
    public void signTransaction(String signingKey) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        if (!signingKey.equals(fromAddress)) {
            throw new SignatureException("You cannot sign transactions for other wallets");
        }

        String hashTx = calculateHash();
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(signingKey.getBytes(), "HmacSHA256");
        sha256_HMAC.init(secret_key);
        
        this.signature = sha256_HMAC.doFinal(hashTx.getBytes());
    }

    public boolean isValid() throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        if (fromAddress == null) {
            return true;
        }
        System.out.println("isValid:"+signature);
        if (signature == null || signature.length == 0) {
            throw new SignatureException("No signature in this transaction");
        }

        String hashTx = calculateHash();
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(fromAddress.getBytes(), "HmacSHA256");
        sha256_HMAC.init(secret_key);
        
        return MessageDigest.isEqual(signature, sha256_HMAC.doFinal(hashTx.getBytes()));
    }
	public String getFromAddress() {
		return fromAddress;
	}
	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}
	public String getToAddress() {
		return toAddress;
	}
	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
    
}
