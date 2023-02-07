package co.unicauca.utrueque.application.ports.input;

import co.unicauca.utrueque.domain.model.BlockChain;
import co.unicauca.utrueque.domain.model.Transaction;


public interface ITransactionRepository {
    void addTransaction(Transaction transaction);
    Transaction[] getTransactions();
}
