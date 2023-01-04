package com.xkball.xibao;

import com.xkball.xibao.gui.GuiXiBaoCrashScreen;
import com.xkball.xibao.gui.GuiXiBaoDisconnectedScreen;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.*;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import java.util.ArrayList;
import net.minecraft.client.gui.GuiDisconnected;
import net.minecraftforge.client.event.GuiOpenEvent;

public class ClientProxy extends CommonProxy {

    public static ArrayList<String> supportedScreen = new ArrayList<>();
    public static String oldScreen = null;

    // preInit "Run before anything else. Read your config, create blocks, items,
    // etc, and register them with the GameRegistry."
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
        net.minecraftforge.common.MinecraftForge.EVENT_BUS.register(this);
        FMLCommonHandler.instance().bus().register(this);
        supportedScreen.add("GuiDisconnected");
        supportedScreen.add("GuiCrashScreen");
    }

    @SubscribeEvent
    public void on(GuiOpenEvent event) {
        if (event.gui instanceof GuiDisconnected) {
            event.gui = new GuiXiBaoDisconnectedScreen((GuiDisconnected) event.gui);
        } else if (event.gui != null
                && "vfyjxf.bettercrashes.utils.GuiCrashScreen"
                        .equals(event.gui.getClass().getName())) {
            event.gui = new GuiXiBaoCrashScreen(event.gui);
        }
    }

    //    @SubscribeEvent
    //    public void onGuiRender(GuiScreenEvent.DrawScreenEvent.Post event) {
    //        if (event.gui != null) {
    //            String name = event.gui.getClass().getName();
    //            if (!name.equals(oldScreen)) {
    //                MyMod.info(name);
    //                oldScreen = name;
    //            }
    //
    //            if (event.gui instanceof GuiDisconnected
    //                    || "vfyjxf.bettercrashes.utils.GuiCrashScreen"
    //                            .equals(event.gui.getClass().getName())) {
    //                GuiScreen gui = event.gui;
    //                gui.mc.getTextureManager().bindTexture(new ResourceLocation("xibao:textures/xibao.png"));
    //                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    //                GL11.glEnable(3042);
    //                GL11.glDisable(2896);
    //                GL11.glDisable(2912);
    //                OpenGlHelper.glBlendFunc(770, 771, 1, 0);
    //                GL11.glBlendFunc(770, 771);
    //
    //                Tessellator var9 = Tessellator.instance;
    //                var9.startDrawingQuads();
    //                // var9.setColorOpaque_I(4210752);
    //                var9.addVertexWithUV(0D, gui.height, 0D, 0D, 1D);
    //                var9.addVertexWithUV(gui.width, gui.height, 0D, 1D, 1D);
    //                var9.addVertexWithUV(gui.width, 0D, 0D, 1D, 0D);
    //                var9.addVertexWithUV(0D, 0D, 0D, 0D, 0D);
    //                var9.draw();
    //            }
    //        }
    //    }

    // load "Do your mod setup. Build whatever data structures you care about. Register recipes."
    public void init(FMLInitializationEvent event) {
        super.init(event);
    }

    // postInit "Handle interaction with other mods, complete your setup based on this."
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
    }

    public void serverAboutToStart(FMLServerAboutToStartEvent event) {
        super.serverAboutToStart(event);
    }

    // register server commands in this event handler
    public void serverStarting(FMLServerStartingEvent event) {
        super.serverStarting(event);
    }

    public void serverStarted(FMLServerStartedEvent event) {
        super.serverStarted(event);
    }

    public void serverStopping(FMLServerStoppingEvent event) {
        super.serverStopping(event);
    }

    public void serverStopped(FMLServerStoppedEvent event) {
        super.serverStopped(event);
    }
}
