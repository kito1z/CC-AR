package com.kito1z.cc_ar.client.elements;

import com.kito1z.cc_ar.common.ElementsContainer;
import com.kito1z.cc_ar.registry.CCARItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD,value = Dist.CLIENT)
public class ARRenderer {
    public static final ElementsContainer elements = new ElementsContainer();
    public static BlockPos lastPos = null;

    private static boolean isArAvailable = false;

    public static void clientTick(TickEvent.ClientTickEvent event){
        if(event.phase!= TickEvent.Phase.END) return;
        isArAvailable = isArAvailable();
    }

    private static boolean isArAvailable(){
        Player player = Minecraft.getInstance().player;
        if(player==null) return false;
        for (ItemStack stack : player.getArmorSlots()){
            if(stack.getItem()== CCARItems.AR_GOGGLES.get()) return true;
        }
        return false;
    }

    public static final IGuiOverlay AR_OVERLAY = (gui, guiGraphics, partialTick, screenWidth, screenHeight) -> {
        elements.forEach((id, e)->{
            if(!isArAvailable) return;
            if (e instanceof ARElement2D element2D) element2D.render(gui, guiGraphics, partialTick, screenWidth, screenHeight);
        });
    };

    public static void renderLevel(RenderLevelStageEvent event){
        if(!isArAvailable) return;
        if (!(event.getStage()== RenderLevelStageEvent.Stage.AFTER_TRANSLUCENT_BLOCKS)) return;
        MultiBufferSource.BufferSource source = Minecraft.getInstance().renderBuffers().bufferSource();
        elements.forEach((id,e)->{
            if(e instanceof ARElement3D element3D) {
                event.getPoseStack().pushPose();
                element3D.render(event.getPoseStack(), source, event.getCamera(), event.getProjectionMatrix());
                event.getPoseStack().popPose();
            }
        });
    }
    public static void logOut(ClientPlayerNetworkEvent.LoggingOut event){
        elements.clear();
    }
    @SubscribeEvent
    public static void registerGUI(RegisterGuiOverlaysEvent event){
        event.registerBelowAll("ar_overlay",AR_OVERLAY);
    }
}
