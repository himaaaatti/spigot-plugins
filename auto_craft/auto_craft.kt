package com.github.himaaaatti.spigot.plugin.auto_craft

import org.bukkit.material.Sign
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.block.BlockFace
import org.bukkit.material.Attachable
import org.bukkit.inventory.ShapedRecipe
import org.bukkit.inventory.Recipe
import org.bukkit.inventory.ItemStack
import org.bukkit.event.block.SignChangeEvent
import org.bukkit.event.block.BlockRedstoneEvent
import org.bukkit.inventory.InventoryHolder
import org.bukkit.Bukkit
import org.bukkit.block.Chest

class AutoCraft: JavaPlugin() {

    val AUTO_CRAFT_TEXT = "auto craft"

    override fun onEnable() {

        getServer().getPluginManager().registerEvents(
            object : Listener {

                @EventHandler
                fun onChange(e: SignChangeEvent)
                {
                    val sign = e.getBlock().getState().getData() as Sign
                    val holder = e.getBlock().getRelative(sign.getAttachedFace())
                    if(holder.getType() != Material.WORKBENCH)
                    {
                        return 
                    }

                    if(e.getLine(0) != AUTO_CRAFT_TEXT) 
                    {
                        return
                    }
                    
                    e.setLine(1,"↑ Recipe")
                    e.setLine(2, "<-Input|Output->");
                    e.setLine(3, "[ok]");
                }


                @EventHandler
                fun onRedStone(e: BlockRedstoneEvent)
                {
                    if(!(e.getOldCurrent() == 0 && e.getNewCurrent() != 0))
                    {
                        return
                    }

                    val pumpkin = e.block.getRelative(0, -1, 0)
                    if(pumpkin.getType() != Material.PUMPKIN)
                    {
                        return 
                    }

                    val w_face = when
                    {
                        pumpkin.getRelative(BlockFace.EAST).getType() == Material.WORKBENCH
                        -> BlockFace.EAST
                        pumpkin.getRelative(BlockFace.WEST).getType() == Material.WORKBENCH
                        -> BlockFace.WEST
                        pumpkin.getRelative(BlockFace.SOUTH).getType() == Material.WORKBENCH
                        -> BlockFace.SOUTH
                        pumpkin.getRelative(BlockFace.NORTH).getType() == Material.WORKBENCH
                        -> BlockFace.NORTH
                        else -> return@onRedStone
                    }

                    val work_bench = pumpkin.getRelative(w_face)

                    val face: BlockFace? = getSignAttachedFace(work_bench)
                    if(face == null)
                    {
                        getLogger().info("getSignAttachedFace")
                        return
                    }

                    val recipe_block = work_bench.getRelative(0, 1, 0)
                    if(recipe_block.getType() != Material.CHEST)
                    {
                        getLogger().info("above block is not CHEST")
                        return
                    }

                    val chest_faces = when(face) {
                        BlockFace.NORTH -> Pair(BlockFace.EAST, BlockFace.WEST)
                        BlockFace.EAST -> Pair(BlockFace.SOUTH, BlockFace.NORTH)
                        BlockFace.SOUTH -> Pair(BlockFace.WEST, BlockFace.EAST)
                        BlockFace.WEST -> Pair(BlockFace.NORTH, BlockFace.SOUTH)
                        else -> return@onRedStone
                    }

                    val source_chest = work_bench.getRelative(chest_faces.first)
                    if(source_chest.getType() != Material.CHEST) 
                    {
                        getLogger().info("left block is not CHEST")
                        return
                    }

                    val dest_chest = work_bench.getRelative(chest_faces.second)
                    if(dest_chest.getType() != Material.CHEST)
                    {
                        getLogger().info("right block is not CHEST")
                        return
                    }

                    val recipe_invent = (recipe_block.getState() as InventoryHolder).getInventory()
                    val item = recipe_invent.getItem(0);
                    if(item == null)
                    {
                        getLogger().info("getItem 0 is empty")
                        return
                    }

                    val source_invent = 
                    (source_chest.getState() as Chest).getBlockInventory()
                    var materials: MutableList<Pair<ItemStack, Int>>? = null
                    for(recipe: Recipe in Bukkit.getRecipesFor(item))
                    {
                        if(recipe is ShapedRecipe)        
                        {
                            val shapes = recipe.getShape()
                            val map :Map<Char, ItemStack> = recipe.getIngredientMap()
                            for(c in shapes[0])
                            {
                                val stack = map.get(c)
                                if(stack == null)
                                {
                                    continue
                                }
                                val type = stack.getType()

                                val index = source_invent.first(type)
                                if(index == -1)
                                {
                                    return  
                                }

                                val ingred_stack = source_invent.getItem(index)
                                if(materials == null)
                                {
                                    materials = mutableListOf(Pair(ingred_stack, index))
                                }
                                else {
                                    materials.add(Pair(ingred_stack, index))
                                }
                            }
                        }
                    }

                    val reduce_stack = fun(m: MutableList<Pair<ItemStack, Int>>?)
                        {
                            if(m == null)
                            {
                                return
                            }

                            for(p in m.listIterator())
                            {
                                if(p.first.getAmount() == 1)
                                {
                                    source_invent.setItem(p.second, null);
                                }
                                else {
                                    p.first.setAmount(p.first.getAmount() - 1)
                                }
                            }
                        }

                    val dest_invent = (dest_chest.getState() as Chest).getBlockInventory()
                    for(stack in dest_invent.iterator())
                    {
                        if(stack == null)
                        {
                            continue
                        }

                        if(stack.getType() != item.getType())
                        {
                            continue
                        }

                        if(stack.getMaxStackSize() == stack.getAmount())
                        {
                            continue
                        }
                        stack.setAmount(stack.getAmount() + 1)
                        reduce_stack(materials)
                        return
                    }

                    val empty_index = dest_invent.firstEmpty()
                    if(empty_index == -1)
                    {
                        return
                    }

                    item.setAmount(1)
                    dest_invent.setItem(empty_index, item)
                    reduce_stack(materials)
                }
            },
            this
        )
    }

    fun getSignAttachedFace(block: Block) : BlockFace?
    {

         val face = when {
            block.getRelative(BlockFace.WEST).getType() == Material.WALL_SIGN
            -> BlockFace.WEST
            block.getRelative(BlockFace.EAST).getType() == Material.WALL_SIGN
            -> BlockFace.EAST
            block.getRelative(BlockFace.SOUTH).getType() == Material.WALL_SIGN
            -> BlockFace.SOUTH
            block.getRelative(BlockFace.NORTH).getType() == Material.WALL_SIGN
            -> BlockFace.NORTH
            else -> return@getSignAttachedFace null
        }

        val sign_block = block.getRelative(face)
        val sign = sign_block.getState() as org.bukkit.block.Sign


        if(sign.getLine(0) != AUTO_CRAFT_TEXT) 
        {
            return null
        }

        val attached_face = (sign.getData() as Attachable).getAttachedFace()
        return when {
            (face == BlockFace.EAST) && (attached_face == BlockFace.WEST)
            -> BlockFace.EAST
            (face == BlockFace.WEST) && (attached_face == BlockFace.EAST) 
            -> BlockFace.WEST
            (face == BlockFace.SOUTH) && (attached_face == BlockFace.NORTH)
            -> BlockFace.SOUTH
            (face == BlockFace.NORTH) && (attached_face == BlockFace.SOUTH)
            -> BlockFace.NORTH
            else -> null
        }

    }
}
