package com.xkball.xibao;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = XiBao.MODID, name = XiBao.NAME, version = XiBao.VERSION)
public class XiBao
{
    public static final String MODID = "xibao";
    public static final String NAME = "XiBao";
    public static final String VERSION = "1.0";
    
    public static final String GROUPNAME = "com.xkball.xibao";
    
    @SidedProxy(clientSide = XiBao.GROUPNAME + ".ClientProxy", serverSide = XiBao.GROUPNAME + ".CommonProxy")
    public static CommonProxy proxy;

    private static Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        // some example code
        //logger.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
        proxy.init(event);
        log("Xibao mod loaded");
    }
    
    public static void log(String s){
        logger.info(s);
    }
}
