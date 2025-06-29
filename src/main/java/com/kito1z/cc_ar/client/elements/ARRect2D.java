package com.kito1z.cc_ar.client.elements;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.gui.overlay.ForgeGui;

public class ARRect2D extends ARElement2D{

    private final int x1,y1,x2,y2,color;

    public ARRect2D(String id, int color, int x1, int y1, int x2, int y2) {
        super(id);
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.color = color;
    }

    public ARRect2D(CompoundTag tag) {
        super(tag);
        x1 = tag.getInt("x1");
        y1 = tag.getInt("y1");
        x2 = tag.getInt("x2");
        y2 = tag.getInt("y2");
        color = tag.getInt("color");
    }

    @Override
    public CompoundTag serialize() {
        CompoundTag tag = super.serialize();
        tag.putInt("x1",x1);
        tag.putInt("y1",y1);
        tag.putInt("x2",x2);
        tag.putInt("y2",y2);
        tag.putInt("color",color);
        return tag;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    protected void render(ForgeGui gui, GuiGraphics guiGraphics, float partialTick, int screenWidth, int screenHeight) {
        guiGraphics.fill(x1,y1,x2,y2,color);
    }

    @Override
    public String getName() {
        return "rect2d";
    }
}
