package com.antrobot.livingTools.objects.containers;

import com.antrobot.livingTools.init.BlockInit;
import com.antrobot.livingTools.init.ContainerInit;
import com.antrobot.livingTools.objects.TileEntities.TestContainerTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;

import java.util.Objects;

public class TestContainer extends Container {
    public final TestContainerTileEntity tileEntity;
    private final IWorldPosCallable canInteractWithCallable;

    public TestContainer(final int windowId, final PlayerInventory inventory, final TestContainerTileEntity tile){
        super(ContainerInit.TEST_CONTAINER.get(),windowId);
        this.tileEntity = tile;
        this.canInteractWithCallable = IWorldPosCallable.of(tile.getWorld(),tile.getPos());

        //Main Inventory
        int startX = 8;
        int startY = 18;
        int slotSizePlus2 = 18;

        for(int row =0;row<4;row++){
            for(int col=0;col<9;col++){
                this.addSlot(new Slot(tileEntity,(row*9)+col,startX+(col*slotSizePlus2),startY+(row*slotSizePlus2) ));
            }
        }

        //Player Inventory
        int playerY = 102;
        for(int row=0;row<3;row++){
            for(int col=0;col<9;col++){
                this.addSlot(new Slot(inventory,9+(row*9)+col,startX+(col*slotSizePlus2),playerY+(row*slotSizePlus2)));
            }
        }
        //Player Hotbar
        int hotbarY = 160;
        for(int col=0;col<9;col++){
            this.addSlot(new Slot(inventory,col,startX+(col*slotSizePlus2),hotbarY));
        }
    }
    public TestContainer(final int windowId, final PlayerInventory inventory, final PacketBuffer data){
        this(windowId,inventory,getTileEntity(inventory,data));
    }
    private static TestContainerTileEntity getTileEntity(final PlayerInventory inventory, final PacketBuffer data){
        Objects.requireNonNull(inventory,"playerInventory Cannot be Null");
        Objects.requireNonNull(data,"data cannot be Null");
        final TileEntity tileAtPos = inventory.player.world.getTileEntity(data.readBlockPos());
        if(tileAtPos instanceof TestContainerTileEntity){
            return (TestContainerTileEntity)tileAtPos;
        }
        throw new IllegalStateException("tileEntity is not correct: "+tileAtPos);
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return isWithinUsableDistance(canInteractWithCallable,playerIn, BlockInit.TEST_CONTAINER_BLOCK.get());
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack stack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        if(slot != null && slot.getHasStack()){
            ItemStack dummyStack = slot.getStack();
            stack = dummyStack.copy();
            if(index<36){
                if(!this.mergeItemStack(dummyStack,36,this.inventorySlots.size(),true)){
                    return ItemStack.EMPTY;
                }
            }
            else if (!this.mergeItemStack(dummyStack,0,36,false)){
                return ItemStack.EMPTY;
            }
            if(dummyStack.isEmpty()){
                slot.putStack(ItemStack.EMPTY);
            }
            else{
                slot.onSlotChanged();
            }
        }
        return stack;
    }
}
