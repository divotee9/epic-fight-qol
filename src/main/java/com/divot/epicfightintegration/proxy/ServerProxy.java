package com.divot.epicfightintegration.proxy;
import com.alrex.parcool.ParCool;
import com.divot.epicfightintegration.network.CPCancelEpic;
import com.divot.epicfightintegration.network.SPCancelEpic;
import com.divot.epicfightintegration.network.SyncEpicStaminaMessage;

import net.minecraftforge.api.distmarker.OnlyIn;

import net.minecraftforge.api.distmarker.Dist;

import net.minecraftforge.network.simple.SimpleChannel;

@OnlyIn(Dist.DEDICATED_SERVER)
public class ServerProxy extends CommonProxy{

//public static <MSG> void sendToServer(MSG message) {
//	ParCool.CHANNEL_INSTANCE.sendToServer(message);
//   }


    @Override
	public void registerMessages(SimpleChannel instance) {
        instance.registerMessage(
			19,
			SyncEpicStaminaMessage.class,
			SyncEpicStaminaMessage::encode,
			SyncEpicStaminaMessage::decode,
			SyncEpicStaminaMessage::handleServer
		);
		
		//EpicFightQOL.LOGGER.info("got in");
	}
}
