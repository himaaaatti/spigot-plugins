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
import org.bukkit.material.Attachable
import org.bukkit.material.Pumpkin
import org.bukkit.block.BlockFace
import java.util.Random

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

class EggGacha: JavaPlugin() {
    //TODO redstone, wood_buttonなどのしょりを追加
    //TODO refoctor

    val rand = Random()

    override fun onEnable() {
        
        getServer().getPluginManager().registerEvents(
                object: Listener{
                    @EventHandler
                    fun onReceiveRedStonePower(e: BlockRedstoneEvent) 
                    {
                        //getLogger().info("block red stone event")
                        if(e.getOldCurrent() >= e.getNewCurrent()) {
                            return
                        }

                        val pumpkin: Block? = getPumpkinBlock(e.getBlock())
                        if (pumpkin == null)
                        {
                            getLogger().info("cannot found pumpkin")
                            return
                        }
                     getLogger().info(
                                String.format("%d, %d, %d",
                                        pumpkin.getX(),
                                        pumpkin.getY(),
                                        pumpkin.getZ()))

                        val pump = pumpkin.getState().getData() as Pumpkin
                        var face = pump.getFacing()
                        
                        getLogger().info(face.toString())
                        face = when(face) {
                            BlockFace.EAST -> BlockFace.WEST
                            BlockFace.WEST -> BlockFace.EAST
                            BlockFace.NORTH -> BlockFace.SOUTH
                            BlockFace.SOUTH -> BlockFace.NORTH
                            else -> null
                        }
                        if(face == null) {
                            return 
                        }
                        //val receive_chest : Block? = getFaceBlock(pumpkin, face)
                        val receive_chest : Block? = pumpkin.getRelative(face)
                        if(receive_chest == null) 
                        {
                            return 
                        }

                        getLogger().info(
                                String.format("%d, %d, %d",
                                        receive_chest.getX(),
                                        receive_chest.getY(),
                                        receive_chest.getZ()))


                        val send_chest :Block? = searchSendChest(pumpkin, face)
                        if(send_chest == null) 
                        {
                            return
                        }

                        getLogger().info("found both chest")
                        getLogger().info(
                                String.format("%d, %d, %d",
                                        send_chest.getX(),
                                        send_chest.getY(),
                                        send_chest.getZ()))
                        //TODO 
                        val r_chest = receive_chest.getState() as Chest
                        val r_invent = r_chest.getBlockInventory()
                        val first_slot: Int = r_invent.first(Material.EGG)

                        getLogger().info(first_slot.toString())
                        if(first_slot == -1) 
                        {
                            getLogger().info("egg not found")
                            return
                        }

                        val item_stack = r_invent.getItem(first_slot)
                        var amount = item_stack.getAmount() - 1
                        if (amount == 0) {
                            r_invent.setItem(first_slot, null)
                        } else {
                            item_stack.setAmount(amount)
                        }

                        // set item to send_chest
                        val s_chest = send_chest.getState() as Chest
                        val s_invent = s_chest.getBlockInventory()
                        val first_empty = s_invent.firstEmpty()
                        if(first_empty == -1) {
                            item_stack.setAmount(item_stack.getAmount() + 1)
                            getLogger().info("send chest have not empty")
                            return
                        }

                        s_invent.setItem(first_empty, getRandomItem())

                        /*
                        if(receive_chest_block == null) 
                        {
                            getLogger().info("cannot found receive chest")
                            return
                        }

                        val send_chest_block = pumpkin.getRelative(0, -1, 0)
                        if(send_chest_block.getType() != Material.CHEST)
                        {
                            getLogger().info("cannot found send chest")
                            return
                        }

                        // get egg from receive_chest
                        getLogger().info("found both chest")
                        //TODO 
                        val r_chest = receive_chest_block.getState() as Chest
                        val r_invent = r_chest.getBlockInventory()
                        val first_slot: Int = r_invent.first(Material.EGG)

                        getLogger().info(first_slot.toString())
                        if(first_slot == -1) {
                            getLogger().info("egg not found")
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
                            item_stack.setAmount(item_stack.getAmount() + 1)
                            getLogger().info("send chest have not empty")
                            return
                        }

                        s_invent.setItem(first_empty, getRandomItem())
                        */
                    }
                }, this
        )
    }

    override fun onCommand(sender: CommandSender,
                    cmd: Command,
                    label: String,
                    args: Array<String>) : Boolean
    {
        getLogger().info("hello world by command")
        return true
//        return false
    }

    fun getRandomItem() :ItemStack {
        val mat_array = Material.values()

        val ran_index = rand.nextInt(mat_array.size)

        return ItemStack(mat_array[ran_index])
    }

    fun getPumpkinBlock(block: Block): Block? {

        when(block.getType()) {
            Material.STONE_BUTTON,
            Material.WOOD_BUTTON,
            Material.LEVER
                -> 
            {
                val button = block.getState().getData() as Attachable

                val attached = block.getRelative(button.getAttachedFace())

                if(attached.getType() != Material.PUMPKIN) {
                    return null
                }
                getLogger().info(
                                String.format("attached %d, %d, %d",
                                        attached.getX(),
                                        attached.getY(),
                                        attached.getZ()))

                return attached
            }
            Material.REDSTONE_WIRE
                ->
            {
                val b = block.getRelative(0, -1, 0)
                if(b.getType() != Material.PUMPKIN) {
                    return null
                }

                return b
            }

            else -> {
                return null
            }
        }
    }

    fun getFaceBlock(block: Block, face: BlockFace) : Block?{
//        val pump = block.getState().getData() as Pumpkin
//        val face = pump.getFacing()
//        getLogger().info(pump.getFacing().toString())

        getLogger().info(face.toString())
        return when(face) {
            BlockFace.EAST -> block.getRelative(1, 0, 0)
            BlockFace.WEST -> block.getRelative(-1, 0, 0)
            BlockFace.SOUTH -> block.getRelative(0, 0, 1)
            BlockFace.NORTH -> block.getRelative(0, 0, -1)
            else -> null
        }
    }

    fun searchSendChest(block: Block, face: BlockFace) : Block? {

        getLogger().info(face.toString())
        fun check_chest(fix: BlockFace): Boolean {
            val a = fix != face
            getLogger().info(a.toString())
            return fix != face && block.getRelative(fix).getType() == Material.CHEST
        }

        when {
            check_chest(BlockFace.EAST)
                ->
            {
                getLogger().info("EAST")
                return block.getRelative(BlockFace.EAST)
            }
            check_chest(BlockFace.WEST)
                ->
            {
                getLogger().info("W")
                return block.getRelative(BlockFace.WEST)
            }
            check_chest(BlockFace.SOUTH)
                ->
            {
                getLogger().info("S")
                return block.getRelative(BlockFace.SOUTH)
            }
            check_chest(BlockFace.NORTH)
                ->
            {
                getLogger().info("N")
                return block.getRelative(BlockFace.NORTH)
            }
            else -> return null
        }
    }
 
}
