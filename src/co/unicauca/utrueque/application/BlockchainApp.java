package co.unicauca.utrueque.application;

import java.util.List;

import co.unicauca.utrueque.application.ports.input.IBlockChainRepository;
import co.unicauca.utrueque.application.ports.input.ITransactionRepository;
import co.unicauca.utrueque.domain.model.Block;
import co.unicauca.utrueque.domain.model.BlockChain;
import co.unicauca.utrueque.domain.model.BlockService;
import co.unicauca.utrueque.domain.model.Transaction;
import co.unicauca.utrueque.domain.service.IBlockChainService;
import co.unicauca.utrueque.domain.service.ITransactionService;

public class BlockchainApp implements IBlockChainService, ITransactionService {
    private final IBlockChainRepository blockChainRepository;
    private final ITransactionRepository transactionRepository;

    public BlockchainApp(IBlockChainRepository blockChainRepository, ITransactionRepository transactionRepository) {
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

	@Override
	public void addTransaction(ITransactionService transaction) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addBlock(BlockService block) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<BlockService> getBlocks() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ITransactionService> getTransactions() {
		// TODO Auto-generated method stub
		return null;
	}

}
