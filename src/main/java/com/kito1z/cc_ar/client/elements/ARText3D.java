package com.kito1z.cc_ar.client.elements;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Matrix4f;
import org.joml.Vector3d;

public class ARText3D extends ARElement3D{

    private final double x, y, z;
    private final String text;
    private final float xRot, yRot, size;
    private final int color;
    private final boolean centered;

    public ARText3D(String id, int color, double x, double y, double z, float xRot, float yRot, float size , String text, boolean centered) {
        super(id);
        this.x = x;
        this.y = y;
        this.z = z;
        this.xRot = xRot;
        this.yRot = yRot;
        this.size = size;
        this.text = text;
        this.color = color;
        this.centered = centered;
    }

    public ARText3D(CompoundTag tag) {
        super(tag);
        x = tag.getDouble("x");
        y = tag.getDouble("y");
        z = tag.getDouble("z");
        xRot = tag.getFloat("xRot");
        yRot = tag.getFloat("yRot");
        size = tag.getFloat("size");
        text = tag.getString("text");
        color = tag.getInt("color");
        centered = tag.getBoolean("centered");
    }

    public CompoundTag serialize() {
        CompoundTag tag = super.serialize();
        tag.putDouble("x",x);
        tag.putDouble("y",y);
        tag.putDouble("z",z);
        tag.putFloat("xRot",xRot);
        tag.putFloat("yRot",yRot);
        tag.putFloat("size",size);
        tag.putString("text", text);
        tag.putInt("color",color);
        tag.putBoolean("centered",centered);
        return tag;
    }
    @Override
    @OnlyIn(Dist.CLIENT)
    protected void render(PoseStack stack, MultiBufferSource.BufferSource source, Camera camera, Matrix4f matrix) {
        var camPos = camera.getPosition();
        Vector3d delta = new Vector3d(x-camPos.x,y-camPos.y,z-camPos.z);
        stack.translate(delta.x,delta.y,delta.z);
        stack.mulPose(Axis.ZP.rotationDegrees(xRot+180));
        stack.mulPose(Axis.YP.rotationDegrees(yRot));
        stack.scale(size*0.03f,size*0.03f,size*0.03f);
        float halfWidth = 0f;
        if(centered) halfWidth = Minecraft.getInstance().font.width(text)/2f;
        float halfHeight = 0f;
        if(centered) halfHeight = Minecraft.getInstance().font.lineHeight/2f;
        Minecraft.getInstance().font.drawInBatch(text,-halfWidth,-halfHeight,color,false,stack.last().pose(),source, Font.DisplayMode.NORMAL,0, LightTexture.FULL_BRIGHT);
    }

    @Override
    public String getName() {
        return "text3d";
    }
}
