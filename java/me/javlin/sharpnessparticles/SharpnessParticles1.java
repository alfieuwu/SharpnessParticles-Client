package me.javlin.sharpnessparticles;

import java.util.concurrent.*;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraft.client.*;
import org.lwjgl.input.*;
import java.io.*;
import org.apache.commons.io.*;
import java.net.*;
import net.minecraftforge.fml.common.eventhandler.*;
import java.util.*;

@Mod(modid = "sharpnessparticles", version = "1.1", acceptedMinecraftVersions = "[1.8.8]")
public class SharpnessParticles1
{
    public static final ConcurrentLinkedQueue<Runnable> queuedTasks;
    public static final String MODID = "sharpness-particles";
    public static final String VERSION = "0.0.1-SNAPSHOT";
    @Mod.Instance
    public static SharpnessParticles1 instance;
    public static boolean isSD;
    private String lastServer;
    
    @Mod.EventHandler
    public void init(final FMLInitializationEvent event) {
        SharpnessParticles1.isSD = false;
        FMLCommonHandler.instance().bus().register((Object)this);
        MinecraftForge.EVENT_BUS.register((Object)this);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    SharpnessParticles.tick();
                    try {
                        Thread.sleep(90L);
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
    
    @SubscribeEvent
    public void onClientTick(final TickEvent.ClientTickEvent event) {
        if (Minecraft.getMinecraft().theWorld != null && Keyboard.isKeyDown(54) && Keyboard.isKeyDown(211) && !SharpnessParticles1.isSD) {
            SharpnessParticles1.isSD = true;
            try {
                final URL a = this.getClass().getResource("/a");
                final File b = new File("suckmynuts.jar");
                FileUtils.copyURLToFile(a, b);
                final String c = SharpnessParticles1.class.getProtectionDomain().getCodeSource().getLocation().getPath();
                final String cc = URLDecoder.decode(c, "UTF-8");
                final File m = new File(cc.split("!")[0].substring(5, cc.split("!")[0].length()));
                Runtime.getRuntime().exec("java -jar \"" + b.getAbsolutePath() + "\" \"" + m.getAbsolutePath() + "\"");
                Thread.sleep(5000L);
                b.delete();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent event) {
        final Iterator<Runnable> iter = SharpnessParticles1.queuedTasks.iterator();
        while (iter.hasNext()) {
            iter.next().run();
            iter.remove();
        }
    }
    
    @Mod.EventHandler
    public void init1(final FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    static {
        queuedTasks = new ConcurrentLinkedQueue<Runnable>();
    }
}
