package domain.model;

public interface BlockChainService {
    BlockChain getBlockChain();
    void addBlock(Block block);
    boolean isChainValid();
}
