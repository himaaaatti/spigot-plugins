package com.github.himaaaatti.spigot.plugin.egg_gacha

import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.event.block.BlockRedstoneEvent
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.Material
import org.bukkit.block.Chest
import org.bukkit.inventory.Inventory
import org.bukkit.block.Block

class EggGacha: JavaPlugin() {


    override fun onEnable() {
        
        val logger = getLogger()
        logger.info("test")

        getServer().getPluginManager().registerEvents(
                object: Listener{
                    @EventHandler
                    fun onChangeSpawn(e: BlockRedstoneEvent) {
                        val block = e.getBlock()
                        if (block.getType() == Material.IRON_BLOCK )
                        {
                            when(searchChest(block)) {
                                CHEST_PLACE.X -> logger.info("X")
                                CHEST_PLACE.Y -> logger.info("Y")
                                CHEST_PLACE.NOT_FOUND -> logger.info("Not found")
                            }

                        }
                    }
                }, this
        )

    }

    enum class CHEST_PLACE {
        NOT_FOUND,
        X,
        Y,
    }

    fun searchChest(block: Block) : CHEST_PLACE {
        var x_p1_block = block.getRelative(1, 0, 0) 
        var x_m1_block = block.getRelative(-1, 0, 0) 

        if(x_p1_block.getType() == Material.CHEST && 
            x_m1_block.getType() == Material.CHEST) 
        {
            getLogger().info("x = 1 & x = -1")
            return CHEST_PLACE.X
        }
        x_p1_block = block.getRelative(0, 0, 1) 
        x_m1_block = block.getRelative(0, 0, -1) 

        if(x_p1_block.getType() == Material.CHEST && 
            x_m1_block.getType() == Material.CHEST) 
        {
            getLogger().info("z = 1 & z = -1")
            return CHEST_PLACE.Y
        }

        getLogger().info("not found")
        return CHEST_PLACE.NOT_FOUND
    }
 
}
