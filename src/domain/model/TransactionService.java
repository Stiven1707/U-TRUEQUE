package domain.model;

public interface TransactionService {
    void addTransaction(Transaction transaction);
    boolean processTransactions();
}