package user.meistertisch.danissmpplugin.tpa;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.Player;
import user.meistertisch.danissmpplugin.Main;
import user.meistertisch.danissmpplugin.files.FilePlayer;

import java.util.HashMap;
import java.util.ResourceBundle;

public class ManagerTPA {
    private HashMap<Player, Player> requests;
    private HashMap<Player, Boolean> toRequesterMap;

    public ManagerTPA() {
        requests = new HashMap<>();
        toRequesterMap = new HashMap<>();
    }

    public void addRequest(Player requester, Player target, boolean toRequester) {
        ResourceBundle requesterBundle = ResourceBundle.getBundle("language_" + FilePlayer.getConfig().getString(requester.getName() + ".lang"));
        ResourceBundle targetBundle = ResourceBundle.getBundle("language_" + FilePlayer.getConfig().getString(target.getName() + ".lang"));

        if(!FilePlayer.getConfig().getBoolean(target.getName() + ".tpa")){
            requester.sendMessage(
                    Component.text(requesterBundle.getString("commands.tpa.targetDisallowing")).color(NamedTextColor.RED)
                            .replaceText(TextReplacementConfig.builder().match("%target%")
                                    .replacement(Component.text(target.getName()).color(NamedTextColor.GOLD)).build())
            );
            return;
        }

        if(requests.containsKey(requester)){
            requester.sendMessage(
                    Component.text(requesterBundle.getString("commands.tpa.alreadyRequested")).color(NamedTextColor.RED)
                            .replaceText(TextReplacementConfig.builder().match("%target%")
                                    .replacement(Component.text(requester.getName()).color(NamedTextColor.GOLD)).build())
            );
            return;
        }

        requests.put(requester, target);
        toRequesterMap.put(requester, toRequester);

        Component requestSent = Component.text(requesterBundle.getString("commands.tpa.requestSent")).color(NamedTextColor.GREEN)
                .replaceText(TextReplacementConfig.builder().match("%target%")
                        .replacement(Component.text(target.getName()).color(NamedTextColor.GOLD)).build());

        Component toRequesterMessage = Component.text(targetBundle.getString("commands.tpa.toRequester.requestGot")).color(TextColor.color(Main.getPrimaryColor()))
                .replaceText(TextReplacementConfig.builder().match("%requester%")
                        .replacement(Component.text(target.getName()).color(TextColor.color(Main.getSecondaryColor()))).build());

        Component toTargetMessage = Component.text(targetBundle.getString("commands.tpa.toTarget.requestGot")).color(TextColor.color(Main.getPrimaryColor()))
                .replaceText(TextReplacementConfig.builder().match("%requester%")
                        .replacement(Component.text(target.getName()).color(TextColor.color(Main.getSecondaryColor()))).build());

        requester.sendMessage(requestSent);
        target.sendMessage(toRequester ? toRequesterMessage : toTargetMessage);

        Component accept = Component.text("[").append(Component.text(targetBundle.getString("tpa.accept")))
                .append(Component.text("]")).color(NamedTextColor.GREEN)
                .clickEvent(ClickEvent.runCommand("/tpa accept"));
        Component decline = Component.text("[").append(Component.text(targetBundle.getString("tpa.decline")))
                .append(Component.text("]")).color(NamedTextColor.RED)
                .clickEvent(ClickEvent.runCommand("/tpa decline"));

        target.sendMessage(accept.append(Component.text(" ")).append(decline));
    }

    public void cancelRequest(Player requester){
        ResourceBundle requesterBundle = ResourceBundle.getBundle("language_" + FilePlayer.getConfig().getString(requester.getName() + ".lang"));

        if(requests.containsKey(requester)){
            Player target = requests.get(requester);
            ResourceBundle targetBundle = ResourceBundle.getBundle("language_" + FilePlayer.getConfig().getString(target.getName() + ".lang"));

            requests.remove(requester);
            toRequesterMap.remove(requester);

            Component requestCancelled = Component.text(requesterBundle.getString("commands.tpa.cancelled")).color(NamedTextColor.RED)
                    .replaceText(TextReplacementConfig.builder().match("%target%")
                            .replacement(Component.text(target.getName()).color(NamedTextColor.GOLD)).build());

            Component targetRequestCancelled = Component.text(targetBundle.getString("commands.tpa.cancelledTarget")).color(NamedTextColor.RED)
                    .replaceText(TextReplacementConfig.builder().match("%requester%")
                            .replacement(Component.text(requester.getName()).color(NamedTextColor.GOLD)).build());

            requester.sendMessage(requestCancelled);
            target.sendMessage(targetRequestCancelled);
        } else {
            requester.sendMessage(
                    Component.text(requesterBundle.getString("commands.tpa.cancelNoRequest")).color(NamedTextColor.RED)
            );
        }
    }

    public void accept(Player target) {
        ResourceBundle targetBundle = ResourceBundle.getBundle("language_" + FilePlayer.getConfig().getString(target.getName() + ".lang"));

        Player requester = null;

        for(Player player : requests.keySet()){
            if(requests.get(player) == target){
                requester = player;
                break;
            }
        }

        if(requester == null){
            target.sendMessage(
                    Component.text(targetBundle.getString("commands.tpa.noRequest")).color(NamedTextColor.RED)
            );
            return;
        }

        ResourceBundle requesterBundle = ResourceBundle.getBundle("language_" + FilePlayer.getConfig().getString(requester.getName() + ".lang"));

        if(toRequesterMap.get(requester)){
            target.teleport(requester);
        } else {
            requester.teleport(target);
        }

        requests.remove(requester);
        toRequesterMap.remove(requester);

        Component requestAccepted = Component.text(targetBundle.getString("commands.tpa.acceptedTarget")).color(NamedTextColor.DARK_GREEN)
                .replaceText(TextReplacementConfig.builder().match("%target%")
                        .replacement(Component.text(requester.getName()).color(NamedTextColor.GREEN)).build());

        Component requesterAccepted = Component.text(requesterBundle.getString("commands.tpa.accepted")).color(NamedTextColor.GREEN);

        target.sendMessage(requestAccepted);
        requester.sendMessage(requesterAccepted);
    }

    public void decline(Player target) {
        ResourceBundle targetBundle = ResourceBundle.getBundle("language_" + FilePlayer.getConfig().getString(target.getName() + ".lang"));

        Player requester = null;

        for(Player player : requests.keySet()){
            if(requests.get(player) == target){
                requester = player;
                break;
            }
        }

        if(requester == null){
            target.sendMessage(
                    Component.text(targetBundle.getString("commands.tpa.noRequest")).color(NamedTextColor.RED)
            );
            return;
        }

        ResourceBundle requesterBundle = ResourceBundle.getBundle("language_" + FilePlayer.getConfig().getString(requester.getName() + ".lang"));

        requests.remove(requester);
        toRequesterMap.remove(requester);

        Component requestDeclined = Component.text(targetBundle.getString("commands.tpa.declinedTarget")).color(NamedTextColor.RED)
                .replaceText(TextReplacementConfig.builder().match("%target%")
                        .replacement(Component.text(requester.getName()).color(NamedTextColor.GOLD)).build());

        Component requesterDeclined = Component.text(requesterBundle.getString("commands.tpa.declined")).color(NamedTextColor.RED);

        target.sendMessage(requestDeclined);
        requester.sendMessage(requesterDeclined);
    }
}
