package ru.entryset.item;

import java.lang.reflect.Field;
import java.util.UUID;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.SkullMeta;
import ru.entryset.tools.MenuFiles;

public class ItemSkull extends Item {

	private String value;
	
	public ItemSkull(Player p, String value, FileConfiguration y) {
		super(p, value, y);
		
		SkullMeta meta = (SkullMeta) getMeta();
		load(value, y, meta);
		setItemMeta(meta);
	}
	
	@SuppressWarnings("deprecation")
	private void load(String value, FileConfiguration y, SkullMeta meta) {
		if(y.contains(value + ".Value")) {
			this.value = MenuFiles.PAPI(getPlayer(), y.getString(value + ".Value"));
			char[] c = getValue().toCharArray();
			if(c.length < 17) {
				meta.setOwner(getValue());
				return;
			}
			GameProfile profile = new GameProfile(UUID.randomUUID(), null);
            profile.getProperties().put("textures", new Property("textures"
            		, y.getString(value + ".Value").replace(" ", "")));
            Class<?> headMetaClass = meta.getClass();
            try {
              getField(headMetaClass, "profile", GameProfile.class, 0).set(meta, profile);
            } catch (IllegalArgumentException e) {
              e.printStackTrace();
            } catch (IllegalAccessException e) {
              e.printStackTrace();
            } 
		}
	}
	
	private <T> Field getField(Class<?> target, String name, Class<T> fieldType, int index) {
        for (Field field : target.getDeclaredFields()) {
          if ((name == null || field.getName().equals(name)) && fieldType.isAssignableFrom(field.getType()) && index-- <= 0) {
            field.setAccessible(true);
            return field;
          } 
        } 
        if (target.getSuperclass() != null)
          return getField(target.getSuperclass(), name, fieldType, index); 
        throw new IllegalArgumentException("Cannot find field with type " + fieldType);
    }
	
	public String getValue() {
		return this.value;
	}

}
