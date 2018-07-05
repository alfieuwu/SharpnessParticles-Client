package me.javlin.sharpnessparticles;

import net.minecraft.client.*;
import net.minecraftforge.fml.client.*;
import org.lwjgl.input.*;
import net.minecraft.network.play.client.*;
import net.minecraft.util.*;
import net.minecraft.network.*;
import net.minecraft.client.entity.*;
import net.minecraft.entity.*;

public class SharpnessParticles
{
    public static boolean toggled;
    
    public static void tick() {
        SharpnessParticles1.queuedTasks.add(new Runnable() {
            @Override
            public void run() {
                if (!SharpnessParticles1.isSD && SharpnessParticles.toggled) {
                    final EntityPlayerSP ep = Minecraft.getMinecraft().thePlayer;
                    if (ep != null && FMLClientHandler.instance().getClient().inGameHasFocus && Mouse.isButtonDown(0)) {
                        ep.swingItem();
                        if (Minecraft.getMinecraft().objectMouseOver != null) {
                            final MovingObjectPosition mop = Minecraft.getMinecraft().objectMouseOver;
                            if (mop.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY) {
                                final Entity entity = mop.entityHit;
                                final C02PacketUseEntity pac = new C02PacketUseEntity(entity, C02PacketUseEntity.Action.ATTACK);
                                Minecraft.getMinecraft().effectRenderer.emitParticleAtEntity(entity, EnumParticleTypes.CRIT_MAGIC);
                                ep.sendQueue.addToSendQueue((Packet)pac);
                            }
                        }
                    }
                }
            }
        });
    }
    
    static {
        SharpnessParticles.toggled = true;
    }
}
