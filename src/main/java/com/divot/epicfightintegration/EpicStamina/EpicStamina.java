package com.divot.epicfightintegration.EpicStamina;

import com.alrex.parcool.common.capability.IStamina;
import com.divot.epicfightintegration.network.SyncEpicStaminaMessage;

import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.world.entity.player.Player;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

public class EpicStamina implements IStamina{
    public EpicStamina(Player player) {
		this.player = player;
	}

	final Player player;
	int old;

	private PlayerPatch getInternalInstance() {
		return EpicFightCapabilities.getEntityPatch(this.player, PlayerPatch.class);
	}

	@Override
	public int getMaxStamina() {
		return (int) getInternalInstance().getMaxStamina();
	}

	@Override
	public int getActualMaxStamina() {
		return (int) getInternalInstance().getStamina();
	}

	@Override
	public void setMaxStamina(int value) {
	}

	@Override
	public int get() {
		return (int) getInternalInstance().getStamina();
	}

	@Override
	public int getOldValue() {
		return old;
	}

	@Override
	public void consume(int value) {
		PlayerPatch playerStamina = getInternalInstance();
		playerStamina.consumeStaminaAlways(playerStamina.getModifiedStaminaConsume(((float)value)/10));
		if (player instanceof AbstractClientPlayer clientPlayer)
			SyncEpicStaminaMessage.send(clientPlayer, value, SyncEpicStaminaMessage.Type.TAKE);
	}

	@Override
	public void recover(int value) {
	}

	@Override
	public boolean isExhausted() {
		return !getInternalInstance().hasStamina(1);
	}

	@Override
	public void setExhaustion(boolean value) {
	}

	@Override
	public void tick() {
		old = get();
	}

	@Override
	public void set(int value) {
	}
}
