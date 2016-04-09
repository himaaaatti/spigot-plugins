package com.github.himaaaatti.spigot.plugin.dig

import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.plugin.java.JavaPlugin

class  Dig: JavaPlugin() {
    override fun onEnable() {
        getServer().getPluginManager().registerEvents(
            object : Listener {
                fun isDirt(block: Block) = when (block.getType()) {
                    Material.GRASS,
		    Material.DIRT,
		    Material.SAND,
		    Material.CLAY,
		    Material.GRAVEL,
		    Material.SOUL_SAND,
		    Material.SNOW -> true
                    else -> false
                }

		fun isSand(block: Block) = when (block.getType()) {
                    Material.SAND -> true
                    else -> false
                }

		fun isShovel(type: Material) = when(type) {
		    Material.WOOD_SPADE,
		    Material.STONE_SPADE,
		    Material.IRON_SPADE,
		    Material.GOLD_SPADE,
		    Material.DIAMOND_SPADE -> true
		    else -> false
		}

                fun breakBlock(block: Block, player: Player) {
                    getServer().getScheduler().scheduleSyncDelayedTask(
                        this@Dig,
                        object : Runnable {
                            override fun run() {
                                if (player.isValid() && isDirt(block)) {
				    block.breakNaturally()
                                }
                            }
                        },
                        10
                    )
                }

                @EventHandler
                fun onBlockBreak(event: BlockBreakEvent) {
                    val player = event.getPlayer()
                    val break_block = event.getBlock()
		    val item =  player.getInventory().getItemInMainHand().getType()
                    if (!isDirt(break_block) || !isShovel(item)){
                        return
                    }
		    for (modY in 0..1){		    
                       for (modX in -1..1){
                           for (modZ in -1..1) {
                               val block = break_block.getRelative(modX, modY ,modZ)
			       getLogger().info(break_block.toString() + ":" + block.getType().toString())
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
