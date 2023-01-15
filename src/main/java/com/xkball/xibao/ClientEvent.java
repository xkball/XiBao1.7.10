package com.xkball.xibao;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = "xibao")
public class ClientEvent {
    @SubscribeEvent
    public static void onDrawnBackground(GuiScreenEvent.BackgroundDrawnEvent event){
        //XiBao.log("test");
        GuiScreen gui = event.getGui();
        if(ClientProxy.supportedGui.contains(gui.getClass().getName())){
            drawXiBao(gui);
        }
    }
    
    @SubscribeEvent
    public static void onInitGui(GuiScreenEvent.InitGuiEvent.Post event){
        GuiScreen gui = event.getGui();
        if(ClientProxy.supportedGui.contains(gui.getClass().getName())){
            event.getButtonList().add(new GuiButton(
                    event.getButtonList().size(),
                    gui.width / 2 - 50 - 15,
                    // 原为12
                    gui.height / 4 + 120 + 25 + 5,
                            //+ 25,
                    140,
                    20,
                    I18n.format("xibao.gui.common.exchangexibaoandbeibao")));
        }
    }
    
    @SubscribeEvent
    public static void onClickButton(GuiScreenEvent.ActionPerformedEvent.Pre event){
        GuiScreen gui = event.getGui();
        if(ClientProxy.supportedGui.contains(gui.getClass().getName()) && event.getButton().id == event.getButtonList().size()-1){
            XiBaoConfig.showXiBaoButNotBeiBao = !XiBaoConfig.showXiBaoButNotBeiBao;
            ConfigManager.sync("xibao", Config.Type.INSTANCE);
        }
    }
    
    public static void drawXiBao(GuiScreen gui){
        GlStateManager.disableLighting();
        GlStateManager.disableFog();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        gui.mc.getTextureManager().bindTexture(XiBaoConfig.showXiBaoButNotBeiBao ? ClientProxy.xibao : ClientProxy.beibao);
        //gui.drawTexturedModalRect(0,0,gui.width,gui.height, gui.width, gui.height);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        //复制自喜报-1.7.10
        //var9.startDrawingQuads();
        // var9.setColorOpaque_I(4210752);
        bufferbuilder.pos(0D, gui.height, 0D).tex(0D, 1D).color(255,255,255,255).endVertex();
        bufferbuilder.pos(gui.width, gui.height,0D).tex(1D, 1D).color(255,255,255,255).endVertex();
        bufferbuilder.pos(gui.width, 0D, 0D).tex(1D, 0D).color(255,255,255,255).endVertex();
        bufferbuilder.pos(0D, 0D, 0D).tex( 0D, 0D).color(255,255,255,255).endVertex();
        tessellator.draw();
    }
}
