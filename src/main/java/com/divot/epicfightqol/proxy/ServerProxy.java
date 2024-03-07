package com.divot.epicfightqol.proxy;
import com.divot.epicfightqol.EpicFightQOL;
import com.divot.epicfightqol.EpicStamina.SyncEpicStaminaMessage;

import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.network.simple.SimpleChannel;

@OnlyIn(Dist.DEDICATED_SERVER)
public class ServerProxy extends CommonProxy{
    
    @Override
	public void registerMessages(SimpleChannel instance) {
        instance.registerMessage(
				19,
				SyncEpicStaminaMessage.class,
				SyncEpicStaminaMessage::encode,
				SyncEpicStaminaMessage::decode,
				SyncEpicStaminaMessage::handleServer
		);
		EpicFightQOL.LOGGER.info("got in");
	}
}
