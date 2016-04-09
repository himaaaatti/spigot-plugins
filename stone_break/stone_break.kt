package com.github.himaaaatti.spigot.plugin.stone_break

import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.command.CommandSender
import org.bukkit.command.Command
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.plugin.java.JavaPlugin

class  Stone_Break: JavaPlugin() {
    var flag = false
    
    override fun onEnable() {
        getServer().getPluginManager().registerEvents(
            object : Listener {
                fun isStone(block: Block) = when (block.getType()) {
                    Material.STONE -> true
                    else -> false
                }

		fun isPickaxe(type: Material) = when(type) {
		    Material.STONE_PICKAXE -> true
		    else -> false
		}

                fun breakBlock(block: Block, player: Player) {
                    getServer().getScheduler().scheduleSyncDelayedTask(
                        this@Stone_Break,
                        object : Runnable {
                            override fun run() {
                                if (player.isValid() && isStone(block)) {
				    block.breakNaturally()
                                }
                            }
                        },
                        10
                    )
                }

                @EventHandler
                fun onBlockBreak(event: BlockBreakEvent) {
		    if(!flag){
			return
		    }
                    val player = event.getPlayer()
                    val break_block = event.getBlock()
		    val item =  player.getInventory().getItemInMainHand().getType()
                    if (!isStone(break_block) || !isPickaxe(item)){
                        return
                    }
		    for (modY in 0..1){		    
                       for (modX in -1..1){
                           for (modZ in -1..1) {
                               val block = break_block.getRelative(modX, modY ,modZ)
			       breakBlock(block, player)
                           }
	               }
		    }
		}
		},
            this
        )
    }

    override fun onCommand(sender: CommandSender, 
    	     	           cmd: Command, 
			   commandLabel: String, 
			   args: Array<String>): Boolean
	     {
    	     if(cmd.getName() == "sb"){
	        if(args.size == 0){
		    flag = !flag
		}else if(args.size == 1){
		    when (args[0]){
		        "on" -> flag = true
			"off" -> flag = false
			"status" ->  { if(flag ==true){
				           sender.sendMessage("stone_break: on")
				 	}else {
					   sender.sendMessage("stone_break: off")
					}
				      }
			else -> {}
		    }
		}
		return true
	     }
	     
	     return false
    }
}
