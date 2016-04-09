package com.github.himaaaatti.spigot.plugin.egg_gacha

import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.event.block.BlockRedstoneEvent
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.Material
import org.bukkit.block.Chest
import org.bukkit.inventory.Inventory
import org.bukkit.block.Block
import org.bukkit.material.Button
import org.bukkit.inventory.ItemStack

import java.util.Random

//import org.bukkit.command.Command;
//import org.bukkit.command.CommandSender;

class EggGacha: JavaPlugin() {

    //TODO redstone, wood_button, leverなどのしょりを追加

    val rand = Random()

    override fun onEnable() {
        
        val logger = getLogger()

        getServer().getPluginManager().registerEvents(
                object: Listener{
                    @EventHandler
                    fun onReceiveRedStonePower(e: BlockRedstoneEvent) 
                    {
                        if(e.getOldCurrent() >= e.getNewCurrent()) {
                            return
                        }
                        val block = e.getBlock()

                        if(block.getType() != Material.STONE_BUTTON) {
                            return
                        }
                        val button = block.getState().getData() as Button
                        val attached = block.getRelative(button.getAttachedFace())

                        if (attached.getType() != Material.PUMPKIN)
                        {
                            return
                        }
                        
                        val receive_chest_block = attached.getRelative(0, 1, 0)
                        if(receive_chest_block.getType() != Material.CHEST)
                        {
                            return
                        }

                        val send_chest_block :Block? = searchChest(attached)
                        if(send_chest_block != null) {
                            
                            // get egg from receive_chest
                            logger.info("found both chest")
                            //TODO 
                            val r_chest = receive_chest_block.getState() as Chest
                            val r_invent = r_chest.getBlockInventory()
                            val first_slot: Int = r_invent.first(Material.EGG)

                            logger.info(first_slot.toString())
                            if(first_slot == -1) {
                                return
                            }

                            val item_stack = r_invent.getItem(first_slot)
                            item_stack.setAmount(item_stack.getAmount() - 1)
                            //TODO 最後の一つの時にsetAmount(0)とやっても亡くならない

                            // set item to send_chest
                            val s_chest = send_chest_block.getState() as Chest
                            val s_invent = s_chest.getBlockInventory()
                            val first_empty = s_invent.firstEmpty()
                            if(first_empty == -1) {
                                //item_stack.setAmount(item_stack.getAmount() + 1)
                                return
                            }

                            s_invent.setItem(first_empty, getRandomItem())

                        }

                    }
                }, this
        )

    }

/*    override fun onCommand(sender: CommandSender,
                    cmd: Command,
                    label: String,
                    args: Array<String>) : Boolean
    {
        return false
    }
    */

    fun getRandomItem() :ItemStack {
        val mat_array = Material.values()

        val ran_index = rand.nextInt(mat_array.size)

        return ItemStack(mat_array[ran_index])
    }

    fun searchChest(block: Block) : Block? {
        for(x in -1..1) {
            for(z in -1..1) {
                val chest = block.getRelative(x, 0, z) 
                if(chest.getType() == Material.CHEST) {
                    return chest
                }
            }
        }
        return null
    }
 
}
