package co.unicauca.utrueque.domain.service;

import co.unicauca.utrueque.domain.model.Transaction;

public interface ITransactionService {
    void addTransaction(Transaction transaction);
    boolean processTransactions();
}