package com.divot.epicfightqol;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import yesman.epicfight.client.ClientEngine;
import yesman.epicfight.client.input.EpicFightKeyMappings;
import java.util.Iterator;
import yesman.epicfight.client.world.capabilites.entitypatch.player.LocalPlayerPatch;
import yesman.epicfight.main.EpicFightMod;

import org.lwjgl.glfw.GLFW;
import com.alrex.parcool.common.action.Action;
import com.alrex.parcool.common.action.impl.FastRun;
import com.alrex.parcool.common.capability.Parkourability;
import com.gitlab.srcmc.epiccompat_parcool.forge.client.capabilities.IParkourPlayerPatch;
import com.mrcrayfish.guns.client.handler.AimingHandler;

import yesman.epicfight.world.capabilities.EpicFightCapabilities;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(Dist.CLIENT)
public class EventHandler {

    private static boolean epicUsing = false;

    private static boolean manualSwitch = false;

    private static boolean shiftSwitch = false;

    private static boolean epicHolding = false;

    private static final Minecraft mc = Minecraft.getInstance();

    public static boolean checkUseItem(ItemStack item) {
        if (Config.CLIENT.epicItems().contains(ForgeRegistries.ITEMS.getKey(item.getItem()).toString())) {
            return true;
        }
        return false;
    }

    public static boolean checkHeldItem(ItemStack item) {
        if (EpicFightMod.CLIENT_CONFIGS.miningAutoSwitchItems.contains(item.getItem())) {
            return true;
        }
        return false;
    }

    @SubscribeEvent
    public static void useEvent (LivingEntityUseItemEvent.Start event){

        if (mc.player != null && !mc.isPaused() && Config.CLIENT.epicItems() != null) {

            LocalPlayerPatch ppplayer = EpicFightCapabilities.getEntityPatch(mc.player, LocalPlayerPatch.class);

            if (checkUseItem(event.getItem()) && ppplayer.isBattleMode()) {
                
                epicUsing = true;
                //IHandlerPatch handledPlayer = (IHandlerPatch)ClientEngine.getInstance().getPlayerPatch();
                //EpicFightQOL.LOGGER.info("used");
                if(ppplayer.isBattleMode()){
                    ppplayer.toMiningMode(true);
                }
            }
        }
    }

    @SubscribeEvent
    public static void unuseEvent (LivingEntityUseItemEvent.Stop event){

        if (mc.player != null && !mc.isPaused() && epicUsing) {

            LocalPlayerPatch ppplayer = EpicFightCapabilities.getEntityPatch(mc.player, LocalPlayerPatch.class);

            epicUsing = false ;
                
            //EpicFightQOL.LOGGER.info("used");
            if(!ppplayer.isBattleMode() && !manualSwitch && !shiftSwitch){
                ppplayer.toBattleMode(true);
            }
        }
    }

    @SubscribeEvent
    public static void aimEvent (InputEvent.MouseButton.Post event){

        if (mc.player != null && !mc.isPaused() && Config.CLIENT.getEpicAiming()) {

            if (AimingHandler.get().isAiming()) {
                epicUsing = true;
                //IHandlerPatch handledPlayer = (IHandlerPatch)ClientEngine.getInstance().getPlayerPatch();
                //EpicFightQOL.LOGGER.info("used");
                LocalPlayerPatch ppplayer = EpicFightCapabilities.getEntityPatch(mc.player, LocalPlayerPatch.class);

                if(ppplayer.isBattleMode()){
                    ppplayer.toMiningMode(true);
                }
            }
        }
    }

    @SubscribeEvent
    public static void unaimEvent (InputEvent.MouseButton.Post event){

        if (mc.player != null && !mc.isPaused() && epicUsing && Config.CLIENT.getEpicAiming()) {

            if (!AimingHandler.get().isAiming()){

                epicUsing = false ;
                
                LocalPlayerPatch ppplayer = EpicFightCapabilities.getEntityPatch(mc.player, LocalPlayerPatch.class);

                if(!ppplayer.isBattleMode()){
                ppplayer.toBattleMode(true);
                }
            }
        }
    }

    @SubscribeEvent
    public static void keyDodge (InputEvent.Key event){

        if (mc.player != null && !mc.isPaused() && Config.CLIENT.getparkourDodge()){

            LocalPlayerPatch ppplayer = EpicFightCapabilities.getEntityPatch(mc.player, LocalPlayerPatch.class);
            
            if(EpicFightKeyMappings.DODGE.consumeClick() && event.getAction() == 1 && ppplayer.isBattleMode()) {

                IParkourPlayerPatch pPlayer = (IParkourPlayerPatch)ClientEngine.getInstance().getPlayerPatch();

                if(pPlayer !=null){

                    Parkourability parkourAbility = Parkourability.get(mc.player);

                    if (parkourAbility != null) {

                        Iterator var3 = parkourAbility.getList().iterator();

                        while(var3.hasNext()) {
                            Action action = (Action)var3.next();

                            if (action.isDoing() && !(action instanceof FastRun)) {

                                action.setDoing(false);
                                //pPlayer.setParkourActive(false);
                                //NetworkManager.sendToServer(new CPSetParkourActive(false));
                                //EpicParkour.LOGGER.info("dodge interupt");
                                KeyMapping.click(EpicFightKeyMappings.DODGE.getKey());

                                return;
                            }

                        }

                    return;
                    }
                }
            }    
        }
    }

    @SubscribeEvent
    public static void mouseDodge (InputEvent.MouseButton event){

        if (mc.player != null && !mc.isPaused() && Config.CLIENT.getparkourDodge()){

            LocalPlayerPatch ppplayer = EpicFightCapabilities.getEntityPatch(mc.player, LocalPlayerPatch.class);
            
            if(EpicFightKeyMappings.DODGE.consumeClick() && event.getAction() == 1 && ppplayer.isBattleMode()) {

                IParkourPlayerPatch pPlayer = (IParkourPlayerPatch)ClientEngine.getInstance().getPlayerPatch();

                if(pPlayer !=null){

                    Parkourability parkourAbility = Parkourability.get(mc.player);

                    if (parkourAbility != null) {

                        Iterator var3 = parkourAbility.getList().iterator();

                        while(var3.hasNext()) {
                            Action action = (Action)var3.next();

                            if (action.isDoing() && !(action instanceof FastRun)) {

                                action.setDoing(false);
                                //pPlayer.setParkourActive(false);
                                //NetworkManager.sendToServer(new CPSetParkourActive(false));
                                //EpicParkour.LOGGER.info("dodge interupt");
                                KeyMapping.click(EpicFightKeyMappings.DODGE.getKey());

                                return;
                            }

                        }

                    return;
                    }
                }
            }    
        }
    }
    

    @SubscribeEvent
    public static void shiftEvent (InputEvent.Key event){

        if (mc.player != null && !mc.isPaused() && Config.CLIENT.getBattleShift() && !manualSwitch){
            if (event.getAction() == 1 && event.getKey() == GLFW.GLFW_KEY_LEFT_SHIFT) {

                LocalPlayerPatch ppplayer = EpicFightCapabilities.getEntityPatch(mc.player, LocalPlayerPatch.class);

                if (ppplayer.isBattleMode()){

                    ppplayer.toMiningMode(true);
                    //EpicParkour.LOGGER.info("shift press");

                    shiftSwitch = true;
        
                    return;
                }
                return;
            }
            return;
        } 
        return;
    }

    @SubscribeEvent
    public static void shiftReleaseEvent (InputEvent.Key event){

        if (mc.player != null && !mc.isPaused() && !manualSwitch && shiftSwitch){
            if (event.getAction() == 0 && event.getKey() == GLFW.GLFW_KEY_LEFT_SHIFT) {

                LocalPlayerPatch ppplayer = EpicFightCapabilities.getEntityPatch(mc.player, LocalPlayerPatch.class);

                shiftSwitch = false;

                if (!ppplayer.isBattleMode()){

                    ppplayer.toBattleMode(true);
                    //EpicParkour.LOGGER.info("shift release");
        
                    return;
                }
                return;
            }
            return;
        } 
        return;
    }

    @SubscribeEvent
    public static void manualSwitchEvent (InputEvent.Key event){

        if (mc.player != null && !mc.isPaused()){
            if (event.getAction() == 1 && event.getKey() == EpicFightKeyMappings.SWITCH_MODE.getKey().getValue()) {
                
                LocalPlayerPatch ppplayer = EpicFightCapabilities.getEntityPatch(mc.player, LocalPlayerPatch.class);

                if(ppplayer.isBattleMode() && !manualSwitch){
                    manualSwitch = true;
                    //EpicParkour.LOGGER.info("manual switch on");
                    return;
                }

                if(!ppplayer.isBattleMode() && manualSwitch){
                    manualSwitch = false;
                    //EpicParkour.LOGGER.info("manual switch off");
                    return;
                }

                return;
            }
            return;
        } 
        return;
    }



    @SubscribeEvent
    public static void unholdEvent (InputEvent.MouseScrollingEvent event){

        if (mc.player != null && !mc.isPaused() && epicHolding) {

            epicHolding = false;

            LocalPlayerPatch ppplayer = EpicFightCapabilities.getEntityPatch(mc.player, LocalPlayerPatch.class);

            if (checkHeldItem(mc.player.getMainHandItem()) && !ppplayer.isBattleMode() && !manualSwitch){
                ppplayer.toBattleMode(true);

                //EpicParkour.LOGGER.info("unequipped");
            }
        }
    }

    public static boolean getEpicUsing() {
        return epicUsing;
    }

    public static void setEpicHolding(boolean val) {
        epicHolding = val;
    }

    public static boolean getEpicHolding() {
        return epicHolding;
    }

}
