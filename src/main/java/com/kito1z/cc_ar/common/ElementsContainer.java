package com.kito1z.cc_ar.common;

import com.kito1z.cc_ar.client.elements.ARElement;
import net.minecraft.network.FriendlyByteBuf;

import java.util.HashMap;

public class ElementsContainer extends HashMap<String, ARElement> {
    private long version = 0;

    public String label = "";

    public long getVersion() {
        return version;
    }
    public void setVersion(long value){
        version= value;
    }
    public void writeToBuf(FriendlyByteBuf buf){
        buf.writeInt(size());
        forEach((id,e)->{
            buf.writeNbt(e.serialize());
        });
    }

    public void readFromBuf(FriendlyByteBuf buf){
        clear();
        int size = buf.readInt();
        for (int i = 0; i<size;i++){
            put(ARElementsReg.create(buf.readNbt()));
        }
    }

    public ARElement put(ARElement value) {
        return super.put(value.getId(), value);
    }



    @Override
    public void clear() {
        super.clear();
        version = 0;
    }
}
