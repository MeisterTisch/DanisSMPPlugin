package user.meistertisch.danissmpplugin.level;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.entity.Player;
import user.meistertisch.danissmpplugin.files.FilePlayer;
import user.meistertisch.danissmpplugin.level.types.LevelType;

import java.util.ResourceBundle;

public class MessageLevelUp {
    public MessageLevelUp(Player player, LevelType levelType, int levelInt){
        ResourceBundle bundle = ResourceBundle.getBundle("language_" + FilePlayer.getConfig().getString(player.getName() + ".lang"));

        TextComponent level = Component.text(levelInt, NamedTextColor.GREEN);
        TextComponent type = Component.text(bundle.getString("level." + levelType.toString().toLowerCase()), levelType.getColor())
                .decoration(TextDecoration.BOLD, true)
                .hoverEvent(Component.text(bundle.getString("level.levelUp.hover")))
                .clickEvent(ClickEvent.runCommand("/level"));

        Component text = Component.text(bundle.getString("level.levelUp"))
                .color(TextColor.color(NamedTextColor.DARK_GREEN))
                .replaceText(TextReplacementConfig.builder().match("%level%").replacement(level).build())
                .replaceText(TextReplacementConfig.builder().match("%leveltype%").replacement(type).build());


        Component openText = Component.text(bundle.getString("level.levelUp.openReward"))
                .color(TextColor.color(NamedTextColor.DARK_GREEN))
                .decoration(TextDecoration.ITALIC, true)
                .hoverEvent(Component.text(bundle.getString("level.levelUp.openReward.hover")))
                .clickEvent(ClickEvent.runCommand("/level reward " + levelType.name().toLowerCase()));

        player.sendMessage(text);
        player.sendMessage(openText);
    }
}
