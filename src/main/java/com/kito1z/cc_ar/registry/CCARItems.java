package com.kito1z.cc_ar.registry;

import com.kito1z.cc_ar.CCARMod;
import com.kito1z.cc_ar.items.ARGoggles;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class CCARItems {
    public static final DeferredRegister<Item> REGISTER = DeferredRegister.create(ForgeRegistries.ITEMS, CCARMod.MODID);

    public static final RegistryObject<ARGoggles> AR_GOGGLES = REGISTER.register("ar_goggles",()-> new ARGoggles(new ARGogglesArmorMaterial(), ArmorItem.Type.HELMET,new Item.Properties().stacksTo(1)));
    public static final RegistryObject<BlockItem> AR_CONTROLLER = REGISTER.register("ar_controller",()-> new BlockItem(CCARBlocks.AR_CONTROLLER.get(),new Item.Properties()));
}
