package com.kito1z.cc_ar.client.elements;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;
import org.joml.Matrix4f;
import org.joml.Vector3d;

public class ARItemGhost3D extends ARElement3D{

    private final double x, y, z;
    private ItemStack stack;
    private final float xRot, yRot, size;

    public ARItemGhost3D(String id, double x, double y, double z, float xRot, float yRot, float size , String item) {
        super(id);
        this.x = x;
        this.y = y;
        this.z = z;
        this.xRot = xRot;
        this.yRot = yRot;
        this.size = size;
        validateItem(item);
    }

    public ARItemGhost3D(CompoundTag tag) {
        super(tag);
        x = tag.getDouble("x");
        y = tag.getDouble("y");
        z = tag.getDouble("z");
        xRot = tag.getFloat("xRot");
        yRot = tag.getFloat("yRot");
        size = tag.getFloat("size");
        validateItem(tag.getString("item"));
    }

    private void validateItem(String id){
        if(id==null) return;
        Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(id));
        if(item!=null)stack = new ItemStack(item);
        else stack = null;
    }

    @Override
    public CompoundTag serialize() {
        CompoundTag tag = super.serialize();
        tag.putDouble("x",x);
        tag.putDouble("y",y);
        tag.putDouble("z",z);
        tag.putFloat("xRot",xRot);
        tag.putFloat("yRot",yRot);
        tag.putFloat("size",size);
        if(stack==null) return tag;
        ResourceLocation location = ForgeRegistries.ITEMS.getKey(stack.getItem());
        tag.putString("item",location.toString());
        return tag;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    protected void render(PoseStack stack, MultiBufferSource.BufferSource source, Camera camera, Matrix4f matrix) {
        var camPos = camera.getPosition();
        Vector3d delta = new Vector3d(x-camPos.x,y-camPos.y,z-camPos.z);
        stack.translate(delta.x,delta.y,delta.z);
        stack.mulPose(Axis.ZP.rotationDegrees(xRot));
        stack.mulPose(Axis.YP.rotationDegrees(yRot));
        stack.scale(size,size,size);
        Minecraft.getInstance().getItemRenderer().renderStatic(this.stack, ItemDisplayContext.FIXED, LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY,stack,source,Minecraft.getInstance().level,0);
    }

    @Override
    public String getName() {
        return "item3d";
    }
}
