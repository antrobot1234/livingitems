package com.antrobot.livingTools.objects.TileEntities;

import com.antrobot.livingTools.init.TileEntityInit;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.BlockTags;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;

public class TestTileEntity extends TileEntity implements ITickableTileEntity{
    public int tick;
    boolean initialized = false;
    public TestTileEntity(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    public TestTileEntity(){
        this(TileEntityInit.TEST_TE.get());
    }

    @Override
    public void tick() {
        if(world.isRemote)return;
        if(!initialized){init();}
        tick++;
        if(tick==20){
            tick = 0;
            execute();
        }
    }
    private void init(){
        initialized = true;
        tick = 0;
    }

    @Override
    public void read(CompoundNBT compound) {
        super.read(compound);
        this.tick = 0;
        initialized = true;
    }

    private void execute(){
        int y = 1;
        boolean success = false;
        for(y=y;y<32;y++){
            boolean[] values = isExistBreakable(this.getPos().up(y));
            if(values[0]){
                if (values[1]){
                    success = true;
                    break;
                }
                break;
            }
        }
        if(success){
            destroyBlock(this.getPos().up(y),true,null);
        }

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
    public boolean[] isExistBreakable(BlockPos pos){
        BlockState state = world.getBlockState(pos);
        if(state.isAir(world,pos)) return new boolean[]{false,false};
        if(!state.getFluidState().isEmpty()){
            if(state.getProperties().contains(BlockStateProperties.WATERLOGGED)){
                return new boolean[]{true, true};
            }
            return new boolean[]{false,false};
        }
        if(BlockTags.WITHER_IMMUNE.contains(state.getBlock())) return new boolean[]{true, false};
        return new boolean[]{true,true};
    }
}
