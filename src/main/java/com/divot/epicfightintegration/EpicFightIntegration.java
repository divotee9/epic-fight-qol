package com.divot.epicfightintegration;

import com.mojang.logging.LogUtils;
import com.alrex.parcool.ParCool;
import com.divot.epicfightintegration.config.Client;
import com.divot.epicfightintegration.config.Common;
import com.divot.epicfightintegration.network.NetworkHandler;
import com.divot.epicfightintegration.proxy.ClientProxy;
import com.divot.epicfightintegration.proxy.CommonProxy;
import com.divot.epicfightintegration.proxy.ServerProxy;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;

import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import org.slf4j.Logger;

@Mod(EpicFightIntegration.MODID)
public class EpicFightIntegration
{
    public static final String MODID = "epicfightintegration";


    public static final CommonProxy PROXY = DistExecutor.unsafeRunForDist(
			() -> ClientProxy::new,
			() -> ServerProxy::new
	);

    public static final Logger LOGGER = LogUtils.getLogger();
    public EpicFightIntegration()
    {
        ModLoadingContext modLoadingContext = ModLoadingContext.get();
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::commonSetup);

        //MinecraftForge.EVENT_BUS.addListener(this::registerCommand);

        MinecraftForge.EVENT_BUS.register(this);

        PROXY.init();

        modLoadingContext.registerConfig(ModConfig.Type.COMMON, Common.SPEC, "epicfightintegration-common.toml");

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> {
            return () -> {
                modLoadingContext.registerConfig(ModConfig.Type.CLIENT, Client.SPEC, "epicfightintegration-client.toml");
            };
        });
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        //EventBusForgeRegistry.register(MinecraftForge.EVENT_BUS);
		//EventBusModRegistry.register(FMLJavaModLoadingContext.get().getModEventBus());

		PROXY.registerMessages(NetworkHandler.INSTANCE);

    }

    //private void registerCommand(final RegisterCommandsEvent event) {
	//	CommandRegistry.register(event.getDispatcher());
	//}

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
        }
    }
}
