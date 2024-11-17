package user.meistertisch.danissmpplugin.essentials.chat;

import io.papermc.paper.chat.ChatRenderer;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import user.meistertisch.danissmpplugin.files.FileTeams;

public class MainChatRenderer implements ChatRenderer {
    @Override
    public Component render(Player source, Component sourceDisplayName, Component message, Audience viewer) {
        Component chatMessage = Component.text("%player%: %message%");

        chatMessage = chatMessage
                .replaceText(TextReplacementConfig.builder().match("%player%").replacement(FileTeams.getTeamNamePrefixComponent(source)).build())
                .replaceText(TextReplacementConfig.builder().match("%message%").replacement(message).build());

        return chatMessage;
    }
}
