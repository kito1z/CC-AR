package com.kito1z.cc_ar.blocks.blockentities;

import com.kito1z.cc_ar.client.elements.*;
import com.kito1z.cc_ar.common.ARContainerIdentifier;
import com.kito1z.cc_ar.common.ElementsContainer;
import com.kito1z.cc_ar.common.ElementsServer;
import com.kito1z.cc_ar.registry.CCARBlockEntities;
import com.kito1z.cc_ar.registry.IPeripheralHolder;
import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.lua.LuaTable;
import dan200.computercraft.api.lua.ObjectLuaTable;
import dan200.computercraft.api.peripheral.IPeripheral;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class ARController extends BlockEntity implements IPeripheralHolder {
    public ARController(BlockPos pos, BlockState state) {
        super(CCARBlockEntities.AR_CONTROLLER.get(), pos, state);
    }

    public final ElementsContainer arContainer = new ElementsContainer();


    @Override
    public @NotNull IPeripheral getPeripheral() {
        return new ARControllerPeripheral(this);
    }

    public static class ARControllerPeripheral implements IPeripheral{
        private final ARController instance;

        @NotNull
        private ElementsContainer validateContainer(){
            return instance.arContainer;
        }

        protected ARControllerPeripheral(ARController instance) {
            this.instance = instance;
        }

        @Override
        public String getType() {
            return "ar_controller";
        }

        @Override
        public boolean equals(@Nullable IPeripheral iPeripheral) {
            return iPeripheral instanceof ARControllerPeripheral controllerPeripheral && controllerPeripheral.instance == instance;
        }

        @LuaFunction
        public void drawLine3D(String id, int color ,double ax, double ay, double az, double bx, double by, double bz){
            validateContainer().put(new ARLine3D(id, new Vector3f((float) ax,(float) ay,(float) az),new Vector3f((float)bx, (float)by, (float)bz),color));
        }

        @LuaFunction
        public void drawHorizontalLine2D(String id, int color, int x, int y, int targetX){
            validateContainer().put(new ArLine2D(id,color, ArLine2D.LineType.HORIZONTAL,x,y,targetX));
        }
        @LuaFunction
        public void drawVerticalLine2D(String id, int color, int x, int y, int targetY){
            validateContainer().put(new ArLine2D(id,color, ArLine2D.LineType.VERTICAL,x,y,targetY));
        }

        @LuaFunction
        public void drawItemIcon2D(String id, int x, int y , String item){
            validateContainer().put(new ARItemIcon2D(id, x, y, item));
        }

        @LuaFunction
        public void drawItemIcon3D(String id, double x, double y, double z, double xRot, double yRot, double size ,String item){
            validateContainer().put(new ARItemGhost3D(id,x,y,z,(float) xRot,(float) yRot,(float) size,item));
        }

        @LuaFunction
        public void drawText2D(String id, int color, int x, int y, String text, boolean centered){
            validateContainer().put(new ARText2D(id,x,y,color,text,centered));
        }
        @LuaFunction
        public void drawText3D(String id, int color, double x, double y, double z, double xRot, double yRot, double size ,String text,boolean centered){
            validateContainer().put(new ARText3D(id,color,x,y,z,(float)xRot,(float)yRot,(float) size,text,centered ));
        }
        @LuaFunction
        public void drawRect2D(String id, int color, int x1, int y1, int x2, int y2){
            validateContainer().put(new ARRect2D(id, color, x1,y1,x2,y2));
        }

        @LuaFunction
        public void delete(String id){
            validateContainer().remove(id);
        }

        @LuaFunction
        public void clear(){
            validateContainer().clear();
        }

        @LuaFunction
        public void setLabel(String label){
            validateContainer().label = label;
        }

        @LuaFunction
        public LuaTable<?,?> getAllElements(){
            Map<String, String> map = new HashMap<>();
            validateContainer().forEach((id, element)->map.put(element.getId(),element.getName()));
            return new ObjectLuaTable(map);
        }

        @LuaFunction
        public void update(){
            ARContainerIdentifier identifier = new ARContainerIdentifier(instance.getBlockPos(), instance.getLevel());
            ElementsContainer container = (ElementsContainer) instance.arContainer.clone();
            container.setVersion(System.currentTimeMillis());
            ElementsServer.ELEMENTS.put(identifier,container);
        }

    }
}
