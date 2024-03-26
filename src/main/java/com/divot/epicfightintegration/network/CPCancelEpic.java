package com.divot.epicfightintegration.network;


import java.util.function.Supplier;

import com.divot.epicfightintegration.client.IIntegratedPlayerPatch;

import net.minecraftforge.network.NetworkEvent.Context;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

public class CPCancelEpic {
    private final boolean cancelEpic;

   public CPCancelEpic() {
      this.cancelEpic = false;
   }

   public CPCancelEpic(boolean cancelEpic) {
      this.cancelEpic = cancelEpic;
   }

   public static CPCancelEpic fromBytes(FriendlyByteBuf buf) {
      return new CPCancelEpic(buf.readBoolean());
   }

   public static void toBytes(CPCancelEpic msg, FriendlyByteBuf buf) {
      buf.writeBoolean(msg.cancelEpic);
   }

   public static void handle(CPCancelEpic msg, Supplier<Context> ctx) {
      ((Context)ctx.get()).enqueueWork(() -> {
         ServerPlayer player = ((Context)ctx.get()).getSender();
         if (player != null) {
            IIntegratedPlayerPatch entitypatch = (IIntegratedPlayerPatch)EpicFightCapabilities.getEntityPatch(player, ServerPlayerPatch.class);
            if (entitypatch != null) {
               entitypatch.setEpicCancelled(msg.cancelEpic);
               NetworkHandler.sendToAllPlayerTrackingThisEntity(new SPCancelEpic(msg.cancelEpic, player.getId()), player);
            }
         }

      });
      ((Context)ctx.get()).setPacketHandled(true);
   }
    
}
