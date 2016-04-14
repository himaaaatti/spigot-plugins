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
import java.util.LinkedList
//import org.bukkit.command.Command;
//import org.bukkit.command.CommandSender;
//TODO add command. 

class EggGacha: JavaPlugin() {
    //TODO refoctor

    val rand = Random()
    //var eggMaterials: LinkedList<Material>? = null
    var eggMaterials: LinkedList<Material> = LinkedList() 

    override fun onEnable() {

        this.saveDefaultConfig()

        val config = this.getConfig()
        var isEgg = config.getBoolean("egg.is_enable")

        val eggMList = if(isEgg) 
        {
            config.getList("egg.materials") 
        } else {
            null
        }

        if(eggMList == null) {
            isEgg = false
        } 
        else {
            eggMaterials = LinkedList()
            for(m in eggMList) {

                val mt =  Material.valueOf(m as String)
                eggMaterials.add(mt)
                //eggMaterials.add(Material.getMaterial(m))
            }
        }

        
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
//                            getLogger().info("cannot found pumpkin")
                            return
                        }
 //                       getLogger().info(
 //                               String.format("%d, %d, %d",
 //                                       pumpkin.getX(),
 //                                       pumpkin.getY(),
 //                                       pumpkin.getZ()))

                        val pump = pumpkin.getState().getData() as Pumpkin
                        var face = pump.getFacing()
                        
//                        getLogger().info(face.toString())
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
                        val send_chest : Block? = pumpkin.getRelative(face)
                        if(send_chest == null) 
                        {
                            return 
                        }

//                        getLogger().info(
//                                String.format("%d, %d, %d",
//                                        receive_chest.getX(),
//                                        receive_chest.getY(),
//                                        receive_chest.getZ()))


                        val receive_chest :Block? = searchSendChest(pumpkin, face)
                        if(receive_chest == null) 
                        {
                            return
                        }

                        getLogger().info("found both chest")
//                        getLogger().info(
 //                               String.format("%d, %d, %d",
 //                                       send_chest.getX(),
 //                                       send_chest.getY(),
 //                                       send_chest.getZ()))
                        //TODO 
                        val r_chest = receive_chest.getState() as Chest
                        val r_invent = r_chest.getBlockInventory()
//                        var first_slot: Int = -1 //= r_invent.first(Material.EGG)

                        val slot_item : Material? = when {
                            isEgg && (r_invent.first(Material.EGG) != -1)
                                -> Material.EGG
                            else -> null
                        }

                        if(slot_item == null) { 
                            getLogger().info("Item is null")
                            return 
                        }

                        //val first_slot = r_invent.first(Material.EGG)
                        val first_slot = r_invent.first(ItemStack(slot_item, 16))
                        getLogger().info(first_slot.toString())
                        if(first_slot == -1) 
                        {
                            getLogger().info("egg not found")
                            return
                        }

                        //TODO change egg consumption
                        val item_stack = r_invent.getItem(first_slot)
                        var amount = item_stack.getAmount()
                        if(amount != 16) {
                            return 
                        }

                        //if (amount == 0) {
                        r_invent.setItem(first_slot, null)
                        //} else {
                            //item_stack.setAmount(amount)
                        //}

                        // set item to send_chest
                        val s_chest = send_chest.getState() as Chest
                        val s_invent = s_chest.getBlockInventory()
                        val first_empty = s_invent.firstEmpty()
                        if(first_empty == -1) {
//                            item_stack.setAmount(item_stack.getAmount() + 1)
//                            getLogger().info("send chest have not empty")
                            return
                        }

                        s_invent.setItem(first_empty, getRandomItem(slot_item))
                    }
                }, this
        )
    }

//    override fun onCommand(sender: CommandSender,
//                    cmd: Command,
//                    label: String,
//                    args: Array<String>) : Boolean
//    {
//        getLogger().info("hello world by command")
//        return true
//    }

    fun getRandomItem(inItem: Material) :ItemStack {

        var mat_array = when(inItem) {
            Material.EGG -> eggMaterials
            else -> null
        }

        if(mat_array?.size == 0) {
            mat_array = null
        }

        if(mat_array == null) {
            return ItemStack(Material.EGG)
        }

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
