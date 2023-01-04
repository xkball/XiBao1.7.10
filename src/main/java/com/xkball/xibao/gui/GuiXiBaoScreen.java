package com.xkball.xibao.gui;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GuiXiBaoScreen extends GuiScreen {
    public void drawXiBao() {
        this.mc.getTextureManager().bindTexture(new ResourceLocation("xibao:textures/xibao.png"));
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glEnable(3042);
        GL11.glDisable(2896);
        GL11.glDisable(2912);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glBlendFunc(770, 771);

        Tessellator var9 = Tessellator.instance;
        var9.startDrawingQuads();
        // var9.setColorOpaque_I(4210752);
        var9.addVertexWithUV(0D, this.height, 0D, 0D, 1D);
        var9.addVertexWithUV(this.width, this.height, 0D, 1D, 1D);
        var9.addVertexWithUV(this.width, 0D, 0D, 1D, 0D);
        var9.addVertexWithUV(0D, 0D, 0D, 0D, 0D);
        var9.draw();
    }
}
