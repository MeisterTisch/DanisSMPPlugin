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
        Component chatMessage = Component.text("%player%: %message%");

        chatMessage = chatMessage
                .replaceText(TextReplacementConfig.builder().match("%player%").replacement(FileTeams.getTeamName(source)).build())
                .replaceText(TextReplacementConfig.builder().match("%message%").replacement(message).build());

        return chatMessage;
    }
}
