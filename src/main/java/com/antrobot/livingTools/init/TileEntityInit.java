package com.antrobot.livingTools.init;

import com.antrobot.livingTools.LivingTools;
import com.antrobot.livingTools.objects.TileEntities.TestContainerTileEntity;
import com.antrobot.livingTools.objects.TileEntities.TestTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class TileEntityInit {
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITES = new DeferredRegister<>(ForgeRegistries.TILE_ENTITIES, LivingTools.MOD_ID);

    public static final RegistryObject<TileEntityType<TestTileEntity>> TEST_TE = TILE_ENTITES.register("test_te", ()->TileEntityType.Builder.create(TestTileEntity::new,BlockInit.TEST_TE_BLOCK.get()).build(null));
    public static final RegistryObject<TileEntityType<TestContainerTileEntity>> TEST_CONTAINER_TE = TILE_ENTITES.register("test_container",()->TileEntityType.Builder.create(TestContainerTileEntity::new,BlockInit.TEST_CONTAINER_BLOCK.get()).build(null));
}
