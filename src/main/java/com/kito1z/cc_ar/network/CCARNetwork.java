package com.kito1z.cc_ar.network;

import com.kito1z.cc_ar.CCARMod;
import com.kito1z.cc_ar.client.elements.ARRenderer;
import com.kito1z.cc_ar.common.ElementsContainer;
import com.kito1z.cc_ar.items.ARGoggles;
import com.kito1z.cc_ar.registry.CCARItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class CCARNetwork {
    private static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(new ResourceLocation(CCARMod.MODID,"main"),
            ()->"1",
            (v)->true,
            (v)->true);

    public static void register(){
        CHANNEL.messageBuilder(ElementsContainer.class,0, NetworkDirection.PLAY_TO_CLIENT)
                .encoder(ElementsContainer::writeToBuf)
                .decoder(buf->{
                    ARRenderer.elements.readFromBuf(buf);
                    return ARRenderer.elements;
                })
                .consumerMainThread((container,contextSupplier)->{})
                .add();
        CHANNEL.messageBuilder(RequestUpdatePacket.class,1,NetworkDirection.PLAY_TO_SERVER)
                .encoder((packet,buf)->{})
                .decoder((b)->new RequestUpdatePacket())
                .consumerMainThread((b,c)->{
                    ServerPlayer player = c.get().getSender();
                    if(player==null) return;
                    for (ItemStack stack : player.getArmorSlots()){
                        if(stack.getItem()== CCARItems.AR_GOGGLES.get()){
                            ((ARGoggles)stack.getItem()).forceUpdate(stack,player);
                            return;
                        }
                    }
                })
                .add();
    }

    public static void sendToAll(ElementsContainer msg) {
        CHANNEL.send(PacketDistributor.ALL.noArg(), msg);
    }
    public static void sendToPlayer(ElementsContainer msg, ServerPlayer player) {
        CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), msg);
    }
    public static void sendToServer(Object msg){
        CHANNEL.sendToServer(msg);
    }
}
