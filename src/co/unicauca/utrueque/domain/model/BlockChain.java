package co.unicauca.utrueque.domain.model;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BlockChain {
	private ArrayList<Block> chain;
    private int difficulty;
    private ArrayList<Transaction> pendingTransactions;
    private int miningReward;

    public BlockChain() {
        this.chain = new ArrayList<>();
        this.chain.add(createGenesisBlock());
        this.difficulty = 4;
        this.pendingTransactions = new ArrayList<>();
        this.miningReward = 100;
    }

    private Block createGenesisBlock() {
        return new Block(new Date().getTime(), new ArrayList<>(), "0");
    }

    public Block getLatestBlock() {
        return chain.get(chain.size() - 1);
    }

    public void minePendingTransactions(String miningRewardAddress) {
        Transaction rewardTx = new Transaction("origen", miningRewardAddress, this.miningReward);
        this.pendingTransactions.add(rewardTx);

        Block block = new Block(new Date().getTime(), this.pendingTransactions, getLatestBlock().getHash());
        block.mineBlock(this.difficulty);

        System.out.println("Block successfully mined!");
        this.chain.add(block);

        this.pendingTransactions = new ArrayList<>();
    }

    public void addTransaction(Transaction transaction) throws InvalidKeyException, NoSuchAlgorithmException, SignatureException {
        if (transaction.getFromAddress() == null || transaction.getToAddress() == null) {
            throw new IllegalArgumentException("Transaction must include from and to address");
        }

        if (!transaction.isValid()) {
            throw new IllegalArgumentException("Cannot add invalid transaction to chain");
        }

        if (transaction.getAmount() <= 0) {
            throw new IllegalArgumentException("Transaction amount should be higher than 0");
        }

        int walletBalance = getBalanceOfAddress(transaction.getFromAddress());
        if (walletBalance < transaction.getAmount()) {
            throw new IllegalArgumentException("Not enough balance");
        }

        ArrayList<Transaction> pendingTxForWallet = new ArrayList<>();
        for (Transaction tx : this.pendingTransactions) {
            if (tx.getFromAddress().equals(transaction.getFromAddress())) {
                pendingTxForWallet.add(tx);
            }
        }

        if (pendingTxForWallet.size() > 0) {
            int totalPendingAmount = 0;
            for (Transaction tx : pendingTxForWallet) {
                totalPendingAmount += tx.getAmount();
            }

            double totalAmount = totalPendingAmount + transaction.getAmount();
            if (totalAmount > walletBalance) {
                throw new IllegalArgumentException("Pending transactions for this wallet is higher than its balance.");
            }
        }

        this.pendingTransactions.add(transaction);
        System.out.println("Transaction added: " + transaction);
    }
    public int getBalanceOfAddress(String address) {
        int balance = 0;
        for (Block block : this.chain) {
            for (Transaction trans : block.getTransactions()) {
                if (trans.getFromAddress() .equals(address)) {
                    balance -= trans.getAmount();
                }
                if (trans.getToAddress().equals(address)) {
                    balance += trans.getAmount();
                }
            }
        }
        System.out.println("Get balance of address: " + balance);
        return balance;
    }
    public List<Transaction> getAllTransactionsForWallet(String address) {
        List<Transaction> txs = new ArrayList<>();

        for (Block block : this.chain) {
            for (Transaction tx : block.getTransactions()) {
                if (tx.getFromAddress().equals(address) || tx.getToAddress().equals(address)) {
                    txs.add(tx);
                }
            }
        }

        System.out.println("get transactions for wallet count: " + txs.size());
        return txs;
    }
    public boolean isChainValid() {
        // Check if the Genesis block hasn't been tampered with by comparing
        // the output of createGenesisBlock with the first block on our chain
        String realGenesis = createGenesisBlock().toString();
        
        if (!realGenesis.equals(this.chain.get(0).toString())) {
            return false;
        }

        // Check the remaining blocks on the chain to see if there hashes and
        // signatures are correct
        for (int i = 1; i < chain.size(); i++) {
            Block currentBlock = chain.get(i);
            Block previousBlock = chain.get(i - 1);

            if (!previousBlock.getHash().equals(currentBlock.getPreviousHash())) {
                return false;
            }
            if (!currentBlock.getHash().equals(currentBlock.calculateHash())) {
                return false;
            }
        }
        return true;
    }



}
