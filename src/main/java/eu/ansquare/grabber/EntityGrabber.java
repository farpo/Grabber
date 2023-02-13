package eu.ansquare.grabber;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.util.RayTraceResult;

import javax.annotation.Nullable;
import java.util.function.Predicate;

public class EntityGrabber {
    public void grabEntity(Player player){
        @Nullable Predicate<Entity> filter = new Predicate<Entity>() {
            @Override
            public boolean test(Entity entity) {
                if(entity.getType() == EntityType.PLAYER){
                    return false;
                }
                else {
                    return true;
                }
            }
        };
        RayTraceResult trace = player.getWorld().rayTraceEntities(player.getEyeLocation(), player.getLocation().getDirection(), 128, 1, filter);
        if(trace != null){
            Entity entity = trace.getHitEntity();
            if(!Grabber.instance.entityMap.containsValue(entity)){
                Grabber.instance.entityMap.put(player, entity);
                player.sendMessage(entity.getType().toString() + "grabbed");
                Grabber.instance.invulnerableEntityList.add(entity);
            }
            else {
                player.sendMessage("Entity already grabbed");
            }
        }
    }
    public void letGo(Player player){
        Entity entity = Grabber.instance.entityMap.get(player);
        player.sendMessage("lettting go of" + entity.getType().toString());
        Grabber.instance.entityMap.remove(player);
        Grabber.instance.invulnerableEntityList.remove(entity);
    }
}
