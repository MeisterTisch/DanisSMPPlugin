package user.meistertisch.danissmpplugin.essentials.chat;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;

public enum ChatColorResolver {
    BLACK("0", NamedTextColor.BLACK),
    DARK_BLUE("1", NamedTextColor.DARK_BLUE),
    DARK_GREEN("2", NamedTextColor.DARK_GREEN),
    DARK_AQUA("3", NamedTextColor.DARK_AQUA),
    DARK_RED("4", NamedTextColor.DARK_RED),
    DARK_PURPLE("5", NamedTextColor.DARK_PURPLE),
    GOLD("6",   NamedTextColor.GOLD),
    GRAY("7", NamedTextColor.GRAY),
    DARK_GRAY("8", NamedTextColor.DARK_GRAY),
    BLUE("9", NamedTextColor.BLUE),
    GREEN("a",  NamedTextColor.GREEN),
    AQUA("b", NamedTextColor.AQUA),
    RED("c", NamedTextColor.RED),
    LIGHT_PURPLE("d", NamedTextColor.LIGHT_PURPLE),
    YELLOW("e", NamedTextColor.YELLOW),
    WHITE("f", NamedTextColor.WHITE);

    private final String code;
    private final NamedTextColor color;

    ChatColorResolver(String code, NamedTextColor color) {
        this.code = code;
        this.color = color;
    }

    public String getCode() {
        return code;
    }

    public NamedTextColor getColor() {
        return color;
    }

    public static Component resolveMessage(Component message) {
        for(ChatColorResolver color : ChatColorResolver.values()) {
            message = message.replaceText(TextReplacementConfig.builder().match("&" + color.getColor()).replacement("" + color.getColor()).build());
        }
        return message;
    }
}
