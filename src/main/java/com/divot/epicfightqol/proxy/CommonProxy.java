package com.divot.epicfightqol.proxy;

import net.minecraftforge.network.simple.SimpleChannel;

public abstract class CommonProxy {
	public abstract void registerMessages(SimpleChannel instance);

	public void init() {
	}
}
