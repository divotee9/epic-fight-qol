package com.divot.epicfightqol.EpicStamina;

import com.alrex.parcool.server.command.impl.StaminaControlCommand;
import com.divot.epicfightqol.EpicFightQOL;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;


public class CommandRegistry {
	public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {

		dispatcher.register(
				Commands.literal(EpicFightQOL.MODID)
						.then(StaminaControlCommand.getBuilder())
		);
	}
}