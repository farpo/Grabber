package eu.ansquare.grabber;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Grabber extends JavaPlugin implements Listener {

    static Grabber instance;

    ItemManager im;
    EntityGrabber eg;
    EntityListener el;
    Map<Player, Entity> entityMap;
    List<Entity> invulnerableEntityList;
    Map<Player, Integer> distanceMap;
    long lastInteractionTime;
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        if(label.equalsIgnoreCase("grabber")){
            im.giveGrabber((Player) sender);
            return true;
        }
        return false;
    }

    @Override
    public void onEnable(){
        instance = this;
        im = new ItemManager();
        eg = new EntityGrabber();
        el = new EntityListener();
        entityMap = new HashMap<>();
        invulnerableEntityList = new ArrayList<>();
        distanceMap = new HashMap<>();
        getServer().getPluginManager().registerEvents(new EntityListener(), this);
        getServer().getPluginManager().registerEvents(this, this);
        lastInteractionTime =0L;
    }
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event){
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        if(item.hasItemMeta()){
            im = new ItemManager();
            if(im.isGrabber(item)){
                long now = System.currentTimeMillis();
                if(event.getAction().isRightClick() && (now - lastInteractionTime) > 100){
                    if(!entityMap.containsKey(player)){
                        eg.grabEntity(player);
                        event.setCancelled(true);
                    }
                    else {
                        eg.letGo(player);
                        event.setCancelled(true);
                    }
                    lastInteractionTime = System.currentTimeMillis();
                }
            }
        }
    }
    @EventHandler
    public void onPlayerHeldItem(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();
        if (entityMap.containsKey(player)) {
            int prevSlot = event.getPreviousSlot();
            int nowSlot = event.getNewSlot();
            if (prevSlot == 0 && nowSlot == 8) {
                modifyDistance(player, 1);
            } else if (prevSlot == 8 && nowSlot == 0) {
                modifyDistance(player, -1);
            } else if (prevSlot <= nowSlot) {
                modifyDistance(player, -1);
            } else if (prevSlot >= nowSlot) {
                modifyDistance(player, 1);
            }
            event.setCancelled(true);
        }
    }
    void modifyDistance(Player player, int modifier){
        int previousDistance = distanceMap.get(player);
        distanceMap.put(player, previousDistance + modifier);
        el.moveEntity(player, entityMap.get(player));
    }
}
