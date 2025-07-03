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

package plugily.projects.murdermystery.commands.arguments.admin;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import plugily.projects.minigamesbox.api.user.IUser;
import plugily.projects.minigamesbox.classic.commands.arguments.data.CommandArgument;
import plugily.projects.minigamesbox.classic.commands.arguments.data.LabelData;
import plugily.projects.minigamesbox.classic.commands.arguments.data.LabeledCommandArgument;
import plugily.projects.minigamesbox.classic.handlers.language.MessageBuilder;
import plugily.projects.murdermystery.arena.role.Role;
import plugily.projects.murdermystery.commands.arguments.ArgumentsRegistry;
import plugily.projects.murdermystery.handlers.language.SetupLanguageHelper;

/**
 * @author Tigerpanzer_02
 * <p>Created at 21.01.2022
 */
public class RolePassArgument {

  public RolePassArgument(ArgumentsRegistry registry) {
    registry.mapArgument("murdermysteryadmin", new LabeledCommandArgument("rolepass", "murdermystery.admin.rolepass", CommandArgument.ExecutorType.BOTH,
      new LabelData(SetupLanguageHelper.getCommandArgumentCommand("RolePass"), 
                   SetupLanguageHelper.getCommandArgumentCommand("RolePass"), 
                   SetupLanguageHelper.getCommandArgumentDescription("RolePass"))) {
      @Override
      public void execute(CommandSender sender, String[] args) {
        if(args.length < 4) {
          new MessageBuilder("&cUsage: /mma rolepass <add/set/remove> <role> <amount> [player]").send(sender);
          return;
        }

        String action = args[1];
        String role = args[2].toUpperCase();
        int amount;
        try {
          amount = Integer.parseInt(args[3]);
        } catch(NumberFormatException e) {
          new MessageBuilder("&cAmount must be a number!").send(sender);
          return;
        }

        Player player;
        if(args.length == 5) {
          player = Bukkit.getPlayerExact(args[4]);
          if(player == null) {
            new MessageBuilder("COMMANDS_PLAYER_NOT_FOUND").asKey().send(sender);
            return;
          }
        } else {
          if(!(sender instanceof Player)) {
            new MessageBuilder("&cYou must specify a player when executing from console!").send(sender);
            return;
          }
          player = (Player) sender;
        }

        IUser user = registry.getPlugin().getUserManager().getUser(player);
        if(role.equalsIgnoreCase("murderer") || role.equalsIgnoreCase("detective")) {
          Role roleEnum = Role.valueOf(role);
          String statistic = "PASS_" + roleEnum.name();

          switch(action.toLowerCase()) {
            case "add":
              user.adjustStatistic(statistic, amount);
              break;
            case "set":
              user.setStatistic(statistic, amount);
              break;
            case "remove":
              user.adjustStatistic(statistic, -amount);
              break;
            default:
              new MessageBuilder("&cInvalid action! Use add, set, or remove.").send(sender);
              return;
          }

          if(roleEnum == Role.MURDERER) {
            new MessageBuilder("IN_GAME_MESSAGES_ARENA_PASS_CHANGE").asKey().player(player).integer(user.getStatistic("PASS_MURDERER")).value(Role.MURDERER.name()).sendPlayer();
          } else {
            new MessageBuilder("IN_GAME_MESSAGES_ARENA_PASS_CHANGE").asKey().player(player).integer(user.getStatistic("PASS_DETECTIVE")).value(Role.DETECTIVE.name()).sendPlayer();
          }

          new MessageBuilder("&aSuccessfully " + action + " " + amount + " " + roleEnum.name() + " passes for " + player.getName()).send(sender);
        } else {
          new MessageBuilder("&cInvalid role! Use murderer or detective.").send(sender);
        }
      }
    });
  }
}
