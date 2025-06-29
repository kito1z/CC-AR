package com.kito1z.cc_ar.items;

import com.kito1z.cc_ar.client.elements.ARRenderer;
import com.kito1z.cc_ar.common.ARContainerIdentifier;
import com.kito1z.cc_ar.common.ElementsContainer;
import com.kito1z.cc_ar.common.ElementsServer;
import com.kito1z.cc_ar.network.CCARNetwork;
import com.kito1z.cc_ar.network.RequestUpdatePacket;
import com.kito1z.cc_ar.registry.CCARBlocks;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class ARGoggles extends ArmorItem {

    public ARGoggles(ArmorMaterial p_40386_, Type p_266831_, Properties p_40388_) {
        super(p_40386_, p_266831_, p_40388_);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        super.appendHoverText(stack, level, components, flag);
        String label = stack.getOrCreateTag().getString("label");
        if(!label.isEmpty()) components.add(Component.literal(label).withStyle(ChatFormatting.GOLD));
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        InteractionResult result = InteractionResult.PASS;
        if(context.getLevel().getBlockState(context.getClickedPos()).getBlock() == CCARBlocks.AR_CONTROLLER.get()){
            result = InteractionResult.SUCCESS;
            if(context.getLevel().isClientSide) return result;
            ItemStack stack = context.getPlayer().getItemInHand(context.getHand());
            CompoundTag tag = stack.getOrCreateTag();
            tag.putDouble("x", context.getClickedPos().getX());
            tag.putDouble("y", context.getClickedPos().getY());
            tag.putDouble("z", context.getClickedPos().getZ());
            tag.putLong("version",-1L);
            ARContainerIdentifier identifier = new ARContainerIdentifier(context.getClickedPos(),context.getLevel());
            if(ElementsServer.ELEMENTS.containsKey(identifier)){
                tag.putString("label",ElementsServer.ELEMENTS.get(identifier).label);
            }else tag.putString("label", "AR Controller");
            stack.setTag(tag);
        }
        return result;
    }

    public void forceUpdate(ItemStack stack, ServerPlayer player){
        if(!stack.hasTag()) return;
        CompoundTag tag = stack.getTag();
        BlockPos pos = new BlockPos((int) tag.getDouble("x"),(int) tag.getDouble("y"),(int) tag.getDouble("z"));
        ElementsContainer container = ElementsServer.ELEMENTS.get(new ARContainerIdentifier(pos,player.level()));
        if(container!=null){
            tag.putLong("version",container.getVersion());
            tag.putString("label", container.label);
            CCARNetwork.sendToPlayer(container,player);
        }
        stack.setTag(tag);
    }

    @Override
    public void onArmorTick(ItemStack stack, Level level, Player player) {
        if(!stack.hasTag()) return;
        CompoundTag tag = stack.getTag();
        BlockPos pos = new BlockPos((int) tag.getDouble("x"),(int) tag.getDouble("y"),(int) tag.getDouble("z"));
        if(level.isClientSide) {
            if(!Objects.equals(ARRenderer.lastPos, pos)) {
                ARRenderer.elements.clear();
                ARRenderer.lastPos = pos;
                CCARNetwork.sendToServer(new RequestUpdatePacket());
            }
            return;
        }
        ServerPlayer serverPlayer = (ServerPlayer) player;
        if(!tag.contains("version")) return;
        long version = tag.getLong("version");
        ElementsContainer container = ElementsServer.ELEMENTS.get(new ARContainerIdentifier(pos,player.level()));
        if(container!=null && container.getVersion()>version){
            tag.putLong("version",container.getVersion());
            tag.putString("label", container.label);
            CCARNetwork.sendToPlayer(container,serverPlayer);
        }
    }
}
