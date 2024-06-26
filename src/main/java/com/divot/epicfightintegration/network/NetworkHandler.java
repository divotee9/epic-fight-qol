 // Source code is unavailable, and was generated by the Fernflower decompiler.
 package com.divot.epicfightintegration.network;

 import com.gitlab.srcmc.epiccompat_parcool.forge.network.client.CPSetParkourActive;
 import com.gitlab.srcmc.epiccompat_parcool.forge.network.server.SPSetParkourActive;
 
 import net.minecraft.resources.ResourceLocation;
 import net.minecraft.server.level.ServerPlayer;
 import net.minecraft.world.entity.Entity;
 import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
 import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.NetworkRegistry;
 import net.minecraftforge.network.PacketDistributor;
 import net.minecraftforge.network.PacketDistributor.PacketTarget;
 import net.minecraftforge.network.simple.SimpleChannel;
 
 @EventBusSubscriber(
    modid = "epicfightintegration",
    bus = Bus.MOD
 )
 public class NetworkHandler {
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation("epicfightintegration", "network_manager"), () -> {
       return "1";
    }, "1"::equals, "1"::equals);
 
    public static <MSG> void sendToServer(MSG message) {
       INSTANCE.sendToServer(message);
    }
 
    public static <MSG> void sendToClient(MSG message, PacketTarget packetTarget) {
       INSTANCE.send(packetTarget, message);
    }
 
    public static <MSG> void sendToAll(MSG message) {
       sendToClient(message, PacketDistributor.ALL.noArg());
    }
 
    public static <MSG> void sendToAllPlayerTrackingThisEntity(MSG message, Entity entity) {
       sendToClient(message, PacketDistributor.TRACKING_ENTITY.with(() -> {
          return entity;
       }));
    }
 
    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
       sendToClient(message, PacketDistributor.PLAYER.with(() -> {
          return player;
       }));
    }
 
    public static <MSG> void sendToAllPlayerTrackingThisEntityWithSelf(MSG message, ServerPlayer entity) {
       sendToClient(message, PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> {
          return entity;
       }));
    }
 
    public static <MSG> void sendToAllPlayerTrackingThisChunkWithSelf(MSG message, LevelChunk chunk) {
       sendToClient(message, PacketDistributor.TRACKING_CHUNK.with(() -> {
          return chunk;
       }));
    }

    @SubscribeEvent
   public static void register(FMLCommonSetupEvent event) {
      INSTANCE.registerMessage(
			20, 
			SPCancelEpic.class, 
			SPCancelEpic::toBytes,
			SPCancelEpic::fromBytes,
			SPCancelEpic::handle);
    INSTANCE.registerMessage(
			21, 
			CPCancelEpic.class, 
			CPCancelEpic::toBytes, 
			CPCancelEpic::fromBytes, 
			CPCancelEpic::handle);
   }
 }
 