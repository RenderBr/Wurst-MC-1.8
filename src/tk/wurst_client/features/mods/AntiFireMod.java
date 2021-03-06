/*
 * Copyright � 2014 - 2017 | Wurst-Imperium | All rights reserved.
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package tk.wurst_client.features.mods;

import net.minecraft.network.play.client.CPacketPlayer;
import tk.wurst_client.events.listeners.UpdateListener;

@Mod.Info(
	description = "Blocks damage from catching on fire.\n"
		+ "Does NOT block damage from standing inside of fire.\n"
		+ "Requires a full hunger bar.",
	name = "AntiFire",
	tags = "anti fire, AntiBurn, anti burn, NoFire, no fire",
	help = "Mods/AntiFire")
@Mod.Bypasses(ghostMode = false, latestNCP = false, olderNCP = false)
public class AntiFireMod extends Mod implements UpdateListener
{
	@Override
	public void onEnable()
	{
		wurst.events.add(UpdateListener.class, this);
	}
	
	@Override
	public void onDisable()
	{
		wurst.events.remove(UpdateListener.class, this);
	}
	
	@Override
	public void onUpdate()
	{
		if(!mc.player.capabilities.isCreativeMode && mc.player.onGround
			&& mc.player.isBurning())
			for(int i = 0; i < 100; i++)
				mc.player.connection.sendPacket(new CPacketPlayer());
	}
}
