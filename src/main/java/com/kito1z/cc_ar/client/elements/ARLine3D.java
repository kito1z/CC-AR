package com.kito1z.cc_ar.client.elements;


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Matrix4f;
import org.joml.Vector3d;
import org.joml.Vector3f;

public class ARLine3D extends ARElement3D{

    final Vector3f start;
    final int color;
    final Vector3f end;

    public ARLine3D(String id, Vector3f start, Vector3f end, int color) {
        super(id);
        this.start = start;
        this.end = end;
        this.color=color;
    }

    public ARLine3D(CompoundTag tag){
        super(tag);

        start =new Vector3f(tag.getFloat("sx"),tag.getFloat("sy"),tag.getFloat("sz"));
        end =new Vector3f(tag.getFloat("ex"),tag.getFloat("ey"),tag.getFloat("ez"));
        color = tag.getInt("color");
    }

    @Override
    public CompoundTag serialize() {
        CompoundTag tag = super.serialize();
        tag.putInt("color",color);
        tag.putFloat("sx",start.x);
        tag.putFloat("sy",start.y);
        tag.putFloat("sz",start.z);
        tag.putFloat("ex",end.x);
        tag.putFloat("ey",end.y);
        tag.putFloat("ez",end.z);
        return tag;
    }

    @Override
    public String getName() {
        return "line3d";
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    protected void render(PoseStack stack, MultiBufferSource.BufferSource source, Camera camera, Matrix4f matrix) {
        VertexConsumer buffer = source.getBuffer(RenderType.LINES);
        var camPos = camera.getPosition();
        Vector3d delta = new Vector3d(start.x-camPos.x,start.y-camPos.y,start.z-camPos.z);
        stack.translate(delta.x,delta.y,delta.z);
        buffer.vertex(stack.last().pose(), start.x, start.y, start.z).color(color).normal(stack.last().normal(),1f,0f,0f).endVertex();
        buffer.vertex(stack.last().pose(),end.x, end.y, end.z).color(color).normal(stack.last().normal(),1f,0f,0f).endVertex();
        buffer.vertex(stack.last().pose(), start.x, start.y, start.z).color(color).normal(stack.last().normal(),0f,0f,-1f).endVertex();
        buffer.vertex(stack.last().pose(),end.x, end.y, end.z).color(color).normal(stack.last().normal(),0f,0f,-1f).endVertex();
    }
}
