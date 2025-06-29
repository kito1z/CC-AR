package com.kito1z.cc_ar.common;

import com.kito1z.cc_ar.client.elements.ARElement;
import net.minecraft.nbt.CompoundTag;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class ARElementsReg {
    private static final Map<String,IARElementReg> REGISTRY = new HashMap<>();

    public static void register(String id, IARElementReg constructor){
        REGISTRY.put(id, constructor);
    }

    @Nullable
    public static ARElement create(String id, CompoundTag tag){
        if(!REGISTRY.containsKey(id)) return null;
        return REGISTRY.get(id).create(tag);
    }
    @Nullable
    public static ARElement create(CompoundTag tag){
        return create(tag.getString("name"),tag);
    }
}
