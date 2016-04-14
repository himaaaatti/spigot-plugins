package com.github.himaaaatti.spigot.plugin.planter

import org.bukkit.plugin.java.JavaPlugin

import org.bukkit.Material

import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.scheduler.BukkitTask

import org.bukkit.event.Listener
import org.bukkit.event.EventHandler
import org.bukkit.event.block.SignChangeEvent

import org.bukkit.material.Sign
//import org.bukkit.block.Sign

class Planter: JavaPlugin() {
    
    val CHECK_INTERVAL: Long = 1000

    lateinit var check_task: BukkitTask

    override fun onEnable() {

        

        getServer().getPluginManager().registerEvents(
            object: Listener{

                @EventHandler
                fun onChange(e: SignChangeEvent) {
                    val sign = e.getBlock().getState().getData() as Sign

                    val chest = e.getBlock().getRelative(sign.getAttachedFace())
                    if(chest.getType() != Material.CHEST) {
                        return
                    }

                    val first_line = e.getLine(0) //sign.getLine(0)
                    //getLogger().info(first_line)
                    if(first_line != "planter")
                    {
                        return
                    }
                    //getLogger().info("planter")
                    e.setLine(1, "[ok]")

                    //WIP
                    // add chest data to task
                }

            }, this
        )

        check_task = object: BukkitRunnable() {
            override fun run() {

            }
        }.runTaskTimer(this, 0, CHECK_INTERVAL)
    }

    override fun onDisable() {
        check_task.cancel()
    }

    
}
