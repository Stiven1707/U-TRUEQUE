package co.unicauca.utrueque.application.ports.input;

import co.unicauca.utrueque.domain.model.BlockChain;
import co.unicauca.utrueque.domain.model.Transaction;


public interface IBlockChainRepository {
    BlockChain getBlockChain();
    void saveBlockChain(BlockChain blockChain);
}
