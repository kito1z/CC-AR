package com.kito1z.cc_ar.registry;

import com.kito1z.cc_ar.CCARMod;
import com.kito1z.cc_ar.blocks.ARControllerBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class CCARBlocks {
    public static final DeferredRegister<Block> REGISTER = DeferredRegister.create(ForgeRegistries.BLOCKS, CCARMod.MODID);

    public static final RegistryObject<ARControllerBlock> AR_CONTROLLER = REGISTER.register("ar_controller",()->new ARControllerBlock(BlockBehaviour.Properties.copy(Blocks.STONE)));
}
