package user.meistertisch.danissmpplugin.mobHealthbar;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attributable;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.jetbrains.annotations.NotNull;
import user.meistertisch.danissmpplugin.Main;

public class ListenerMobAttack implements Listener {
    @EventHandler
    public void onMobAttack(EntityDamageByEntityEvent event) {
        if(!Main.getPlugin().getConfig().getBoolean("mobHealthbar.use", true))
            return;

        Entity entity = event.getEntity();
        int maxHealth = (int) ((Attributable) entity).getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
        Component healthComp = getComponent((Damageable) entity, maxHealth, event.getDamage());
        Component maxHealthComp = Component.text(String.valueOf(maxHealth));
        Component name = Component.text("%health%/%maxHealth%")
                .replaceText(TextReplacementConfig.builder().match("%health%").replacement(healthComp).build())
                .replaceText(TextReplacementConfig.builder().match("%maxHealth%").replacement(maxHealthComp).build());

        event.getDamager().sendActionBar(name);
    }

    private static @NotNull Component getComponent(Damageable entity, int maxHealth, double damage) {
        int health = (int) (entity.getHealth() - damage);
        if(health < 0)
            health = 0;

        double procent = (double) health / maxHealth;
        TextColor color;

        if(procent >= 0.9)
            color = TextColor.color(0x00FF00);
        else if(procent >= 0.8)
            color = TextColor.color(0x6bee00);
        else if(procent >= 0.7)
            color = TextColor.color(0x95db00);
        else if(procent >= 0.6)
            color = TextColor.color(0xb0c800);
        else if(procent >= 0.5)
            color = TextColor.color(0xc5b400);
        else if(procent >= 0.4)
            color = TextColor.color(0xd79f00);
        else if(procent >= 0.3)
            color = TextColor.color(0xe68700);
        else if(procent >= 0.2)
            color = TextColor.color(0xf46900);
        else if(procent >= 0.1)
            color = TextColor.color(0xfc4700);
        else
            color = TextColor.color(0xff00000);


        Component healthComp = Component.text(String.valueOf(health)).color(color);
        return healthComp;
    }
}
