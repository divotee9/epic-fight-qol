package com.divot.epicfightintegration.mixins.epicfight.world.capabilities.entitypatch.player;

import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.divot.epicfightintegration.client.IIntegratedPlayerPatch;

import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

@Mixin({PlayerPatch.class})
public abstract class MixinPlayerPatch<T extends Player> extends LivingEntityPatch<T> implements IIntegratedPlayerPatch {
   protected boolean epicCancelled;

   @Inject(
      method = {"isUnstable"},
      at = {@At("HEAD")},
      cancellable = true,
      remap = false
   )
   public void onIsUnstable(CallbackInfoReturnable<Boolean> ci) {
      if (this.isEpicCancelled()) {
         ci.setReturnValue(true);
         ci.cancel();
      }
   }
   
   public boolean isEpicCancelled() {
      return this.epicCancelled;
   }

   public void setEpicCancelled(boolean value) {
      this.epicCancelled = value;
   }
}