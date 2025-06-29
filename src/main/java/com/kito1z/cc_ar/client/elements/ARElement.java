package com.kito1z.cc_ar.client.elements;

import net.minecraft.nbt.CompoundTag;

public abstract class ARElement {
    private final String id;

    public ARElement(String id){
        this.id = id;
    }

    public ARElement(CompoundTag tag){
        id = tag.getString("id");
    }

    public abstract String getName();

    public String getId() {
        return id;
    }

    public CompoundTag serialize(){
        CompoundTag tag = new CompoundTag();
        tag.putString("id", id);
        tag.putString("name",getName());
        return tag;
    }
}
