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

//import org.bukkit.command.Command;
//import org.bukkit.command.CommandSender;
//TODO add command. 

class EggGacha: JavaPlugin() {
    //TODO refoctor

    val rand = Random()
    val EggMaterials: List<Material> = listOf (
            Material.ACACIA_DOOR ,
            Material.ACACIA_DOOR_ITEM ,
            Material.ACACIA_FENCE ,
            Material.ACACIA_FENCE_GATE ,
            Material.ACACIA_STAIRS ,
//            Material.ACTIVATOR_RAIL ,
//            Material.AIR ,
//            Material.ANVIL ,
            Material.APPLE ,
            Material.ARMOR_STAND ,
            Material.ARROW ,
            Material.BAKED_POTATO ,
            Material.BANNER ,
//            Material.BARRIER ,
//            Material.BEACON ,
            Material.BED ,
            Material.BED_BLOCK ,
//            Material.BEDROCK ,
            Material.BEETROOT ,
            Material.BEETROOT_BLOCK ,
            Material.BEETROOT_SEEDS ,
            Material.BEETROOT_SOUP ,
            Material.BIRCH_DOOR ,
            Material.BIRCH_DOOR_ITEM ,
            Material.BIRCH_FENCE ,
            Material.BIRCH_FENCE_GATE ,
            Material.BIRCH_WOOD_STAIRS ,
//            Material.BLAZE_POWDER ,
//            Material.BLAZE_ROD ,
            Material.BOAT ,
            Material.BOAT_ACACIA ,
            Material.BOAT_BIRCH ,
            Material.BOAT_DARK_OAK ,
            Material.BOAT_JUNGLE ,
            Material.BOAT_SPRUCE ,
            Material.BONE ,
            Material.BOOK ,
            Material.BOOK_AND_QUILL ,
            Material.BOOKSHELF ,
            Material.BOW ,
            Material.BOWL ,
//            Material.BREAD ,
//            Material.BREWING_STAND ,
//            Material.BREWING_STAND_ITEM ,
            Material.BRICK ,
            Material.BRICK_STAIRS ,
            Material.BROWN_MUSHROOM ,
            Material.BUCKET ,
//            Material.BURNING_FURNACE ,
            Material.CACTUS ,
            Material.CAKE ,
            Material.CAKE_BLOCK ,
            Material.CARPET ,
            Material.CARROT ,
//            Material.CARROT_ITEM ,
//            Material.CARROT_STICK ,
//            Material.CAULDRON ,
//            Material.CAULDRON_ITEM ,
//            Material.CHAINMAIL_BOOTS ,
//            Material.CHAINMAIL_CHESTPLATE ,
//            Material.CHAINMAIL_HELMET ,
//            Material.CHAINMAIL_LEGGINGS ,
            Material.CHEST ,
//            Material.CHORUS_FLOWER ,
//            Material.CHORUS_FRUIT ,
//            Material.CHORUS_FRUIT_POPPED ,
//            Material.CHORUS_PLANT ,
            Material.CLAY ,
            Material.CLAY_BALL ,
            Material.CLAY_BRICK ,
            Material.COAL ,
            Material.COAL_BLOCK ,
            Material.COAL_ORE ,
            Material.COBBLE_WALL ,
            Material.COBBLESTONE ,
            Material.COBBLESTONE_STAIRS ,
            Material.COCOA ,
//            Material.COMMAND ,
//            Material.COMMAND_CHAIN ,
//            Material.COMMAND_MINECART ,
//            Material.COMMAND_REPEATING ,
            Material.COMPASS ,
            Material.COOKED_BEEF ,
            Material.COOKED_CHICKEN ,
            Material.COOKED_FISH ,
            Material.COOKED_MUTTON ,
            Material.COOKED_RABBIT ,
            Material.COOKIE ,
//            Material.CROPS ,
            Material.DARK_OAK_DOOR ,
            Material.DARK_OAK_DOOR_ITEM ,
            Material.DARK_OAK_FENCE ,
            Material.DARK_OAK_FENCE_GATE ,
            Material.DARK_OAK_STAIRS ,
//            Material.DAYLIGHT_DETECTOR ,
//            Material.DAYLIGHT_DETECTOR_INVERTED ,
//            Material.DEAD_BUSH ,
//            Material.DETECTOR_RAIL ,
//            Material.DIAMOND ,
//            Material.DIAMOND_AXE ,
//            Material.DIAMOND_BARDING ,
//            Material.DIAMOND_BLOCK ,
//            Material.DIAMOND_BOOTS ,
//            Material.DIAMOND_CHESTPLATE ,
//            Material.DIAMOND_HELMET ,
//            Material.DIAMOND_HOE ,
//            Material.DIAMOND_LEGGINGS ,
//            Material.DIAMOND_ORE ,
//            Material.DIAMOND_PICKAXE ,
//            Material.DIAMOND_SPADE ,
//            Material.DIAMOND_SWORD ,
//            Material.DIODE ,
//            Material.DIODE_BLOCK_OFF ,
//            Material.DIODE_BLOCK_ON ,
            Material.DIRT ,
            Material.DISPENSER ,
            Material.DOUBLE_PLANT ,
            Material.DOUBLE_STEP ,
            Material.DOUBLE_STONE_SLAB2 ,
//            Material.DRAGON_EGG ,
//            Material.DRAGONS_BREATH ,
            Material.DROPPER ,
            Material.EGG ,
//            Material.ELYTRA ,
            Material.EMERALD ,
            Material.EMERALD_BLOCK ,
            Material.EMERALD_ORE ,
            Material.EMPTY_MAP ,
//            Material.ENCHANTED_BOOK ,
//            Material.ENCHANTMENT_TABLE ,
 //           Material.END_BRICKS ,
 //           Material.END_CRYSTAL ,
 //           Material.END_GATEWAY ,
 //           Material.END_ROD ,
 //           Material.ENDER_CHEST ,
 //           Material.ENDER_PEARL ,
 //           Material.ENDER_PORTAL ,
 //           Material.ENDER_PORTAL_FRAME ,
 //           Material.ENDER_STONE ,
            Material.EXP_BOTTLE ,
            Material.EXPLOSIVE_MINECART ,
//            Material.EYE_OF_ENDER ,
            Material.FEATHER ,
            Material.FENCE ,
            Material.FENCE_GATE ,
            Material.FERMENTED_SPIDER_EYE ,
//            Material.FIRE ,
//            Material.FIREBALL ,
            Material.FIREWORK ,
            Material.FIREWORK_CHARGE ,
            Material.FISHING_ROD ,
            Material.FLINT ,
            Material.FLINT_AND_STEEL ,
            Material.FLOWER_POT ,
            Material.FLOWER_POT_ITEM ,
            Material.FROSTED_ICE ,
            Material.FURNACE ,
//            Material.GHAST_TEAR ,
            Material.GLASS ,
            Material.GLASS_BOTTLE ,
//            Material.GLOWING_REDSTONE_ORE ,
            Material.GLOWSTONE ,
            Material.GLOWSTONE_DUST ,
//            Material.GOLD_AXE ,
//            Material.GOLD_BARDING ,
//            Material.GOLD_BLOCK ,
//            Material.GOLD_BOOTS ,
//            Material.GOLD_CHESTPLATE ,
//            Material.GOLD_HELMET ,
//            Material.GOLD_HOE ,
//            Material.GOLD_INGOT ,
//            Material.GOLD_LEGGINGS ,
//            Material.GOLD_NUGGET ,
//            Material.GOLD_ORE ,
//            Material.GOLD_PICKAXE ,
//            Material.GOLD_PLATE ,
//            Material.GOLD_RECORD ,
//            Material.GOLD_SPADE ,
//            Material.GOLD_SWORD ,
//            Material.GOLDEN_APPLE ,
//            Material.GOLDEN_CARROT ,
//            Material.GRASS ,
//            Material.GRASS_PATH ,
            Material.GRAVEL ,
            Material.GREEN_RECORD ,
            Material.GRILLED_PORK ,
            Material.HARD_CLAY ,
//            Material.HAY_BLOCK ,
            Material.HOPPER ,
//            Material.HOPPER_MINECART ,
//            Material.HUGE_MUSHROOM_1 ,
//            Material.HUGE_MUSHROOM_2 ,
            Material.ICE ,
            Material.INK_SACK ,
            Material.IRON_AXE ,
            Material.IRON_BARDING ,
            Material.IRON_BLOCK ,
            Material.IRON_BOOTS ,
            Material.IRON_CHESTPLATE ,
            Material.IRON_DOOR ,
            Material.IRON_DOOR_BLOCK ,
            Material.IRON_FENCE ,
            Material.IRON_HELMET ,
            Material.IRON_HOE ,
            Material.IRON_INGOT ,
            Material.IRON_LEGGINGS ,
            Material.IRON_ORE ,
            Material.IRON_PICKAXE ,
            Material.IRON_PLATE ,
            Material.IRON_SPADE ,
            Material.IRON_SWORD ,
            Material.IRON_TRAPDOOR ,
            Material.ITEM_FRAME ,
            Material.JACK_O_LANTERN ,
//            Material.JUKEBOX ,
            Material.JUNGLE_DOOR ,
            Material.JUNGLE_DOOR_ITEM ,
            Material.JUNGLE_FENCE ,
            Material.JUNGLE_FENCE_GATE ,
            Material.JUNGLE_WOOD_STAIRS ,
            Material.LADDER ,
            Material.LAPIS_BLOCK ,
            Material.LAPIS_ORE ,
//            Material.LAVA ,
//            Material.LAVA_BUCKET ,
            Material.LEASH ,
            Material.LEATHER ,
            Material.LEATHER_BOOTS ,
            Material.LEATHER_CHESTPLATE ,
            Material.LEATHER_HELMET ,
            Material.LEATHER_LEGGINGS ,
            Material.LEAVES ,
            Material.LEAVES_2 ,
            Material.LEVER ,
            Material.LINGERING_POTION ,
            Material.LOG ,
            Material.LOG_2 ,
            Material.LONG_GRASS ,
            Material.MAGMA_CREAM ,
            Material.MAP ,
            Material.MELON ,
            Material.MELON_BLOCK ,
            Material.MELON_SEEDS ,
            Material.MELON_STEM ,
            Material.MILK_BUCKET ,
            Material.MINECART ,
//            Material.MOB_SPAWNER ,
//            Material.MONSTER_EGG ,
//            Material.MONSTER_EGGS ,
            Material.MOSSY_COBBLESTONE ,
            Material.MUSHROOM_SOUP ,
            Material.MUTTON ,
            Material.MYCEL ,
            Material.NAME_TAG ,
 //           Material.NETHER_BRICK ,
 //           Material.NETHER_BRICK_ITEM ,
 //           Material.NETHER_BRICK_STAIRS ,
 //           Material.NETHER_FENCE ,
 //           Material.NETHER_STALK ,
 //           Material.NETHER_STAR ,
 //           Material.NETHER_WARTS ,
 //           Material.NETHERRACK ,
            Material.NOTE_BLOCK ,
            Material.OBSIDIAN ,
            Material.PACKED_ICE ,
            Material.PAINTING ,
            Material.PAPER ,
            Material.PISTON_BASE ,
            Material.PISTON_EXTENSION ,
            Material.PISTON_MOVING_PIECE ,
            Material.PISTON_STICKY_BASE ,
            Material.POISONOUS_POTATO ,
            Material.PORK ,
            Material.PORTAL ,
            Material.POTATO ,
            Material.POTATO_ITEM ,
            Material.POTION ,
//           Material.POWERED_MINECART ,
//            Material.POWERED_RAIL ,
//            Material.PRISMARINE ,
//            Material.PRISMARINE_CRYSTALS ,
//            Material.PRISMARINE_SHARD ,
            Material.PUMPKIN ,
            Material.PUMPKIN_PIE ,
            Material.PUMPKIN_SEEDS ,
            Material.PUMPKIN_STEM ,
            Material.PURPUR_BLOCK ,
            Material.PURPUR_DOUBLE_SLAB ,
            Material.PURPUR_PILLAR ,
            Material.PURPUR_SLAB ,
            Material.PURPUR_STAIRS ,
//            Material.QUARTZ ,
//            Material.QUARTZ_BLOCK ,
//            Material.QUARTZ_ORE ,
//            Material.QUARTZ_STAIRS ,
            Material.RABBIT ,
            Material.RABBIT_FOOT ,
            Material.RABBIT_HIDE ,
            Material.RABBIT_STEW ,
//            Material.RAILS ,
            Material.RAW_BEEF ,
            Material.RAW_CHICKEN ,
            Material.RAW_FISH ,
//            Material.RECORD_10 ,
//            Material.RECORD_11 ,
//            Material.RECORD_12 ,
//            Material.RECORD_3 ,
//            Material.RECORD_4 ,
//            Material.RECORD_5 ,
//            Material.RECORD_6 ,
//            Material.RECORD_7 ,
//            Material.RECORD_8 ,
//            Material.RECORD_9 ,
            Material.RED_MUSHROOM ,
            Material.RED_ROSE ,
            Material.RED_SANDSTONE ,
            Material.RED_SANDSTONE_STAIRS ,
            Material.REDSTONE ,
            Material.REDSTONE_BLOCK ,
            Material.REDSTONE_COMPARATOR ,
            Material.REDSTONE_COMPARATOR_OFF ,
            Material.REDSTONE_COMPARATOR_ON ,
            Material.REDSTONE_LAMP_OFF ,
            Material.REDSTONE_LAMP_ON ,
            Material.REDSTONE_ORE ,
            Material.REDSTONE_TORCH_OFF ,
            Material.REDSTONE_TORCH_ON ,
            Material.REDSTONE_WIRE ,
            Material.ROTTEN_FLESH ,
            Material.SADDLE ,
            Material.SAND ,
            Material.SANDSTONE ,
            Material.SANDSTONE_STAIRS ,
            Material.SAPLING ,
//            Material.SEA_LANTERN ,
            Material.SEEDS ,
            Material.SHEARS ,
            Material.SHIELD ,
            Material.SIGN ,
            Material.SIGN_POST ,
            Material.SKULL ,
            Material.SKULL_ITEM ,
            Material.SLIME_BALL ,
            Material.SLIME_BLOCK ,
            Material.SMOOTH_BRICK ,
            Material.SMOOTH_STAIRS ,
            Material.SNOW ,
            Material.SNOW_BALL ,
            Material.SNOW_BLOCK ,
            Material.SOIL ,
            Material.SOUL_SAND ,
            Material.SPECKLED_MELON ,
            Material.SPECTRAL_ARROW ,
            Material.SPIDER_EYE ,
            Material.SPLASH_POTION ,
//            Material.SPONGE ,
            Material.SPRUCE_DOOR ,
            Material.SPRUCE_DOOR_ITEM ,
            Material.SPRUCE_FENCE ,
            Material.SPRUCE_FENCE_GATE ,
            Material.SPRUCE_WOOD_STAIRS ,
            Material.STAINED_CLAY ,
            Material.STAINED_GLASS ,
            Material.STAINED_GLASS_PANE ,
            Material.STANDING_BANNER ,
            Material.STATIONARY_LAVA ,
            Material.STATIONARY_WATER ,
            Material.STEP ,
            Material.STICK ,
            Material.STONE ,
            Material.STONE_AXE ,
            Material.STONE_BUTTON ,
            Material.STONE_HOE ,
            Material.STONE_PICKAXE ,
            Material.STONE_PLATE ,
            Material.STONE_SLAB2 ,
            Material.STONE_SPADE ,
            Material.STONE_SWORD ,
            Material.STORAGE_MINECART ,
            Material.STRING ,
            Material.STRUCTURE_BLOCK ,
            Material.SUGAR ,
            Material.SUGAR_CANE ,
            Material.SUGAR_CANE_BLOCK ,
            Material.SULPHUR ,
            Material.THIN_GLASS ,
            Material.TIPPED_ARROW ,
 //           Material.TNT ,
            Material.TORCH ,
            Material.TRAP_DOOR ,
            Material.TRAPPED_CHEST ,
            Material.TRIPWIRE ,
            Material.TRIPWIRE_HOOK ,
            Material.VINE ,
            Material.WALL_BANNER ,
            Material.WALL_SIGN ,
            Material.WATCH ,
            Material.WATER ,
            Material.WATER_BUCKET ,
            Material.WATER_LILY ,
            Material.WEB ,
            Material.WHEAT ,
            Material.WOOD ,
            Material.WOOD_AXE ,
            Material.WOOD_BUTTON ,
            Material.WOOD_DOOR ,
            Material.WOOD_DOUBLE_STEP ,
            Material.WOOD_HOE ,
            Material.WOOD_PICKAXE ,
            Material.WOOD_PLATE ,
            Material.WOOD_SPADE ,
            Material.WOOD_STAIRS ,
            Material.WOOD_STEP ,
            Material.WOOD_SWORD ,
            Material.WOODEN_DOOR ,
            Material.WOOL ,
            Material.WORKBENCH ,
            Material.WRITTEN_BOOK ,
            Material.YELLOW_FLOWER 
    )

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
//                        var first_slot: Int = -1 //= r_invent.first(Material.EGG)

                        val slot_item : Material? = when {
                            r_invent.first(Material.EGG) != -1 
                                -> Material.EGG
                            else -> null
                        }

                        if(slot_item == null) { 
                            return 
                        }

                        val first_slot = r_invent.first(Material.EGG)
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
                            item_stack.setAmount(item_stack.getAmount() + 1)
                            getLogger().info("send chest have not empty")
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

        val mat_array = when(inItem) {
            Material.EGG -> EggMaterials
            else -> null
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
