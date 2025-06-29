package com.kito1z.cc_ar.common;

import com.kito1z.cc_ar.client.elements.ARElement;
import net.minecraft.nbt.CompoundTag;

public interface IARElementReg {
    ARElement create(CompoundTag tag);
}
