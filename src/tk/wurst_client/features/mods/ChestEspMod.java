/*
 * Copyright � 2014 - 2017 | Wurst-Imperium | All rights reserved.
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package tk.wurst_client.features.mods;

import net.minecraft.entity.item.EntityMinecartChest;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityEnderChest;
import tk.wurst_client.events.listeners.RenderListener;
import tk.wurst_client.features.Feature;
import tk.wurst_client.utils.ChatUtils;
import tk.wurst_client.utils.RenderUtils;

@Mod.Info(description = "Allows you to see chests through walls.",
	name = "ChestESP",
	tags = "ChestFinder, chest esp, chest finder",
	help = "Mods/ChestESP")
@Mod.Bypasses
public class ChestEspMod extends Mod implements RenderListener
{
	private int maxChests = 1000;
	public boolean shouldInform = true;
	
	@Override
	public Feature[] getSeeAlso()
	{
		return new Feature[]{wurst.mods.itemEspMod, wurst.mods.searchMod,
			wurst.mods.xRayMod};
	}
	
	@Override
	public void onEnable()
	{
		shouldInform = true;
		wurst.events.add(RenderListener.class, this);
	}
	
	@Override
	public void onRender()
	{
		int i = 0;
		for(Object o : mc.world.loadedTileEntityList)
		{
			if(i >= maxChests)
				break;
			if(o instanceof TileEntityChest)
			{
				i++;
				RenderUtils.blockESPBox(((TileEntityChest)o).getPos());
			}else if(o instanceof TileEntityEnderChest)
			{
				i++;
				RenderUtils.blockESPBox(((TileEntityEnderChest)o).getPos());
			}
		}
		for(Object o : mc.world.loadedEntityList)
		{
			if(i >= maxChests)
				break;
			if(o instanceof EntityMinecartChest)
			{
				i++;
				RenderUtils.blockESPBox(((EntityMinecartChest)o).getPosition());
			}
		}
		if(i >= maxChests && shouldInform)
		{
			ChatUtils.warning(getName() + " found �lA LOT�r of chests.");
			ChatUtils.message("To prevent lag, it will only show the first "
				+ maxChests + " chests.");
			shouldInform = false;
		}else if(i < maxChests)
			shouldInform = true;
	}
	
	@Override
	public void onDisable()
	{
		wurst.events.remove(RenderListener.class, this);
	}
}
