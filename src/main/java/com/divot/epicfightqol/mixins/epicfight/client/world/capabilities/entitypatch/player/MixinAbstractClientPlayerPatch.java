package com.divot.epicfightqol.mixins.epicfight.client.world.capabilities.entitypatch.player;

import com.divot.epicfightqol.EventHandler;

import net.minecraft.client.player.AbstractClientPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import yesman.epicfight.client.world.capabilites.entitypatch.player.AbstractClientPlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

@Mixin({AbstractClientPlayerPatch.class})
public abstract class MixinAbstractClientPlayerPatch<T extends AbstractClientPlayer> extends PlayerPatch<T>{
   @Inject(
      method = {"overrideRender"},
      at = {@At("HEAD")},
      cancellable = true,
      remap = false
   )

   public void onOverrideRender(CallbackInfoReturnable<Boolean> ci) {
      
      if(EventHandler.getEpicUsing() || EventHandler.getEpicHolding()){
        ci.setReturnValue(false);
        ci.cancel();
        //EpicFightQOL.LOGGER.info("cancelled");
      }
   }
}
