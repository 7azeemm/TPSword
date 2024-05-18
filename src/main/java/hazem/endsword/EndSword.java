package hazem.endsword;

import hazem.endsword.listeners.onSwordRightClick;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public final class EndSword extends JavaPlugin {

    public int maxDistance = getConfig().getInt("MaxDistance");
    public int cooldown = getConfig().getInt("Cooldown");
    public boolean tpSound = getConfig().getBoolean("TPSound");

    @Override
    public void onEnable() {

        saveDefaultConfig();

        //End Sword recipe registration
        EndSwordRecipe();

        getServer().getPluginManager().registerEvents(new onSwordRightClick(this), this);


    }

    private void EndSwordRecipe() {
        ItemStack Sword = new ItemStack(Material.DIAMOND_SWORD);
        ItemMeta meta = Sword.getItemMeta();

        if (meta == null) {
            System.out.println("error: Diamond Sword meta is null");
            return;
        }
        meta.setDisplayName(ChatColor.DARK_PURPLE + "End Sword");

        ArrayList<String> lore = new ArrayList<String>();
        lore.add(" ");
        lore.add(ChatColor.GOLD + "Ability " + ChatColor.YELLOW + "RIGHT CLICK");
        lore.add(ChatColor.GRAY + "Teleport " + ChatColor.GREEN + maxDistance + " blocks " + ChatColor.GRAY + "ahead of you");

        meta.setLore(lore);
        Sword.setItemMeta(meta);

        NamespacedKey key = new NamespacedKey(this, "EndSword");
        ShapedRecipe EndSwordRecipe = new ShapedRecipe(key, Sword);
        EndSwordRecipe.shape(
                "XXX",
                "XKX",
                "XXX");
        EndSwordRecipe.setIngredient('X', Material.ENDER_PEARL);
        EndSwordRecipe.setIngredient('K', Material.DIAMOND_SWORD);

        Bukkit.addRecipe(EndSwordRecipe);
    }
}
