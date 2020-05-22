package com.antrobot.livingTools.init;

import com.antrobot.livingTools.LivingTools;
import com.antrobot.livingTools.utils.ModItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.PickaxeItem;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;


public class ItemInit {
    public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, LivingTools.MOD_ID);
    public static final RegistryObject<Item> TESTITEM = ITEMS.register("testitem", () -> new Item(new Item.Properties().group(LivingTools.TAB)));
    public static final RegistryObject<Item> TESTTOOL = ITEMS.register("testtool",() -> new PickaxeItem(ModItemTier.EXAMPLE,4,2,new Item.Properties().group(LivingTools.TAB)));
}
