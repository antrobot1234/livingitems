package com.antrobot.livingTools.init;

import com.antrobot.livingTools.LivingTools;
import com.antrobot.livingTools.objects.containers.TestContainer;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ContainerInit {
    public static final DeferredRegister<ContainerType<?>> CONTAINERS = new DeferredRegister<>(ForgeRegistries.CONTAINERS, LivingTools.MOD_ID);

    public static final RegistryObject<ContainerType<TestContainer>> TEST_CONTAINER = CONTAINERS.register("test_container",()-> IForgeContainerType.create(TestContainer::new));
}
