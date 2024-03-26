package com.divot.epicfightintegration.config;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;

import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

public class Client {
    public static final ForgeConfigSpec SPEC;
    public static final ClientConfig CLIENT;

    public Client() {
    }



    static {
        Pair<ClientConfig, ForgeConfigSpec> pair = (new ForgeConfigSpec.Builder()).configure(ClientConfig::new);
        SPEC = (ForgeConfigSpec) pair.getRight();
        CLIENT = (ClientConfig) pair.getLeft();
    }

    public static class ClientConfig {

        private final ForgeConfigSpec.ConfigValue<List<? extends String>> epicItems;

        private final ConfigValue<Boolean> epicHold;

        private final ConfigValue<Boolean> battleShift;

        private final ConfigValue<Boolean> parkourDodge;

        private final ConfigValue<Boolean> epicAim;

        public ClientConfig(ForgeConfigSpec.Builder builder) {
            builder.push("Config");
            this.epicItems = builder.comment("Items that switch to mining mode whilst using").translation("Epic Use Items").defineList("epic_use_items", () -> {
                List<String> items = new ArrayList<>();
                //items.add(ForgeRegistries.ITEMS.getKey(Items.SPYGLASS).toString());
                return items;
            }, (item) -> {
                return item != null && ResourceLocation.isValidResourceLocation(item.toString());
            });

            this.battleShift = builder.define("Mining mode while holding shift", () -> {
                return true;
            });

            this.epicHold = builder.define("Auto switch back to battle mode when unequipping an item listed in Epic Fight's mining autoswitch config", () -> {
                return true;
            });

            this.parkourDodge = builder.define("Allow dodge during parkour", () -> {
                return false;
            });

            this.epicAim = builder.define("Switch to mining mode whilst aiming with MrCrayfish Guns", () -> {
                return false;
            });

            builder.pop();

        }

        public List<? extends String> epicItems() {
            return this.epicItems.get();
        }

        public boolean getEpicHold() {
            return this.epicHold.get();
        }

        public boolean getBattleShift() {
            return this.battleShift.get();
        }

        public boolean getparkourDodge() {
            return this.parkourDodge.get();
        }

        public boolean getEpicAiming() {
            return this.epicAim.get();
        }

    }


}
