package com.antrobot.livingTools.objects.TileEntities;

import com.antrobot.livingTools.init.TileEntityInit;
import com.antrobot.livingTools.objects.blocks.TestContainerBlock;
import com.antrobot.livingTools.objects.containers.TestContainer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TestContainerTileEntity extends LockableLootTileEntity {
    public TestContainerTileEntity(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }
    public TestContainerTileEntity(){
        this(TileEntityInit.TEST_CONTAINER_TE.get());
    }
    private NonNullList<ItemStack> contents = NonNullList.withSize(36,ItemStack.EMPTY);
    protected int numPlayersUsing;
    private IItemHandlerModifiable items = createHandler();
    private LazyOptional<IItemHandlerModifiable> itemHandler = LazyOptional.of(()->items);
    @Override
    protected ITextComponent getDefaultName() {
        return new TranslationTextComponent("container.test_container");
    }

    @Override
    protected Container createMenu(int id, PlayerInventory player) {
        return new TestContainer(id,player,this);
    }

    @Override
    public NonNullList<ItemStack> getItems() {
        return this.contents;
    }

    @Override
    public void setItems(NonNullList<ItemStack> itemsIn) {
        this.contents = itemsIn;
    }

    @Override
    public int getSizeInventory() {
        return 36;
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        if(!this.checkLootAndWrite(compound)) ItemStackHelper.saveAllItems(compound, this.contents);
        return compound;
    }

    @Override
    public void read(CompoundNBT compound) {
        super.read(compound);
        this.contents = NonNullList.withSize(this.getSizeInventory(),ItemStack.EMPTY);
        if(!this.checkLootAndRead(compound)){
            ItemStackHelper.loadAllItems(compound,this.contents);
        }
    }
    private void playSound(SoundEvent sound){
        double dx = (double)this.pos.getX() + .5;
        double dy = (double)this.pos.getY() + .5;
        double dz = (double)this.pos.getZ() + .5;
        this.world.playSound((PlayerEntity)null,dx,dy,dz,sound,SoundCategory.BLOCKS,0.5f,this.world.rand.nextFloat()*.1f+.9f);
    }

    @Override
    public boolean receiveClientEvent(int id, int type) {
        if(id==1){
            this.numPlayersUsing = type;
            return true;
        }
        return super.receiveClientEvent(id,type);
    }

    @Override
    public void openInventory(PlayerEntity player) {
        if(!player.isSpectator()){
            if(this.numPlayersUsing >0){
                this.numPlayersUsing=0;
            }
            this.numPlayersUsing++;
            this.openOrClose();
        }
    }

    @Override
    public void closeInventory(PlayerEntity player) {
        if(!player.isSpectator()){
            this.numPlayersUsing--;
            this.openOrClose();
        }
    }
    public void openOrClose(){
        Block block = this.getBlockState().getBlock();
        if(block instanceof TestContainerBlock){
            this.world.addBlockEvent(this.pos,block,1,this.numPlayersUsing);
            this.world.notifyNeighborsOfStateChange(this.pos,block);
        }
    }

    public int getNumPlayersUsing(IBlockReader reader,BlockPos pos) {
        BlockState blockState = reader.getBlockState(pos);
        if(blockState.hasTileEntity()){
            TileEntity tileEntity = reader.getTileEntity(pos);
            if(tileEntity instanceof TestContainerTileEntity){
                return((TestContainerTileEntity)tileEntity).numPlayersUsing;
            }
        }
        return 0;
    }
    public static void swapContents(TestContainerTileEntity tile, TestContainerTileEntity other){
        NonNullList<ItemStack> list = tile.getItems();
        tile.setItems(other.getItems());
        other.setItems(list);
    }

    @Override
    public void updateContainingBlockInfo() {
        super.updateContainingBlockInfo();
        if(this.itemHandler != null){
            this.itemHandler.invalidate();
            this.itemHandler = null;
        }
    }

    @Nullable
    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side) {
        if(cap== CapabilityItemHandler.ITEM_HANDLER_CAPABILITY){
            return itemHandler.cast();
        }
        return super.getCapability(cap,side);
    }
    private IItemHandlerModifiable createHandler(){
        return new InvWrapper(this);
    }

    @Override
    public void remove() {
        super.remove();
        if(itemHandler != null){
            itemHandler.invalidate();
        }
    }
}
