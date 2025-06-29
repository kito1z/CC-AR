package com.kito1z.cc_ar.registry;

import dan200.computercraft.api.peripheral.IPeripheral;
import org.jetbrains.annotations.NotNull;

public interface IPeripheralHolder {
    @NotNull
    IPeripheral getPeripheral();
}
