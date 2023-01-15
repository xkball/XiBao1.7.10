package com.xkball.xibao;

import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

import java.util.ArrayList;

public class ClientProxy extends CommonProxy {
    public static ArrayList<String> supportedGui = new ArrayList<>();
    public static ResourceLocation xibao = new ResourceLocation("xibao:textures/xibao.png");
    public static ResourceLocation beibao = new ResourceLocation("xibao:textures/beibao.png");
    public void init(FMLInitializationEvent event){
        supportedGui.add(GuiDisconnected.class.getName());
        supportedGui.add("org.dimdev.vanillafix.crashes.GuiCrashScreen");
        supportedGui.add("zone.rong.loliasm.common.crashes.GuiCrashScreen");
    }
}
