package com.github.himaaaatti.spigot.plugin.garbage_can

import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.event.inventory.InventoryMoveItemEvent
import org.bukkit.event.inventory.InventoryDragEvent
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.block.SignChangeEvent

import org.bukkit.event.Listener
import org.bukkit.event.EventHandler
import org.bukkit.Material
import org.bukkit.block.Chest
import org.bukkit.block.BlockFace
import org.bukkit.block.Sign
import org.bukkit.inventory.Inventory

class GarbageCan: JavaPlugin() {

    fun checkText(sign: Sign) :Boolean {
        val first_text = sign.getLine(0) 
        val last_text = sign.getLine(3)

        getLogger().info(first_text)
        if(first_text == "garbage") {
            sign.setLine(3, "ok[0]")
            return true
        }
        
        if(last_text == "ok[0]") {
            sign.setLine(3, "")
        }
        getLogger().info(first_text)
        return false
    }
    
    override fun onEnable() {

        getServer().getPluginManager().registerEvents(
                object: Listener{

                    fun isGarBageCan(invent: Inventory) : Boolean {

                        val chest: Chest = 
                            when(invent.getHolder()) 
                            {
                                is Chest -> 
                                    invent.getHolder() as Chest

                                    else -> return@isGarBageCan false
                            }

                        val chest_b = chest.getBlock()

                        val check_sign = 
                            fun(face: BlockFace) :Sign?
                            {
                                val face_block = chest_b.getRelative(face)
                                //TODO check is sine attached with me?
                                if(face_block.getType() == Material.WALL_SIGN)
                                {
                                    return face_block.getState() as Sign
                                }
                                else {
                                    return null
                                }
                            }

                        val faces = arrayOf(
                                BlockFace.EAST,
                                BlockFace.WEST,
                                BlockFace.NORTH,
                                BlockFace.SOUTH
                            )

                        var sign: Sign? = null
                        loop@ for(face in faces) {
                            sign = check_sign(face)
                            if(sign != null)
                            {
                                break@loop
                            }
                        }
                        if(sign == null) {
                            return@isGarBageCan false
                        }
                            

                        if(!checkText(sign)) {
                            getLogger().info("sige is inValid")
                            return@isGarBageCan false
                        }

                        return true
                    }

                    @EventHandler
                    fun onChange(e: SignChangeEvent) {
                        val sign = e.getBlock().getState() as Sign
                        getLogger().info("changed")
                        if(checkText(sign)) {
                        getLogger().info("ok")
                            e.setLine(3, "[ok]")
                        }
                        else {
                        getLogger().info("no")
                            e.setLine(3, "")
                        }
                    }


                    @EventHandler
                    fun onMove(e: InventoryMoveItemEvent) {
                        val dest_invent = e.getDestination()
                        if(!isGarBageCan(dest_invent)) {
                            //getLogger().info("is not garbage can")
                            return
                        }
                        dest_invent.clear()
                    }

                    @EventHandler
                    fun onDrag(e: InventoryDragEvent) {
                        val dest_invent = e.getInventory()
                        getLogger().info("DragEvent 1")
                        if(!isGarBageCan(dest_invent)) {
                            getLogger().info("is not garbage can")
                            return
                        }
                        getLogger().info("DragEvent 2")
                        dest_invent.clear()
                    }

                    @EventHandler
                    fun onClick(e: InventoryClickEvent) {
                        val dest_invent = e.getInventory()
                        if(!isGarBageCan(dest_invent)) {
                            getLogger().info("is not garbage can")
                            return
                        }
                        dest_invent.clear()

                    }

                }, this

                

        )
    }
}


