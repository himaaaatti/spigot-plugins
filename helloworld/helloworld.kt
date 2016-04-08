package com.github.himaaaatti.spigot.plugin.helloworld

import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.scheduler.BukkitTask
import org.bukkit.event.world.SpawnChangeEvent
import org.bukkit.World
import org.bukkit.Server
import org.bukkit.event.Listener
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerBedLeaveEvent

class HelloWorld: JavaPlugin() {

    lateinit var world  : World
    lateinit var yoakeTask : BukkitTask
    lateinit var yofukeTask: BukkitTask

    
    override fun onEnable() {


        world = getServer().getWorlds()[0]
        createTasks()

        getServer().getPluginManager().registerEvents(
                object: Listener{
                    @EventHandler
                    fun onChangeSpawn(e: PlayerBedLeaveEvent) {
                        yoakeTask.cancel()
                        yofukeTask.cancel()
                        createTasks()
                    }
                }, this

        )
    }

    fun createTasks() {
        val tick_of_day: Long = 24000
        val current_time = getServer().getWorlds()[0].getTime()

        val YOFUKE_TIME = 13000
        val YOAKE_TIME  = tick_of_day - 180

        var delay_yoake :Long
        var delay_fofuke :Long
        if(current_time < YOFUKE_TIME) {
            delay_fofuke = YOFUKE_TIME - current_time
        } 
        else {
            delay_fofuke = tick_of_day - current_time + YOFUKE_TIME
        }

        if(current_time < YOAKE_TIME) {
            delay_yoake = YOAKE_TIME - current_time
        } 
        else {
            delay_yoake = tick_of_day - current_time + YOAKE_TIME
        }

        val server = getServer()

        yofukeTask = object: BukkitRunnable() {
            override fun run() {
                server.broadcastMessage("日本の夜更けぜよ")
            }
        }.runTaskTimer(this, delay_fofuke, tick_of_day)

        yoakeTask = object: BukkitRunnable() {
            override fun run() {
                server.broadcastMessage("日本の夜明けぜよ")
            }
        }.runTaskTimer(this, delay_yoake, tick_of_day)

    }

}


