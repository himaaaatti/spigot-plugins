package com.github.himaaaatti.spigot.plugin.garbage_can

import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.event.inventory.InventoryMoveItemEvent
import org.bukkit.event.inventory.InventoryDragEvent
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.block.SignChangeEvent

import org.bukkit.event.Listener
import org.bukkit.event.EventHandler
import org.bukkit.Material
import org.bukkit.block.Chest
import org.bukkit.block.BlockFace
import org.bukkit.block.Sign
import org.bukkit.inventory.Inventory

class GarbageCan: JavaPlugin() {

    val GARBAGE_TEXT = "garbage"

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
                            

                        if(sign.getLine(0) != GARBAGE_TEXT)
                        {
                            return@isGarBageCan false
                        }

                        return true
                    }

                    @EventHandler
                    fun onChange(e: SignChangeEvent) {
                        if(e.getLine(0) == GARBAGE_TEXT){
                            e.setLine(3, "[ok]")
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

//                    @EventHandler
//                    fun onDrag(e: InventoryDragEvent) {
//                        val dest_invent = e.getInventory()
//                        if(!isGarBageCan(dest_invent)) {
//                            return
//                        }
//                        dest_invent.clear()
//                    }

                    @EventHandler
                    fun onOpen(e: InventoryOpenEvent) {
                        val dest_invent = e.getInventory()
                        if(!isGarBageCan(dest_invent))
                        {
                            return
                        }
                        //TODO set timer
                    }

                    @EventHandler
                    fun onClose(e: InventoryCloseEvent) {
                        val dest_invent = e.getInventory()
                        if(!isGarBageCan(dest_invent))
                        {
                            return
                        }
                        dest_invent.clear();
                    }

                }, this

                

        )
    }
}


