package com.kito1z.cc_ar.client.elements;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.gui.overlay.ForgeGui;

public abstract class ARElement2D extends ARElement {
    public ARElement2D(String id) {
        super(id);
    }

    public ARElement2D(CompoundTag tag) {
        super(tag);
    }

    @OnlyIn(Dist.CLIENT)
    protected abstract void render (ForgeGui gui, GuiGraphics guiGraphics, float partialTick, int screenWidth, int screenHeight);
}
