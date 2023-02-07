package ports;

import domain.model.BlockChain;
import domain.model.Transaction;


public interface TransactionRepository {
    void addTransaction(Transaction transaction);
    Transaction[] getTransactions();
}
