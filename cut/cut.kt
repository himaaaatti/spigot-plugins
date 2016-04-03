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
                    Material.LOG, Material.LOG_2 -> true
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
                    if (!isLog(woodlog)) {
                        return
                    }
                    val player = event.getPlayer()
                    for (modY in 0..1) {
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
