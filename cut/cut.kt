package com.github.himaaaatti.spigot.plugin.cut

import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.plugin.java.JavaPlugin

class Cut: JavaPlugin() {
    override fun onEnable() {
        getServer().getPluginManager().registerEvents(
            object : Listener {
                fun isLog(block: Block) = when (block.getType()) {
                    Material.LOG, 
                    Material.LOG_2,
                    Material.LEAVES,
                    Material.LEAVES_2
                    -> true
                    else -> false
                }
                
                fun isAxe(type: Material) = when(type) {
                    Material.DIAMOND_AXE,
                    Material.GOLD_AXE,
                    Material.IRON_AXE,
                    Material.STONE_AXE,
                    Material.WOOD_AXE   -> true
                    else -> false
                }

                fun breakBlock(block: Block, player: Player) {
                    getServer().getScheduler().scheduleSyncDelayedTask(
                        this@Cut,
                        object : Runnable {
                            override fun run() {
                                if (player.isValid() && isLog(block)) {
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
                    val woodlog = event.getBlock()
                    val player = event.getPlayer()
                    if (!isLog(woodlog) 
                        || !isAxe(player.getInventory().getItemInMainHand().getType())) 
                    {
                        return
                    }

                    for (modY in -1..1) {
                        for (modX in -1..1) {
                            for (modZ in -1..1) {
                                val block = woodlog.getRelative(modX, modY, modZ)
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
