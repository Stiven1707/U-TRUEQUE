package co.unicauca.utrueque.domain.service;

import java.util.List;

import co.unicauca.utrueque.domain.model.Block;
import co.unicauca.utrueque.domain.model.BlockChain;
import co.unicauca.utrueque.domain.model.BlockService;

public interface IBlockChainService {
    BlockChain getBlockChain();
    void addTransaction(ITransactionService transaction);
    void addBlock(Block block);
    boolean isChainValid();
	void addBlock(BlockService block);
	List<BlockService> getBlocks();
	List<ITransactionService> getTransactions();
}
