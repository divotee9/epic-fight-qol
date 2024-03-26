package com.divot.epicfightintegration.network;

import java.util.function.Supplier;

import com.divot.epicfightintegration.client.IIntegratedPlayerPatch;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent.Context;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import net.minecraft.world.entity.Entity;

public class SPCancelEpic {
    private final boolean cancelEpic;
   private final int entityId;

   public SPCancelEpic() {
      this.cancelEpic = false;
      this.entityId = 0;
   }

   public SPCancelEpic(boolean parkourActive, int entityId) {
      this.cancelEpic = parkourActive;
      this.entityId = entityId;
   }

   public static SPCancelEpic fromBytes(FriendlyByteBuf buf) {
      return new SPCancelEpic(buf.readBoolean(), buf.readInt());
   }

   public static void toBytes(SPCancelEpic msg, FriendlyByteBuf buf) {
      buf.writeBoolean(msg.cancelEpic);
      buf.writeInt(msg.entityId);
   }

   public static void handle(SPCancelEpic msg, Supplier<Context> ctx) {
      ((Context)ctx.get()).enqueueWork(() -> {
         Minecraft mc = Minecraft.getInstance();
         Entity entity = mc.level.getEntity(msg.entityId);
         if (entity != null) {
            IIntegratedPlayerPatch entitypatch = (IIntegratedPlayerPatch)EpicFightCapabilities.getEntityPatch(entity, PlayerPatch.class);
            if (entitypatch != null) {
               entitypatch.setEpicCancelled(msg.cancelEpic);
            }
         }

      });
      ((Context)ctx.get()).setPacketHandled(true);
   }
}
