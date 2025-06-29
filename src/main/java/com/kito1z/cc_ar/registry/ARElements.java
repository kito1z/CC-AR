package com.kito1z.cc_ar.registry;

import com.kito1z.cc_ar.client.elements.*;
import com.kito1z.cc_ar.common.ARElementsReg;

public class ARElements {
    public static void register(){
        ARElementsReg.register("line3d", ARLine3D::new);
        ARElementsReg.register("line2d", ArLine2D::new);
        ARElementsReg.register("item2d", ARItemIcon2D::new);
        ARElementsReg.register("item3d", ARItemGhost3D::new);
        ARElementsReg.register("text2d", ARText2D::new);
        ARElementsReg.register("text3d",ARText3D::new);
        ARElementsReg.register("rect2d",ARRect2D::new);
    }
}
