package eu.ansquare.grabber;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class ItemManager {
    public void giveGrabber(Player player){
        NamespacedKey key = new NamespacedKey("grabber", "is-grabber");
        ItemStack item = new ItemStack(Material.GOLDEN_HORSE_ARMOR, 1);
        ItemMeta meta = item.getItemMeta();
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "yes");
        item.setItemMeta(meta);
        player.getInventory().addItem(item);
    }
    public boolean isGrabber(ItemStack item){
        NamespacedKey key = new NamespacedKey("grabber", "is-grabber");
        PersistentDataContainer container = item.getItemMeta().getPersistentDataContainer();
        if (container.has(key, PersistentDataType.STRING)){
            String valueString = container.get(key, PersistentDataType.STRING);
            if(valueString.equalsIgnoreCase("yes")){
                return true;
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }
    }
}
