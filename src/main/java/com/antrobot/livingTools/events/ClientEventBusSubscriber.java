package com.antrobot.livingTools.events;

import com.antrobot.livingTools.client.gui.TestContainerScreen;
import com.antrobot.livingTools.init.ContainerInit;
import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import static com.antrobot.livingTools.LivingTools.MOD_ID;
import static com.antrobot.livingTools.init.ContainerInit.*;

@Mod.EventBusSubscriber(modid=MOD_ID,bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventBusSubscriber {
    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event){
        ScreenManager.registerFactory(TEST_CONTAINER.get(), TestContainerScreen::new);
    }
}
