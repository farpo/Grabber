package eu.ansquare.grabber;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
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

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        if(label.equalsIgnoreCase("camplacer")){
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
        getServer().getPluginManager().registerEvents(new EntityListener(), this);
        getServer().getPluginManager().registerEvents(this, this);
    }
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event){
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        if(item.hasItemMeta()){
            im = new ItemManager();
            if(im.isGrabber(item)){

            }
        }
    }
}
