package com.divot.epicfightintegration.EpicStamina;

import com.alrex.parcool.common.capability.IStamina;
import com.divot.epicfightintegration.config.Common;

import net.minecraft.world.entity.player.Player;


import javax.annotation.Nullable;

public class EpicFightManager {

	@Nullable
	public static IStamina newEpicStaminaFor(Player player) {
		if (!Common.COMMON.getEpicStamina()) return null;
		return new EpicStamina(player);
	}
}