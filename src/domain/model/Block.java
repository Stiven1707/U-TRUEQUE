package domain.model;
import java.util.ArrayList;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;

public class Block extends Sha256 {
    private String previousHash;
    private long timestamp;
    private ArrayList<Transaction> transactions;
    private int nonce;
    private String hash;

    public Block(long timestamp, ArrayList<Transaction> transactions, String previousHash) {
        this.previousHash = previousHash;
        this.timestamp = timestamp;
        this.transactions = transactions;
        this.nonce = 0;
        this.hash = calculateHash();
    }

    public String calculateHash() {
        String data = previousHash + Long.toString(timestamp) + transactions.toString() + Integer.toString(nonce);
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] hash = messageDigest.digest(data.getBytes());
        return super.bytesToHex(hash);
    }

    public void mineBlock(int difficulty) {
        String target = new String(new char[difficulty]).replace('\0', '0');
        while (!hash.substring(0, difficulty).equals(target)) {
            nonce++;
            hash = calculateHash();
        }
        System.out.println("Block mined: " + hash);
    }

    public boolean hasValidTransactions() {
        for (Transaction tx : transactions) {
            try {
				if (!tx.isValid()) {
				    return false;
				}
			} catch (InvalidKeyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SignatureException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        return true;
    }

	public String getHash() {
		return hash;
	}

	public ArrayList<Transaction> getTransactions() {
		return transactions;
	}

	public String getPreviousHash() {
		return previousHash;
	}
	
	public void setPreviousHash(String previousHash) {
		this.previousHash = previousHash;
	}

	@Override
	public String toString() {
		return "Block [transactions=" + transactions + ", nonce=" + nonce + "]";
	}
    
}
