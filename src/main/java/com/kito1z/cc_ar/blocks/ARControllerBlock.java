package com.kito1z.cc_ar.blocks;

import com.kito1z.cc_ar.registry.CCARBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class ARControllerBlock extends Block implements EntityBlock {
    public ARControllerBlock(Properties p_49795_) {
        super(p_49795_);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return CCARBlockEntities.AR_CONTROLLER.get().create(pos, state);
    }
}
