package com.antrobot.livingTools.init;

import com.antrobot.livingTools.LivingTools;
import com.antrobot.livingTools.objects.blocks.TestContainerBlock;
import com.antrobot.livingTools.objects.blocks.TestTileEntityBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockInit {
    public static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS, LivingTools.MOD_ID);

    public static final RegistryObject<Block> TESTBLOCK = BLOCKS.register("testblock", () -> new Block(Block.Properties.create(Material.WOOL).hardnessAndResistance(0.5f)));
    public static final RegistryObject<Block> TEST_TE_BLOCK = BLOCKS.register("te_block",()->new TestTileEntityBlock(Block.Properties.create(Material.WOOD)));
    public static final RegistryObject<Block> TEST_CONTAINER_BLOCK = BLOCKS.register("test_container",()->new TestContainerBlock(Block.Properties.from(Blocks.IRON_BLOCK)));
}
