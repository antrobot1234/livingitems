package com.antrobot.livingTools.objects.items;

import com.antrobot.livingTools.LivingTools;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class SpecialItem extends Item {
    private static final Properties itemProperties = new Item.Properties().group(LivingTools.TAB).maxStackSize(1);
    public SpecialItem(Properties properties) {
        super(itemProperties);
    }
    public boolean hasEffect(ItemStack stack){
        return stack.getOrCreateChildTag(LivingTools.MOD_ID).getBoolean("isEntitySneaking");
    }

    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        stack.getOrCreateChildTag(LivingTools.MOD_ID).putBoolean("isEntitySneaking",entityIn.isSneaking());
        super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
    }

    @Override
    public boolean onDroppedByPlayer(ItemStack item, PlayerEntity player) {
        item.getOrCreateChildTag(LivingTools.MOD_ID).putBoolean("isEntitySneaking",false);
        return true;
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return false;
    }
}
