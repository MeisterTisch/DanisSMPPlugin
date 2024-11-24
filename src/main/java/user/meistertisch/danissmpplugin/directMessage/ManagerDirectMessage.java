package user.meistertisch.danissmpplugin.directMessage;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.entity.Player;
import user.meistertisch.danissmpplugin.Main;
import user.meistertisch.danissmpplugin.files.FilePlayer;

import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

public class ManagerDirectMessage {
    private static HashMap<Player, Player> replyMap = new HashMap<>();
    private static HashMap<Player, Integer> replyTimer = new HashMap<>();
    private static ScheduledExecutorService service;
    private static ScheduledFuture scheduledFuture;

    public static void setup(){
        service = Executors.newSingleThreadScheduledExecutor();
        scheduledFuture = service.scheduleAtFixedRate(() -> {
            for (Player player : replyTimer.keySet()) {
                if (replyTimer.get(player) > 0) {
                    replyTimer.put(player, replyTimer.get(player) - 1);
                } else {
                    replyMap.remove(player);
                    replyTimer.remove(player);
                }

                if(!replyMap.containsKey(player) && !replyMap.get(player).isOnline()){
                    replyMap.remove(player);
                    replyTimer.remove(player);
                }
            }
        },0,1, java.util.concurrent.TimeUnit.SECONDS);
    }

    public static void shutdown(){
        scheduledFuture.cancel(true);
        service.shutdown();
    }

    public static void sendMessage(Player sender, Player target, String message){
        TextColor primary = TextColor.color(Main.getPrimaryColor());
        TextColor secondary = TextColor.color(Main.getSecondaryColor());

        Component textComponent = Component.text("§l[§r%sender% §l-> §r%target%§l] §t%message%").color(secondary);
        Component senderComponent = Component.text(sender.getName()).color(primary);
        Component targetComponent = Component.text(target.getName()).color(primary);
        ResourceBundle sBundle = ResourceBundle.getBundle("language_" + FilePlayer.getConfig().getString(sender.getName() + ".lang"));
        Component youComponent = Component.text(sBundle.getString("commands.directMessage.you")).color(primary).decorate(TextDecoration.UNDERLINED);
        ResourceBundle tBundle = ResourceBundle.getBundle("language_" + FilePlayer.getConfig().getString(target.getName() + ".lang"));
        Component toYouComponent = Component.text(tBundle.getString("commands.directMessage.toYou")).color(primary).decorate(TextDecoration.UNDERLINED);
        Component messageComponent = Component.text(message).color(NamedTextColor.WHITE);

        replyMap.put(sender, target);
        replyTimer.put(sender, 60*5);

        if(replyMap.containsKey(target)){
            replyMap.put(target, sender);
            replyTimer.put(target, 60*5);
        }

        sender.sendMessage(textComponent
                .replaceText(TextReplacementConfig.builder().match("%sender%").replacement(youComponent).build())
                .replaceText(TextReplacementConfig.builder().match("%target%").replacement(targetComponent).build())
                .replaceText(TextReplacementConfig.builder().match("%message%").replacement(messageComponent).build())
        );

        target.sendMessage(textComponent
                .replaceText(TextReplacementConfig.builder().match("%sender%").replacement(senderComponent).build())
                .replaceText(TextReplacementConfig.builder().match("%target%").replacement(toYouComponent).build())
                .replaceText(TextReplacementConfig.builder().match("%message%").replacement(messageComponent).build())
        );

        target.playSound(target.getLocation(), "minecraft:entity.experience_orb.pickup", 1, 1);
    }

    public static String convertToString(String[] args){
        StringBuilder builder = new StringBuilder();
        for(String arg : args){
            builder.append(arg).append(" ");
        }
        return builder.toString().trim();
    }

    public static Player getReply(Player player){
        return replyMap.get(player);
    }
}
