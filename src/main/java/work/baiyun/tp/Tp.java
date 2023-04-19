package work.baiyun.tp;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public final class Tp extends JavaPlugin {

    @Override
    public void onEnable () {
        Objects.requireNonNull(this.getCommand("tohome")).setExecutor(new TPCmd());
        Objects.requireNonNull(this.getCommand("tobed")).setExecutor(new TPCmd());
        Objects.requireNonNull(this.getCommand("to")).setExecutor(new TPCmd());
        // Plugin startup logic

    }

    @Override
    public void onDisable () {
        // Plugin shutdown logic
    }
    public static class TPCmd implements CommandExecutor {
        // This method is called, when somebody uses our command
        @Override
        public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String lable, String[] args) {
            Player player = (Player) sender;
            ItemStack handitem = player.getInventory().getItemInMainHand();
            switch (cmd.getName()){
                case"tohome":
                    if(handitem.getType().equals(Material.GOLD_INGOT)) {
                        player.getInventory().setItemInMainHand(new ItemStack(Material.GOLD_INGOT, handitem.getAmount() - 1));
                        player.teleport(player.getWorld().getSpawnLocation());
                        player.sendMessage("传送成功！");
                    }else{
                        player.sendMessage("传送失败，需要消耗1枚金锭，你手上拿的不是金锭，或者数量不够！");
                    }

                    return true;
                case"tobed":
                    if(handitem.getType().equals(Material.GOLD_INGOT)) {
                        player.getInventory().setItemInMainHand(new ItemStack(Material.GOLD_INGOT, handitem.getAmount() - 1));
                        player.teleport(player.getBedSpawnLocation());
                        player.sendMessage("传送成功！");
                    }else{
                        player.sendMessage("传送失败，需要消耗1枚金锭，你手上拿的不是金锭，或者数量不够！");
                    }
                    return true;
                case"to":
                    Player target = null;
                    if(args.length==1)
                        for (Player eachplayer : Bukkit.getOnlinePlayers()) {
                            if(eachplayer.getName().equals(args[0]))
                                target=eachplayer;
                        }
                    if(target==null){
                        player.sendMessage("传送失败，无法找到对应的目标！");
                    }else{
                        if(handitem.getType().equals(Material.GOLD_INGOT)&&handitem.getAmount()>=2) {
                            player.getInventory().setItemInMainHand(new ItemStack(Material.GOLD_INGOT, handitem.getAmount() - 2));
                            player.teleport((Entity) target);
                            player.sendMessage("传送成功！");
                        }else{
                            player.sendMessage("传送失败，需要消耗2枚金锭，你手上拿的不是金锭，或者数量不够！");
                        }
                    }
                    return true;
            }
            return false;
        }
    }
}
