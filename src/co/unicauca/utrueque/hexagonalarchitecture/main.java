package co.unicauca.utrueque.hexagonalarchitecture;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import co.unicauca.utrueque.domain.model.*;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.Security;
import java.util.Base64;
import java.util.HashMap;

public class main {

	public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {

	    String myWalletAddressPrivate = "Codigo hash";
	    String myWalletAddress = "Codigo hash";

	    BlockChain UTrueque = new BlockChain();

	    // Mine the first block
	    UTrueque.minePendingTransactions(myWalletAddress);

	    // Create and sign a transaction
	    Transaction tx1 = new Transaction(myWalletAddress, "address2", 100);
	    tx1.signTransaction(myWalletAddressPrivate);
	    UTrueque.addTransaction(tx1);

	    // Mine a block
	    UTrueque.minePendingTransactions(myWalletAddress);

	    // Create a second transaction
	    Transaction tx2 = new Transaction(myWalletAddress, "address1", 50);
	    tx2.signTransaction(myWalletAddressPrivate);
	    UTrueque.addTransaction(tx2);

	    // Mine a block
	    UTrueque.minePendingTransactions(myWalletAddress);

	    System.out.println();
	    System.out.println("Balance of xavier is: " + UTrueque.getBalanceOfAddress(myWalletAddress));

	    // Uncomment this line if you want to test tampering with the chain
	    // UTrueque.chain[1].transactions[0].amount = 10;

	    // Check if the chain is valid
	    System.out.println();
	    System.out.println("Blockchain valid? " + (UTrueque.isChainValid() ? "Yes" : "No"));
	}

}
