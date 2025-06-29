package com.kito1z.cc_ar.client.elements;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.gui.overlay.ForgeGui;

public class ArLine2D extends ARElement2D{



    public enum LineType{
        HORIZONTAL, VERTICAL
    }

    private final LineType type;

    private final int x;
    private final int y;
    private final int l;
    private final int color;

    public ArLine2D(String id, int color, LineType type, int x, int y, int l) {
        super(id);
        this.color = color;
        this.type = type;
        this.x = x;
        this.y = y;
        this.l = l;
    }

    @Override
    public CompoundTag serialize() {
        CompoundTag tag = super.serialize();
        tag.putString("type",type.name());
        tag.putInt("color",color);
        tag.putInt("x",x);
        tag.putInt("y",y);
        tag.putInt("l",l);
        return tag;
    }

    public ArLine2D(CompoundTag tag) {
        super(tag);
        type =LineType.valueOf(tag.getString("type"));
        color = tag.getInt("color");
        x = tag.getInt("x");
        y = tag.getInt("y");
        l = tag.getInt("l");
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    protected void render(ForgeGui gui, GuiGraphics guiGraphics, float partialTick, int screenWidth, int screenHeight) {


        switch (type){
            case VERTICAL -> guiGraphics.vLine(x,y,l, color);
            case HORIZONTAL -> guiGraphics.hLine(x,l,y, color);
        }
    }

    @Override
    public String getName() {
        return "line2d";
    }
}
