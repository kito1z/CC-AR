package com.kito1z.cc_ar.client.elements;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Matrix4f;

public abstract class ARElement3D extends ARElement{
    public ARElement3D(String id) {
        super(id);
    }

    public ARElement3D(CompoundTag tag) {
        super(tag);
    }
    @OnlyIn(Dist.CLIENT)
    protected abstract void render(PoseStack stack, MultiBufferSource.BufferSource source, Camera camera, Matrix4f matrix);
}
