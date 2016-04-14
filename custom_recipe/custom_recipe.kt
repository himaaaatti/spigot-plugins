package com.github.himaaaatti.spigot.plugin.custom_recipe

import org.bukkit.plugin.java.JavaPlugin

import org.bukkit.Material
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.FurnaceRecipe
class CustomRecipe: JavaPlugin() {
    override fun onEnable() {
        val cray_recipe: ShapedRecipe = ShapedRecipe(ItemStack(Material.CLAY, 1))
        cray_recipe.shape("DSD", "SDS", "DSD")
        cray_recipe.setIngredient('D', Material.DIRT)
        cray_recipe.setIngredient('S', Material.SAND)


        val bukkit2iron = FurnaceRecipe(ItemStack(Material.IRON_INGOT, 2 ), Material.BUCKET)
        val boots2iron = FurnaceRecipe(ItemStack(Material.IRON_INGOT, 3 ), Material.IRON_BOOTS)
        val chestplate2iron = FurnaceRecipe(ItemStack(Material.IRON_INGOT, 7 ), Material.IRON_CHESTPLATE)
        val helmet2iron = FurnaceRecipe(ItemStack(Material.IRON_INGOT, 4 ), Material.IRON_HELMET)
        val leggings2iron = FurnaceRecipe(ItemStack(Material.IRON_INGOT, 6 ), Material.IRON_LEGGINGS)


        getServer().addRecipe(cray_recipe)
        getServer().addRecipe(bukkit2iron)
        getServer().addRecipe(boots2iron)
        getServer().addRecipe(chestplate2iron)
        getServer().addRecipe(helmet2iron)
        getServer().addRecipe(leggings2iron)
    }
}
