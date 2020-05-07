package ua.javaexternal_shulzhenko.repair_service.tags.blocks;

public class AsideMainBlocks extends AbstractBlocksChildTag {
    @Override
    public boolean shouldExecute(BlocksParentTag tag){
        if(!tag.isProcessed()){
            return true;
        }
        return false;
    }
}