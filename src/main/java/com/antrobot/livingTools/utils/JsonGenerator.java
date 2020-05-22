package com.antrobot.livingTools.utils;

import com.antrobot.livingTools.init.BlockInit;
import net.minecraft.block.Blocks;
import net.minecraft.data.*;
import net.minecraftforge.common.Tags;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

import java.util.function.Consumer;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class JsonGenerator {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event){
        DataGenerator generator = event.getGenerator();
        generator.addProvider(new Recipes(generator));
        generator.addProvider(new LootTableProvider(generator));
    }
}
class Recipes extends RecipeProvider {
    public Recipes(DataGenerator generatorIn) {
        super(generatorIn);
    }
    @Override
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer){
        ShapedRecipeBuilder.shapedRecipe(BlockInit.TESTBLOCK.get())
                .patternLine("xxx")
                .patternLine("xox")
                .patternLine("xxx")
                .key('x', Tags.Items.COBBLESTONE)
                .key('o',Tags.Items.DYES)
                .setGroup("livingtools")
                .build(consumer);
    }
}