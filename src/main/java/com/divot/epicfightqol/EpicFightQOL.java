package com.divot.epicfightqol;

import com.mojang.logging.LogUtils;
import com.alrex.parcool.ParCool;
import com.divot.epicfightqol.proxy.ClientProxy;
import com.divot.epicfightqol.proxy.CommonProxy;
import com.divot.epicfightqol.proxy.ServerProxy;


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

@Mod(EpicFightQOL.MODID)
public class EpicFightQOL
{
    public static final String MODID = "epicfightqol";


    public static final CommonProxy PROXY = DistExecutor.unsafeRunForDist(
			() -> ClientProxy::new,
			() -> ServerProxy::new
	);

    public static final Logger LOGGER = LogUtils.getLogger();
    public EpicFightQOL()
    {
        ModLoadingContext modLoadingContext = ModLoadingContext.get();
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::commonSetup);

        //MinecraftForge.EVENT_BUS.addListener(this::registerCommand);

        MinecraftForge.EVENT_BUS.register(this);

        PROXY.init();

        modLoadingContext.registerConfig(ModConfig.Type.COMMON, Common.SPEC, "epicfightqol-common.toml");

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> {
            return () -> {
                modLoadingContext.registerConfig(ModConfig.Type.CLIENT, Client.SPEC, "epicfightqol-client.toml");
            };
        });
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        //EventBusForgeRegistry.register(MinecraftForge.EVENT_BUS);
		//EventBusModRegistry.register(FMLJavaModLoadingContext.get().getModEventBus());

		PROXY.registerMessages(ParCool.CHANNEL_INSTANCE);

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
