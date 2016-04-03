package com.github.gist.thinca.spigot.plugin.Rocksmash

import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.plugin.java.JavaPlugin

class  Rocksmash: JavaPlugin() {
    override fun onEnable() {
        getServer().getPluginManager().registerEvents(
            object : Listener {
                fun isOre(block: Block) = when (block.getType()) {
                    Material.IRON_ORE,
		    Material.GOLD_ORE,
		    Material.COAL_ORE,
		    Material.LAPIS_ORE,
		    Material.DIAMOND_ORE,
		    Material.EMERALD_ORE,
		    Material.GLOWING_REDSTONE_ORE,
		    Material.QUARTZ_ORE,
		    Material.REDSTONE_ORE,
		    Material.OBSIDIAN -> true
                    else -> false
                }

                fun breakBlock(block: Block, player: Player) {
                    getServer().getScheduler().scheduleSyncDelayedTask(
                        this@Rocksmash,
                        object : Runnable {
                            override fun run() {
                                if (player.isValid() && isOre(block)) {
                                    val newEvent = BlockBreakEvent(block, player)
                                    getServer().getPluginManager().callEvent(newEvent)
                                    block.breakNaturally()
                                }
                            }
                        },
                        10
                    )
                }

                @EventHandler
                fun onBlockBreak(event: BlockBreakEvent) {
                    val ore = event.getBlock()
                    if (!isOre(ore)) {
                        return
                    }
                    val player = event.getPlayer()
		    val item = player.getInventory().getItemInMainHand().getType()
		    getLogger().info(item.toString());
                    for (modY in -1..1) {
                        for (modX in -1..1) {
                            for (modZ in -1..1) {
                                val block = ore.getRelative(modX, modY, modZ)
                                breakBlock(block, player)
                            }
                        }
		  }
                }
            },
            this
        )
    }
}