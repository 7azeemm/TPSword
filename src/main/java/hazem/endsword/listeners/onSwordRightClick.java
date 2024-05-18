package hazem.endsword.listeners;

import hazem.endsword.EndSword;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.*;
import java.util.concurrent.TimeUnit;

import static hazem.endsword.utils.TpUtils.*;

public class onSwordRightClick implements Listener {

    int maxDistance;
    int cooldown;
    boolean TpSound;
    Set<Material> transparentMaterials = new HashSet<>(Arrays.asList(Material.AIR, Material.WATER, Material.LAVA));
    private final Map<UUID, Long> cooldowns = new HashMap<>();

    public onSwordRightClick(EndSword plugin) {
        this.maxDistance = plugin.maxDistance;
        this.cooldown = plugin.cooldown;
        this.TpSound = plugin.tpSound;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        if (e.getAction().name().contains("RIGHT_CLICK") && e.getItem() != null
                && ChatColor.stripColor(Objects.requireNonNull(e.getItem().getItemMeta()).getDisplayName()).equalsIgnoreCase("End Sword")) {
            Player p = e.getPlayer();

            if (cooldowns.containsKey(e.getPlayer().getUniqueId())) {
                if ((System.currentTimeMillis() - cooldowns.get(e.getPlayer().getUniqueId())) < 100) {
                    return;
                }
                if (cooldown != 0) {
                    if ((System.currentTimeMillis() - cooldowns.get(e.getPlayer().getUniqueId())) < cooldown) {
                        p.sendMessage(ChatColor.RED + "This ability is on cooldown for " + TimeUnit.MILLISECONDS.toSeconds(cooldown - (System.currentTimeMillis() - cooldowns.get(e.getPlayer().getUniqueId()))) + "s.");
                        return;
                    }
                }
            }

            if (e.getAction().name().equals("RIGHT_CLICK_BLOCK")) {
                Block block = e.getClickedBlock();
                if (block == null) {
                    System.out.println("EndSword: Block = null");
                    return;
                }
                if (distanceBetweenLocations(getBlockFaceCentre(block.getLocation(), e.getBlockFace()), p.getEyeLocation()) < 1.0) {
                    p.sendMessage(ChatColor.RED + "There are blocks on the way!");
                    return;
                }

                Block TpBlock = SafeTpTo(p, block, e.getBlockFace());
                if (TpBlock == null) {
                    p.sendMessage(ChatColor.RED + "You can't tp there!");
                    return;
                }

                p.teleport(TpBlock.getLocation().add(0.5, 0, 0.5).setDirection(p.getLocation().getDirection()));
                if (TpSound) {
                    p.playSound(p, Sound.ENTITY_ENDERMAN_TELEPORT, 1.0f, 1.0f);
                }
                cooldowns.put(e.getPlayer().getUniqueId(), System.currentTimeMillis());
                return;
            }

            if (e.getAction().name().equals("RIGHT_CLICK_AIR")) {
                Block TpBlock = null;
                for (int i = maxDistance; (i <= maxDistance) && (i >= 0); i--) {
                    Block SafeBlock = AirSafeTP(p, p.getTargetBlock(transparentMaterials, i));
                    if (SafeBlock != null) {
                        TpBlock = SafeBlock;
                        break;
                    }
                }
                if (TpBlock == null) {
                    p.sendMessage(ChatColor.RED + "You can't tp there!");
                    return;
                }

                p.teleport(TpBlock.getLocation().add(0.5, 0, 0.5).setDirection(p.getLocation().getDirection()));
                if (TpSound) {
                    p.playSound(p, Sound.ENTITY_ENDERMAN_TELEPORT, 1.0f, 1.0f);
                }
                cooldowns.put(e.getPlayer().getUniqueId(), System.currentTimeMillis());
            }
        }
    }
}
