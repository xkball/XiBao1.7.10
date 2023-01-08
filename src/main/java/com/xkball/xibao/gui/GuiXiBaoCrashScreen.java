package com.xkball.xibao.gui;

import com.xkball.xibao.ColorUtil;
import com.xkball.xibao.Config;
import com.xkball.xibao.XiBao;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.ModContainer;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptionButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.crash.CrashReport;
import org.apache.commons.lang3.StringUtils;

public class GuiXiBaoCrashScreen extends GuiXiBaoScreen {

    public final GuiScreen guiCrashScreen;

    public GuiXiBaoCrashScreen(GuiScreen guiCrashScreen) {
        this.guiCrashScreen = guiCrashScreen;
    }

    @Override
    public void initGui() {
        guiCrashScreen.mc = this.mc;
        // ReflectionHelper.setPrivateValue(GuiScreen.class, guiCrashScreen, fontRendererObj, "fontRendererObj");
        guiCrashScreen.initGui();
        buttonList.clear();
        buttonList.add(new GuiButton(
                1,
                width / 2 - 50,
                height / 4 + 120 + 25,
                110,
                20,
                I18n.format("bettercrashes.gui.common.openCrashReport")));
        buttonList.add(new GuiButton(
                2,
                width / 2 - 50 + 115,
                height / 4 + 120 + 25,
                110,
                20,
                I18n.format("bettercrashes.gui.common.uploadReportAndCopyLink")));
        if (Config.isGTNH) {
            buttonList.add(new GuiButton(
                    3,
                    width / 2 - 50 - 15,
                    // 原为12
                    height / 4 + 120 + 25 + 25,
                    140,
                    20,
                    I18n.format("bettercrashes.gui.common.gtnhIssueTracker")));
        }
        GuiOptionButton mainMenuButton = new GuiOptionButton(
                0,
                width / 2 - 50 - 115,
                height / 4 + 120 + 25,
                110,
                20,
                I18n.format("bettercrashes.gui.crash.toTitle"));
        buttonList.add(mainMenuButton);
    }

    @Override
    protected void actionPerformed(GuiButton guiButton) {
        try {

            Method method = Class.forName("vfyjxf.bettercrashes.utils.GuiCrashScreen")
                    .getDeclaredMethod("func_146284_a", GuiButton.class);
            method.setAccessible(true);
            // ReflectionHelper.findMethod(GuiScreen.class, guiCrashScreen, new String[] {"actionPerformed"},
            // GuiButton.class);
            method.invoke(guiCrashScreen, guiButton);
        } catch (IllegalAccessException
                | InvocationTargetException
                | ClassNotFoundException
                | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void drawScreen(int x, int y, float tick) {
        this.drawXiBao();
        this.drawProblemScreen(x, y, tick);
        this.drawCrashScreen(x, y, tick);
        super.drawScreen(x, y, tick);
    }

    private void drawProblemScreen(int x, int y, float tick) {
        // guiCrashScreen.drawScreen(x, y, tick);
        try {
            Field detectedUnsupportedModNamesField = Class.forName("vfyjxf.bettercrashes.utils.GuiProblemScreen")
                    .getDeclaredField("detectedUnsupportedModNames");
            detectedUnsupportedModNamesField.setAccessible(true);
            List<String> detectedUnsupportedModNames =
                    (List<String>) detectedUnsupportedModNamesField.get(guiCrashScreen);
            if (detectedUnsupportedModNames == null) {
                detectedUnsupportedModNames = getUnsupportedMods();
            }
            boolean hasUnsupportedMods = !detectedUnsupportedModNames.isEmpty();

            int colorYellow = ColorUtil.color(255, 85, 85, 255);
            // drawDefaultBackground();
            drawCenteredString(
                    fontRendererObj,
                    I18n.format("bettercrashes.gui.crash.title"),
                    width / 2,
                    height / 4 - (hasUnsupportedMods ? 16 : 0),
                    colorYellow);

            int textColor = ColorUtil.color(190, 190, 190, 255);
            int x1 = width / 2 - 155;
            int y1 = (height / 4) + 40;
            if (hasUnsupportedMods) {
                y1 -= 32;
            }

            drawString(fontRendererObj, I18n.format("bettercrashes.gui.crash.summary"), x1, y1, textColor);
            drawString(fontRendererObj, I18n.format("bettercrashes.gui.common.paragraph1"), x1, y1 += 18, textColor);

            Method m1 =
                    Class.forName("vfyjxf.bettercrashes.utils.GuiProblemScreen").getDeclaredMethod("getModListString");
            m1.setAccessible(true);
            // ReflectionHelper.findMethod(GuiScreen.class, guiCrashScreen, new String[] {"getModListString"});
            drawCenteredString(fontRendererObj, (String) m1.invoke(guiCrashScreen), width / 2, y1 += 11, 0xE0E000);
            Method m2 = Class.forName("vfyjxf.bettercrashes.utils.GuiProblemScreen")
                    .getDeclaredMethod("isCrashLogExpectedToBeGenerated");
            m2.setAccessible(true);
            // ReflectionHelper.findMethod(GuiScreen.class, guiCrashScreen, new String[]
            // {"isCrashLogExpectedToBeGenerated"});
            if ((boolean) m2.invoke(guiCrashScreen)) {
                drawString(
                        fontRendererObj, I18n.format("bettercrashes.gui.common.paragraph2"), x1, y1 += 11, textColor);
                Field reportField = Class.forName("vfyjxf.bettercrashes.utils.GuiProblemScreen")
                        .getDeclaredField("report");
                reportField.setAccessible(true);
                CrashReport report = (CrashReport) reportField.get(guiCrashScreen);
                drawCenteredString(
                        fontRendererObj,
                        report.getFile() != null
                                ? "\u00A7n" + report.getFile().getName()
                                : I18n.format("bettercrashes.gui.common.reportSaveFailed"),
                        width / 2,
                        y1 += 11,
                        0x00FF00);
            } else {
                drawString(
                        fontRendererObj, I18n.format("bettercrashes.gui.common.paragraph6"), x1, y1 += 11, textColor);
                drawString(
                        fontRendererObj, I18n.format("bettercrashes.gui.common.paragraph7"), x1, y1 += 11, textColor);
            }

            y1 += 12;
            y1 += drawLongString(
                    fontRendererObj,
                    I18n.format("bettercrashes.gui.common.paragraph3" + (Config.isGTNH ? "_gtnh" : "")),
                    x1,
                    y1,
                    340,
                    textColor);

            if (hasUnsupportedMods) {
                drawString(
                        fontRendererObj,
                        I18n.format("bettercrashes.gui.common.paragraph4_gtnh"),
                        x1,
                        y1 += 10,
                        textColor);
                drawCenteredString(
                        fontRendererObj,
                        StringUtils.join(detectedUnsupportedModNames, ", "),
                        width / 2,
                        y1 += 11,
                        0xE0E000);
                drawString(
                        fontRendererObj,
                        I18n.format("bettercrashes.gui.common.paragraph5_gtnh"),
                        x1,
                        y1 += 12,
                        textColor);
            }
        } catch (IllegalAccessException
                | InvocationTargetException
                | ClassNotFoundException
                | NoSuchFieldException
                | NoSuchMethodException e) {
            XiBao.error(e.getLocalizedMessage());
            throw new RuntimeException(e);
        }
    }

    private void drawCrashScreen(int x, int y, float tick) {
        //        drawCenteredString(fontRendererObj, I18n.format("bettercrashes.crashscreen.title"), width / 2, height
        // / 4 - 40, 0xFFFFFF);
        //
        //        int textColor = 0xD0D0D0;
        //        int x = width / 2 - 155;
        //        int y = height / 4;
        //
        //        drawString(fontRendererObj, I18n.format("bettercrashes.crashscreen.summary"), x, y, textColor);
        //        drawString(fontRendererObj, I18n.format("bettercrashes.crashscreen.paragraph1.line1"), x, y += 18,
        // textColor);
        //
        //        drawCenteredString(fontRendererObj, getModListString(), width / 2, y += 11, 0xE0E000);
        //
        //        drawString(fontRendererObj, I18n.format("bettercrashes.crashscreen.paragraph2.line1"), x, y += 11,
        // textColor);
        //
        //        drawCenteredString(fontRendererObj, report.getFile() != null ? "\u00A7n" + report.getFile().getName()
        // : I18n.format("vanillafix.crashscreen.reportSaveFailed"), width / 2, y += 11, 0x00FF00);
        //
        //        drawString(fontRendererObj, I18n.format("bettercrashes.crashscreen.paragraph3.line1"), x, y += 12,
        // textColor);
        //        drawString(fontRendererObj, I18n.format("bettercrashes.crashscreen.paragraph3.line2"), x, y += 9,
        // textColor);
        //        drawString(fontRendererObj, I18n.format("bettercrashes.crashscreen.paragraph3.line3"), x, y += 9,
        // textColor);
        //        drawString(fontRendererObj, I18n.format("bettercrashes.crashscreen.paragraph3.line4"), x, y += 9,
        // textColor);
    }

    protected int drawLongString(FontRenderer fontRenderer, String text, int x, int y, int width, int color) {
        int yOffset = 0;
        for (Object line : Minecraft.getMinecraft().fontRenderer.listFormattedStringToWidth(text, width)) {
            drawString(fontRenderer, (String) line, x, y + yOffset, color);
            yOffset += 9;
        }
        return yOffset;
    }

    protected List<String> getUnsupportedMods() {
        if (!Config.isGTNH) {
            return Collections.emptyList();
        }
        try {
            List<String> list = new ArrayList<>();
            Field field = Class.forName("vfyjxf.bettercrashes.utils.GuiProblemScreen")
                    .getDeclaredField("UNSUPPORTED_MOD_IDS");
            field.setAccessible(true);
            List<String> UNSUPPORTED_MOD_IDS = (List<String>) field.get(guiCrashScreen);
            // ReflectionHelper.getPrivateValue(GuiScreen.class, guiCrashScreen, new String[] {"UNSUPPORTED_MOD_IDS"});
            for (ModContainer mod : Loader.instance().getModList()) {
                if (UNSUPPORTED_MOD_IDS.contains(mod.getModId())) {
                    list.add(mod.getName());
                }
            }
            if (FMLClientHandler.instance().hasOptifine()) {
                list.add("Optifine");
            }
            return list;
        } catch (NoSuchFieldException | ClassNotFoundException | IllegalAccessException e) {
            XiBao.error(e.getLocalizedMessage());
            for (StackTraceElement element : e.getStackTrace()) {
                XiBao.error(element.toString());
            }
            return Collections.emptyList();
        }
    }
}
