package com.kito1z.cc_ar.common;

import net.minecraftforge.event.server.ServerStoppingEvent;

import java.util.HashMap;
import java.util.Map;

public class ElementsServer {
    public static final Map<ARContainerIdentifier,ElementsContainer> ELEMENTS = new HashMap<>();

    public static void serverStop(ServerStoppingEvent event){
        ELEMENTS.clear();
    }
}
