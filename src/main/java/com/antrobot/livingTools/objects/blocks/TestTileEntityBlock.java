package com.antrobot.livingTools.objects.blocks;

import com.antrobot.livingTools.init.TileEntityInit;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;

public class TestTileEntityBlock extends Block {
    public TestTileEntityBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return TileEntityInit.TEST_TE.get().create();
    }
}
