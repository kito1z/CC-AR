package com.kito1z.cc_ar.registry;

import com.kito1z.cc_ar.CCARMod;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.Tags;

public class ARGogglesArmorMaterial implements ArmorMaterial {
    @Override
    public int getDurabilityForType(ArmorItem.Type p_266807_) {
        return 100;
    }

    @Override
    public int getDefenseForType(ArmorItem.Type p_267168_) {
        return 1;
    }

    @Override
    public int getEnchantmentValue() {
        return 0;
    }

    @Override
    public SoundEvent getEquipSound() {
        return SoundEvents.ARMOR_EQUIP_GENERIC;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.of(Tags.Items.GLASS);
    }

    @Override
    public String getName() {
        return CCARMod.MODID+":ar_goggles";
    }

    @Override
    public float getToughness() {
        return 0;
    }

    @Override
    public float getKnockbackResistance() {
        return 0;
    }
}
