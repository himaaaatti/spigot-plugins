package com.github.himaaaatti.spigot.plugin.planter

import org.bukkit.plugin.java.JavaPlugin

import org.bukkit.Material

import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.scheduler.BukkitTask

import org.bukkit.event.Listener
import org.bukkit.event.EventHandler
import org.bukkit.event.block.SignChangeEvent
import org.bukkit.event.block.BlockBreakEvent
//import org.bukkit.event.inventory.InventoryMoveItemEvent
//import org.bukkit.event.inventory.InventoryDragEvent
//import org.bukkit.event.inventory.InventoryCloseEvent

import org.bukkit.block.Chest
import org.bukkit.block.BlockFace
//import org.bukkit.block.Sign

import org.bukkit.material.Sign
import org.bukkit.material.Attachable


class Planter: JavaPlugin() {
    
    val CHECK_INTERVAL: Long = 1000
    val PLANTER_TEXT = "planter"

    lateinit var check_task: BukkitTask

    override fun onEnable() {

        val plugin = this

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
                    if(first_line != PLANTER_TEXT)
                    {
                        return
                    }
                    //getLogger().info("planter")
                    e.setLine(1, "[ok]")


                    //WIP
                    // add chest data to task
                }

                @EventHandler
                fun onBreak(e: BlockBreakEvent) {
                    
                    val current_block = e.getBlock()
                    val below_block = current_block.getRelative(0, -1, 0)
                    if(below_block.getType() != Material.DIRT) 
                    {
//                        getLogger().info("not dirt")
                        return 
                    }

                    when(current_block.getType())
                    {
                        Material.LOG,
                        Material.LOG_2,
                        Material.SAPLING
                            -> {}
                        else -> {
//                            getLogger().info("not log")
                            return@onBreak
                        }
                    }

                    if(below_block.getRelative(0, -1, 0).getType() != Material.AIR)
                    {
//                        getLogger().info("not air")
                        return
                    }
                    
                    val chest = below_block.getRelative(0, -2, 0)
                    if(chest.getType() != Material.CHEST) 
                    {
//                        getLogger().info("not chest")
                        return
                    }

                    val attached_sign = fun(face: BlockFace) : Boolean{
//                        getLogger().info(chest.getRelative(face).getType().toString())
                        if(chest.getRelative(face).getType() != Material.WALL_SIGN) 
                        {
//                            getLogger().info("not sign")
                            return false
                        }

                        val sign = chest.getRelative(face).getState() 
                            as org.bukkit.block.Sign

                        if(sign.getLine(0) != PLANTER_TEXT)
                        {
//                            getLogger().info("wrong text")
                            return false 
                        }

                        val attach = sign.getData() as Attachable

                        val attached_face = attach.getAttachedFace()
                        return when {
                            (face == BlockFace.EAST) && (attached_face == BlockFace.WEST)
                                -> true
                            (face == BlockFace.WEST) && (attached_face == BlockFace.EAST) 
                                -> true
                            (face == BlockFace.SOUTH) && (attached_face == BlockFace.NORTH)
                                -> true
                            (face == BlockFace.NORTH) && (attached_face == BlockFace.SOUTH)
                                -> true
                            else -> false
                        }
                    }

                    when {
                        attached_sign(BlockFace.EAST) -> {}
                        attached_sign(BlockFace.WEST) -> {}
                        attached_sign(BlockFace.SOUTH) -> {}
                        attached_sign(BlockFace.NORTH) -> {}
                        else -> 
                            {
//                                getLogger().info("attached")

                                return@onBreak
                            }
                    }

                    val inventory = (chest.getState() as Chest).getInventory()
                    val index = inventory.first(Material.SAPLING)
                    if(index == -1)
                    {
//                        getLogger().info("not found SAPLING")
                        return
                    }

                    val sapling_stack = inventory.getItem(index)
                    val amount = sapling_stack.getAmount() - 1

                    if(amount == 0) 
                    {
                        inventory.setItem(index, null)
                    }
                    else {
                        sapling_stack.setAmount(amount)
                    }

                     
                    object: BukkitRunnable() {
                        override fun run() {
                            current_block.setType(Material.SAPLING)
                        }
                    }.runTaskLater(plugin, 20)

//                    getLogger().info("condition clear!")
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
