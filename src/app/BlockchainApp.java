package app;

import domain.model.Block;
import domain.model.BlockChain;
import domain.model.Transaction;
import domain.model.BlockChainService;
import domain.model.TransactionService;
import ports.BlockChainRepository;
import ports.TransactionRepository;

public class BlockchainApp implements BlockChainService, TransactionService {
    private final BlockChainRepository blockChainRepository;
    private final TransactionRepository transactionRepository;

    public BlockchainApp(BlockChainRepository blockChainRepository, TransactionRepository transactionRepository) {
        this.blockChainRepository = blockChainRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public BlockChain getBlockChain() {
        return blockChainRepository.getBlockChain();
    }

    @Override
    public void addBlock(Block block) {
        block.setPreviousHash(getBlockChain().getLatestBlock().getHash());
        block.mineBlock(2);
        //getBlockChain().addBlock(block);;
    }


    @Override
    public boolean isChainValid() {
    	return getBlockChain().isChainValid();
    }

    @Override
    public void addTransaction(Transaction transaction) {
        transactionRepository.addTransaction(transaction);
    }

    @Override
    public boolean processTransactions() {
    	getBlockChain().minePendingTransactions("miningRewardAddress");
    	return true;
    }

}
