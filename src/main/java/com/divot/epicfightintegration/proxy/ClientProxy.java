package com.divot.epicfightintegration.proxy;

import com.alrex.parcool.common.registries.EventBusForgeRegistry;
import com.alrex.parcool.common.registries.EventBusModRegistry;
import com.divot.epicfightintegration.network.SyncEpicStaminaMessage;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.simple.SimpleChannel;

@OnlyIn(Dist.CLIENT)
public class ClientProxy extends CommonProxy {
    
	@Override
	public void init() {
		EventBusForgeRegistry.registerClient(MinecraftForge.EVENT_BUS);
		EventBusModRegistry.registerClient(FMLJavaModLoadingContext.get().getModEventBus());
	}

	@Override
	public void registerMessages(SimpleChannel instance) {
		instance.registerMessage(
				19,
				SyncEpicStaminaMessage.class,
				SyncEpicStaminaMessage::encode,
				SyncEpicStaminaMessage::decode,
				SyncEpicStaminaMessage::handleClient
		);
	}
}