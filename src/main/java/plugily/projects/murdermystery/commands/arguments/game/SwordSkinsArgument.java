/*
 * MurderMystery - Find the murderer, kill him and survive!
 * Copyright (c) 2022  Plugily Projects - maintained by Tigerpanzer_02 and contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package plugily.projects.murdermystery.commands.arguments.game;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import plugily.projects.minigamesbox.api.user.IUser;
import plugily.projects.minigamesbox.classic.commands.arguments.data.CommandArgument;
import plugily.projects.minigamesbox.classic.commands.arguments.data.LabelData;
import plugily.projects.minigamesbox.classic.commands.arguments.data.LabeledCommandArgument;
import plugily.projects.minigamesbox.classic.handlers.language.MessageBuilder;
import plugily.projects.murdermystery.Main;
import plugily.projects.murdermystery.commands.arguments.ArgumentsRegistry;
import plugily.projects.murdermystery.handlers.language.SetupLanguageHelper;
import plugily.projects.murdermystery.handlers.skins.sword.SwordSkin;

/**
 * @author Tigerpanzer_02
 * <p>Created at 21.01.2022
 */
public class SwordSkinsArgument {

  public SwordSkinsArgument(ArgumentsRegistry registry) {
    registry.mapArgument("murdermystery", new LabeledCommandArgument("skins", "murdermystery.skins.use", CommandArgument.ExecutorType.PLAYER,
      new LabelData(SetupLanguageHelper.getCommandArgumentCommand("SwordSkins"), 
                   SetupLanguageHelper.getCommandArgumentCommand("SwordSkins"), 
                   SetupLanguageHelper.getCommandArgumentDescription("SwordSkins"))) {
      @Override
      public void execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        Main plugin = registry.getPlugin();

        if(args.length == 1) {
          player.sendMessage(ChatColor.RED + "Usage: /mm skins <sword> [skinName]");
          return;
        }

        if(args[1].equalsIgnoreCase("sword")) {
          if(args.length == 2) {
            player.sendMessage(ChatColor.GREEN + "Available sword skins: ");
            for(SwordSkin skin : plugin.getSwordSkinManager().getSwordSkins()) {
              String skinName = plugin.getSwordSkinManager().getSkinNameByItemStack(skin.getItemStack());
              if(skinName != null) {
                player.sendMessage(ChatColor.GREEN + "- " + skinName);
              }
            }
            player.sendMessage(ChatColor.GREEN + "Your current sword skin: " + plugin.getSwordSkinManager().getPlayerCurrentSkinName(player));
            return;
          }
          String skinName = args[2];

          if(!player.hasPermission("murdermystery.skins.sword." + skinName)) {
            SwordSkin selectedSkin = plugin.getSwordSkinManager().getSkinByName(skinName);
            if(selectedSkin == null) {
              player.sendMessage(new MessageBuilder("COMMANDS_SWORD_SKINS_SKIN_NOT_FOUND").asKey().value(skinName).build());
              return;
            }

            if(!player.hasPermission("murdermystery.skins.sword." + skinName)) {
              player.sendMessage(new MessageBuilder("COMMANDS_SWORD_SKINS_NO_PERMISSION").asKey().value(skinName).build());
              return;
            }
          }

          IUser user = plugin.getUserManager().getUser(player);
          user.setStatistic("SELECTED_SWORD_SKIN", skinName.hashCode());

          player.sendMessage(new MessageBuilder("COMMANDS_SWORD_SKINS_SKIN_SELECTED").asKey().value(skinName).build());
        }
      }
    });
  }
}
