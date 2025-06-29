package com.kito1z.cc_ar;

import com.kito1z.cc_ar.client.elements.ARRenderer;
import com.kito1z.cc_ar.common.ElementsServer;
import com.kito1z.cc_ar.network.CCARNetwork;
import com.kito1z.cc_ar.registry.*;
import com.mojang.logging.LogUtils;
import dan200.computercraft.api.ForgeComputerCraftAPI;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(CCARMod.MODID)
public class CCARMod {


    public static final String MODID = "cc_ar";
    public static final Logger LOGGER = LogUtils.getLogger();

    public CCARMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        CCARItems.REGISTER.register(modEventBus);
        CCARBlocks.REGISTER.register(modEventBus);
        CCARBlockEntities.REGISTER.register(modEventBus);
        CCARNetwork.register();
        ForgeComputerCraftAPI.registerPeripheralProvider(((level, blockPos, direction) -> {
            BlockEntity be = level.getBlockEntity(blockPos);
            if(be!=null && be instanceof IPeripheralHolder holder) return LazyOptional.of(()->holder.getPeripheral());
            return LazyOptional.empty();
        }));
        modEventBus.addListener(this::buildCreativeTabs);
        modEventBus.addListener(this::clientSetup);
        MinecraftForge.EVENT_BUS.addListener(ElementsServer::serverStop);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }
    public void clientSetup(FMLClientSetupEvent event){
        ARElements.register();
        MinecraftForge.EVENT_BUS.addListener(ARRenderer::renderLevel);
        MinecraftForge.EVENT_BUS.addListener(ARRenderer::logOut);
        MinecraftForge.EVENT_BUS.addListener(ARRenderer::clientTick);
    }
    private void buildCreativeTabs(BuildCreativeModeTabContentsEvent event){
        if(event.getTabKey()== ResourceKey.create(Registries.CREATIVE_MODE_TAB,new ResourceLocation("computercraft","tab"))){
            event.accept(CCARItems.AR_GOGGLES);
            event.accept(CCARItems.AR_CONTROLLER);
        }
    }
}
