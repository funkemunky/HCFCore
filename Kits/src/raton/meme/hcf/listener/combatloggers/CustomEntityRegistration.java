package raton.meme.hcf.listener.combatloggers;

import java.lang.reflect.Field;
import java.util.Map;
import net.minecraft.server.v1_7_R4.EntityTypes;

public class CustomEntityRegistration
{
    public static void registerCustomEntities()
    {
        try
        {
            registerCustomEntity(LoggerEntity.class, "Villager", 120);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public static void registerCustomEntity(Class entityClass, String name, int id)
    {
        setFieldPrivateStaticMap("d", entityClass, name);
        setFieldPrivateStaticMap("f", entityClass, Integer.valueOf(id));
    }

    public static void unregisterCustomEntities() {}

    public static void setFieldPrivateStaticMap(String fieldName, Object key, Object value)
    {
        try
        {
            Field field = EntityTypes.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            Map map = (Map)field.get(null);
            map.put(key, value);
            field.set(null, map);
        }
        catch (NoSuchFieldException|SecurityException|IllegalArgumentException|IllegalAccessException ex)
        {
            ex.printStackTrace();
        }
    }

    public static void setField(String fieldName, Object key, Object value)
    {
        try
        {
            Field field = key.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(key, value);
            field.setAccessible(false);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
