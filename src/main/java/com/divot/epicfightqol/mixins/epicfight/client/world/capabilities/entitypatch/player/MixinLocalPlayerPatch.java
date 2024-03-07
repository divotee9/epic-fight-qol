package com.divot.epicfightqol.mixins.epicfight.client.world.capabilities.entitypatch.player;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import com.divot.epicfightqol.Client;
import com.divot.epicfightqol.EventHandler;

import net.minecraft.client.player.LocalPlayer;
import yesman.epicfight.client.world.capabilites.entitypatch.player.AbstractClientPlayerPatch;
import yesman.epicfight.client.world.capabilites.entitypatch.player.LocalPlayerPatch;
import yesman.epicfight.main.EpicFightMod;
import yesman.epicfight.world.capabilities.item.CapabilityItem;

@Mixin(LocalPlayerPatch.class)
class MixinLocalPlayerPatch extends AbstractClientPlayerPatch<LocalPlayer> {

    @Shadow(remap = false)
    public void toBattleMode(boolean synchronize) {}

    @Shadow(remap = false)
    public void toMiningMode(boolean synchronize) {}

    @Overwrite(remap = false)
    public void updateHeldItem(CapabilityItem mainHandCap, CapabilityItem offHandCap) {
      super.updateHeldItem(mainHandCap, offHandCap);
      if (EpicFightMod.CLIENT_CONFIGS.battleAutoSwitchItems.contains(((LocalPlayer)this.original).getMainHandItem().getItem())) {
         this.toBattleMode(true);
      } else if (EpicFightMod.CLIENT_CONFIGS.miningAutoSwitchItems.contains(((LocalPlayer)this.original).getMainHandItem().getItem())) {
         this.toMiningMode(true);
         if (Client.CLIENT.getEpicHold()) {
            EventHandler.setEpicHolding(true);
            //EpicFightQOL.LOGGER.info("equipped");
         }
      }

   }
}
