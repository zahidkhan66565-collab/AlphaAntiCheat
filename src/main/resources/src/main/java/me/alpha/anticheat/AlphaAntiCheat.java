package me.alpha.anticheat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class AlphaAntiCheat extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
        getLogger().info("AlphaAntiCheat Enabled!");
    }

    private void alert(Player player, String hack) {
        String msg = ChatColor.RED + "[AlphaAC] "
                + ChatColor.YELLOW + player.getName()
                + " suspected of "
                + ChatColor.RED + hack;

        Bukkit.getConsoleSender().sendMessage(msg);

        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.isOp()) {
                p.sendMessage(msg);
            }
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        e.getPlayer().sendMessage(ChatColor.GREEN + "Protected by AlphaAntiCheat");
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();

        // Simple Speed Check
        if (p.getVelocity().length() > 1.2 && !p.isFlying()) {
            alert(p, "Speed Hack");
        }

        // Simple Fly Check
        if (!p.isOnGround() && !p.getAllowFlight() && p.getFallDistance() == 0) {
            alert(p, "Fly Hack");
        }
    }
}
