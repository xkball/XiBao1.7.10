package com.xkball.xibao.gui;

import cpw.mods.fml.relauncher.ReflectionHelper;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.IChatComponent;

public class GuiXiBaoDisconnectedScreen extends GuiXiBaoScreen {
    private String field_146306_a;
    private IChatComponent field_146304_f;
    private List field_146305_g;
    private final GuiScreen field_146307_h;

    public GuiXiBaoDisconnectedScreen(GuiScreen p_i45020_1_, String p_i45020_2_, IChatComponent p_i45020_3_) {
        this.field_146307_h = p_i45020_1_;
        this.field_146306_a = I18n.format(p_i45020_2_, new Object[0]);
        this.field_146304_f = p_i45020_3_;
    }

    public GuiXiBaoDisconnectedScreen(GuiDisconnected guiDisconnected) {
        this(
                ReflectionHelper.getPrivateValue(GuiDisconnected.class, guiDisconnected, "field_146307_h"),
                ReflectionHelper.getPrivateValue(GuiDisconnected.class, guiDisconnected, "field_146306_a"),
                ReflectionHelper.getPrivateValue(GuiDisconnected.class, guiDisconnected, "field_146304_f"));
    }

    protected void keyTyped(char p_keyTyped_1_, int p_keyTyped_2_) {}

    public void initGui() {
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(
                0, this.width / 2 - 100, this.height / 4 + 120 + 12, I18n.format("gui.toMenu", new Object[0])));
        this.field_146305_g = this.fontRendererObj.listFormattedStringToWidth(
                this.field_146304_f.getFormattedText(), this.width - 50);
    }

    protected void actionPerformed(GuiButton p_actionPerformed_1_) {
        if (p_actionPerformed_1_.id == 0) {
            this.mc.displayGuiScreen(this.field_146307_h);
        }
    }

    public void drawScreen(int p_drawScreen_1_, int p_drawScreen_2_, float p_drawScreen_3_) {
        this.drawXiBao();
        this.drawCenteredString(
                this.fontRendererObj, this.field_146306_a, this.width / 2, this.height / 2 - 50, 11184810);
        int var4 = this.height / 2 - 30;
        if (this.field_146305_g != null) {
            for (Iterator var5 = this.field_146305_g.iterator();
                    var5.hasNext();
                    var4 += this.fontRendererObj.FONT_HEIGHT) {
                String var6 = (String) var5.next();
                this.drawCenteredString(this.fontRendererObj, var6, this.width / 2, var4, 16777215);
            }
        }
        super.drawScreen(p_drawScreen_1_, p_drawScreen_2_, p_drawScreen_3_);
        // this.drawXiBao();
    }
}
