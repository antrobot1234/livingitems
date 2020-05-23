package com.antrobot.livingTools.objects.TileEntities;

import com.antrobot.livingTools.init.TileEntityInit;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;

public class TestTileEntity extends TileEntity implements ITickableTileEntity{
    public int y,tick;
    boolean initialized = false;
    public TestTileEntity(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    public TestTileEntity(){
        this(TileEntityInit.TEST_TE.get());
    }

    @Override
    public void tick() {
        if(!initialized){init();}
        tick++;
        if(tick==20){
            tick = 0;
            if(y<1){
                execute();
            }
            y++;
        }
    }
    private void init(){
        initialized = true;
        y = this.pos.getY();
        tick = 0;
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        compound.putInt("yOffset",y);
        return super.write(compound);
    }

    @Override
    public void read(CompoundNBT compound) {
        super.read(compound);
        this.y = compound.getInt("yOffset");
        this.tick = 0;
        initialized = true;
    }

    private void execute(){
        destroyBlock(this.getPos().up(y),true,null);
    }
    private boolean destroyBlock(BlockPos pos, Boolean dropBlock, @Nullable Entity entity){
        BlockState state = world.getBlockState(pos);
        if(state.isAir(world,pos))return false;
        IFluidState fluidState = world.getFluidState(pos);
        world.playEvent(2001,pos, Block.getStateId(state));
        if(dropBlock){
            TileEntity tileEntity = state.hasTileEntity() ? world.getTileEntity(pos) : null;
            Block.spawnDrops(state,world,this.pos.down(),tileEntity,entity, ItemStack.EMPTY);
        }
        return world.setBlockState(pos,fluidState.getBlockState(), 3);
    }
}
