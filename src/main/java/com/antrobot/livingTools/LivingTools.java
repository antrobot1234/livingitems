package com.antrobot.livingTools;

import com.antrobot.livingTools.init.BlockInit;
import com.antrobot.livingTools.init.ItemInit;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.IForgeRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.antrobot.livingTools.LivingTools.MOD_ID;

@Mod("livingtools")
@Mod.EventBusSubscriber(modid=MOD_ID,bus = Mod.EventBusSubscriber.Bus.MOD)
public class LivingTools
{
    private static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "livingtools";

    public static final ItemGroup TAB = new ItemGroup("livingtoolsTab") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ItemInit.TESTITEM.get());
        }
    };

    public LivingTools() {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientInit);

        // Register ourselves for server and other game events we are interested in
        ItemInit.ITEMS.register(modEventBus);
        BlockInit.BLOCKS.register(modEventBus);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event){}
    private void clientInit(final FMLClientSetupEvent event){}
    private void enqueueIMC(final InterModEnqueueEvent event){}
    private void processIMC(final InterModProcessEvent event){}

    @SubscribeEvent
    public void onServerStart(FMLServerStartingEvent event){}
    @SubscribeEvent
    public static void onRegisterItems(final RegistryEvent.Register<Item> event){
        final IForgeRegistry<Item> registry = event.getRegistry();
        BlockInit.BLOCKS.getEntries().stream().map(RegistryObject::get).forEach(block ->{
            final Item.Properties properties = new Item.Properties().group(TAB);
            final BlockItem blockItem = new BlockItem(block,properties);
            blockItem.setRegistryName(block.getRegistryName());
            registry.register(blockItem);
        });
    }
}
