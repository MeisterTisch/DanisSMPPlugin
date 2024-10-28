package user.meistertisch.danissmpplugin.essentials.chat;

import io.papermc.paper.chat.ChatRenderer;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.apache.maven.model.Resource;
import org.bukkit.Color;
import org.bukkit.entity.Player;
import user.meistertisch.danissmpplugin.Main;
import user.meistertisch.danissmpplugin.files.FilePlayer;
import user.meistertisch.danissmpplugin.files.FileTeams;

import java.util.Locale;
import java.util.ResourceBundle;

public class MainChatRenderer implements ChatRenderer {
    @Override
    public Component render(Player source, Component sourceDisplayName, Component message, Audience viewer) {
        boolean isTeam = FilePlayer.getConfig().getBoolean(source.getName() + ".isTeam");
        String teamName;
        Color teamColor;
        TextDecoration teamDecoration;
        Component chatMessage = Component.text("");

        if(isTeam){
            teamName = FilePlayer.getConfig().getString(source.getName() + ".team");
            teamColor = FileTeams.getConfig().getColor(teamName + ".color");
            teamDecoration = TextDecoration.valueOf(FileTeams.getConfig().getString(teamName + ".decoration").toUpperCase(Locale.ROOT));
            Component teamNameComp = Component.text("[").color(TextColor.color(teamColor.asRGB())).decorate(TextDecoration.BOLD)
                    .append(Component.text(teamName).color(TextColor.color(teamColor.asRGB())).decorate(TextDecoration.BOLD).decorate(teamDecoration))
                    .append(Component.text("]").color(TextColor.color(teamColor.asRGB())).decorate(TextDecoration.BOLD));

            chatMessage = Component.text("%team% %player%: %message%");
            chatMessage = chatMessage
                    .replaceText(TextReplacementConfig.builder().match("%team%").replacement(teamNameComp).build())
                    .replaceText(TextReplacementConfig.builder().match("%player%").replacement(source.getName()).build())
                    .replaceText(TextReplacementConfig.builder().match("%message%").replacement(message).build());
        } else {
            chatMessage = chatMessage
                    .append(source.name().decorate(TextDecoration.BOLD))
                    .append(Component.text(": ")
                    .append(message)
                    );
        }

        return chatMessage;
    }
}
