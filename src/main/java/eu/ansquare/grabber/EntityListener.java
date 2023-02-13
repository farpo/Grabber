package eu.ansquare.grabber;

import io.papermc.paper.event.entity.EntityMoveEvent;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

public class EntityListener implements Listener {
    @EventHandler
    public void onEntityDamage(EntityDamageEvent event){
        Entity entity = event.getEntity();
        if(Grabber.instance.invulnerableEntityList.contains(entity)){
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void onEntityMove(EntityMoveEvent event){
        Entity entity = event.getEntity();
        if(Grabber.instance.entityMap.containsValue(entity)){
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event){
        Player player = event.getPlayer();
        if(Grabber.instance.entityMap.containsKey(player)){
            moveEntity(player, Grabber.instance.entityMap.get(player));
        }
    }
    void moveEntity(Player player, Entity entity){
        if(!Grabber.instance.distanceMap.containsKey(player)){
            Grabber.instance.distanceMap.put(player, 5);
        }
        Location playerLoc = player.getEyeLocation();
        Vector playerDir = playerLoc.getDirection().normalize().multiply(Grabber.instance.distanceMap.get(player));
        Location newLoc = playerLoc.add(playerDir);
        entity.teleport(newLoc);
    }
}
