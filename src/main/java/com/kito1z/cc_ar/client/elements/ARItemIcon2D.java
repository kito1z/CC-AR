package com.kito1z.cc_ar.client.elements;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.registries.ForgeRegistries;

public class ARItemIcon2D extends ARElement2D{

    private final int x, y;
    private ItemStack stack;


    public ARItemIcon2D(String id, int x, int y, String item) {
        super(id);
        this.x = x;
        this.y = y;
        validateItem(item);
    }

    private void validateItem(String id){
        if(id==null) return;
        Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(id));
        if(item!=null)stack = new ItemStack(item);
        else stack = null;
    }

    public ARItemIcon2D(CompoundTag tag) {
        super(tag);
        x = tag.getInt("x");
        y = tag.getInt("y");
        validateItem(tag.getString("item"));
    }

    @Override
    public CompoundTag serialize() {
        CompoundTag tag = super.serialize();
        tag.putInt("x",x);
        tag.putInt("y",y);
        if(stack==null) return tag;
        ResourceLocation location = ForgeRegistries.ITEMS.getKey(stack.getItem());
        tag.putString("item",location.toString());
        return tag;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    protected void render(ForgeGui gui, GuiGraphics guiGraphics, float partialTick, int screenWidth, int screenHeight) {
        if(stack==null) return;
        guiGraphics.renderFakeItem(stack, x, y);
    }

    @Override
    public String getName() {
        return "item2d";
    }
}
