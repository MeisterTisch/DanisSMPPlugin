package user.meistertisch.danissmpplugin.level;

import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import user.meistertisch.danissmpplugin.files.FilePlayer;
import user.meistertisch.danissmpplugin.level.drumroll.InventoryDrumroll;
import user.meistertisch.danissmpplugin.level.invs.InventoryLevelsStart;
import user.meistertisch.danissmpplugin.level.types.LevelType;
import user.meistertisch.danissmpplugin.level.types.adventure.RewardsLevelingAdventure;

import java.util.List;
import java.util.ResourceBundle;

public class CommandLeveling implements TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(commandSender instanceof Player player){
            switch (strings.length){
                case 0 -> {
                    new InventoryLevelsStart(player);
                }
                case 1 ->{
//                    RewardsLevelingAdventure reward = RewardsLevelingAdventure.getNextItem();
//                    ItemStack item = new ItemStack(reward.getMaterial(), reward.getAmount());
//                    ItemMeta meta = item.getItemMeta();
//                    ResourceBundle bundle = ResourceBundle.getBundle("language_" + FilePlayer.getConfig().getString(player.getName() + ".lang"));
//
//                    meta.displayName(Component.text(bundle.getString(reward.getName())));
//                    meta.lore(List.of(Component.text(bundle.getString(reward.getDescription()))));
//                    item.setItemMeta(meta);
//                    player.getInventory().addItem(item);

                    new InventoryDrumroll(player, LevelType.ADVENTURE);
                }
            }
            return true;
        } else {

        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        return List.of();
    }
}
