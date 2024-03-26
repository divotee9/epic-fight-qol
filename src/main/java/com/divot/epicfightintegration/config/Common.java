package com.divot.epicfightintegration.config;

import org.apache.commons.lang3.tuple.Pair;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;


public class Common {

    public static final ForgeConfigSpec SPEC;

    public static final CommonConfig COMMON;

    public Common() {
    }



    static {
        Pair<CommonConfig, ForgeConfigSpec> pair = (new ForgeConfigSpec.Builder()).configure(CommonConfig::new);
        SPEC = (ForgeConfigSpec) pair.getRight();
        COMMON = (CommonConfig) pair.getLeft();



        //BUILDER.push("Common Config");
//
        //this.epicStamina = BUILDER.comment("If Parcool should use Epic Fight's stamina").define("Epic Stamina", false);
//
        //BUILDER.pop();
        //SPEC = BUILDER.build();
    }


    public static class CommonConfig {

        private final ConfigValue<Boolean> epicStamina;

        public CommonConfig(ForgeConfigSpec.Builder builder) {
            builder.push("Config");
      
            this.epicStamina = builder.define("Use Epic Fight Stamina for Parcool", () -> {
                return false;
            });

            builder.pop();

        }
        public boolean getEpicStamina() {
            return this.epicStamina.get();
        }
    }

}
