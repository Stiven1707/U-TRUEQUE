package ports;

import domain.model.BlockChain;
import domain.model.Transaction;


public interface BlockChainRepository {
    BlockChain getBlockChain();
    void saveBlockChain(BlockChain blockChain);
}
