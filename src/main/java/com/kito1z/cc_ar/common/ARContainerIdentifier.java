package com.kito1z.cc_ar.common;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

import java.util.Objects;

public class ARContainerIdentifier {
    private final BlockPos pos;
    private final Level level;

    public ARContainerIdentifier(BlockPos pos, Level level) {
        this.pos = pos;
        this.level = level;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof ARContainerIdentifier identifier){
            return Objects.equals(pos,identifier.pos) && Objects.equals(level,identifier.level);
        }
        return false;
    }

    public BlockPos getPos() {
        return pos;
    }

    public Level getLevel() {
        return level;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pos, level);
    }
}
