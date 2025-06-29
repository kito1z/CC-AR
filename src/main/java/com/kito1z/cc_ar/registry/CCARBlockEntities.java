package com.kito1z.cc_ar.registry;

import com.kito1z.cc_ar.CCARMod;
import com.kito1z.cc_ar.blocks.blockentities.ARController;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class CCARBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> REGISTER = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, CCARMod.MODID);

    public static final RegistryObject<BlockEntityType<ARController>> AR_CONTROLLER = REGISTER.register("ar_controller",()->
            BlockEntityType.Builder.of(ARController::new,CCARBlocks.AR_CONTROLLER.get()).build(null));
}
