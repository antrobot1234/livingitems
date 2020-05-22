package com.antrobot.livingTools.init;

import com.antrobot.livingTools.LivingTools;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockInit {
    public static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS, LivingTools.MOD_ID);
    public static final RegistryObject<Block> TESTBLOCK = BLOCKS.register("testblock", () -> new Block(Block.Properties.create(Material.IRON)));
}
