package com.kito1z.cc_ar.client.elements;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.gui.overlay.ForgeGui;

public class ARText2D extends ARElement2D{

    private final int x, y, color;
    private final String text;
    private final boolean centered;

    public ARText2D(String id, int x, int y, int color, String text, boolean centered) {
        super(id);
        this.x = x;
        this.y = y;
        this.color = color;
        this.text = text;
        this.centered = centered;
    }

    public ARText2D(CompoundTag tag) {
        super(tag);
        this.x = tag.getInt("x");
        this.y = tag.getInt("y");
        this.color = tag.getInt("color");
        this.text = tag.getString("text");
        this.centered = tag.getBoolean("centered");
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    protected void render(ForgeGui gui, GuiGraphics guiGraphics, float partialTick, int screenWidth, int screenHeight) {
        if(centered) guiGraphics.drawCenteredString(Minecraft.getInstance().font,text,x,y-Minecraft.getInstance().font.lineHeight/2,color);
        else guiGraphics.drawString(Minecraft.getInstance().font,text,x,y,color);
    }

    @Override
    public CompoundTag serialize() {
        CompoundTag tag = super.serialize();
        tag.putInt("x",x);
        tag.putInt("y",y);
        tag.putInt("color",color);
        tag.putString("text",text);
        tag.putBoolean("centered",centered);
        return tag;
    }

    @Override
    public String getName() {
        return "text2d";
    }
}
