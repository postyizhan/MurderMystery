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

package plugily.projects.murdermystery.handlers.language;

import plugily.projects.murdermystery.Main;

/**
 * @author Your Name
 * <p>
 * Helper class for accessing setup menu language strings
 */
public class SetupLanguageHelper {

    private static SetupLanguageManager languageManager;

    /**
     * Initialize the language helper with the plugin instance
     *
     * @param plugin The main plugin instance
     */
    public static void init(Main plugin) {
        languageManager = new SetupLanguageManager(plugin);
    }

    /**
     * Get a string from the language file
     *
     * @param path Path to the string
     * @return String from the language file
     */
    public static String getString(String path) {
        if (languageManager == null) {
            throw new IllegalStateException("SetupLanguageHelper not initialized! Call init() first.");
        }
        return languageManager.getString(path);
    }

    /**
     * Get a menu item name from the language file
     *
     * @param category Category of the menu item
     * @param item Name of the menu item
     * @return Localized name of the menu item
     */
    public static String getMenuItemName(String category, String item) {
        return getString("setup." + category.toLowerCase() + "." + item.toLowerCase() + ".name");
    }

    /**
     * Get a menu item description from the language file
     *
     * @param category Category of the menu item
     * @param item Name of the menu item
     * @return Localized description of the menu item
     */
    public static String getMenuItemDescription(String category, String item) {
        return getString("setup." + category.toLowerCase() + "." + item.toLowerCase() + ".description");
    }

    /**
     * Get a command argument command from the language file
     *
     * @param argument Name of the command argument
     * @return Localized command of the command argument
     */
    public static String getCommandArgumentCommand(String argument) {
        return getString("commands." + argument.toLowerCase() + ".usage");
    }

    /**
     * Get a command argument description from the language file
     *
     * @param argument Name of the command argument
     * @return Localized description of the command argument
     */
    public static String getCommandArgumentDescription(String argument) {
        return getString("commands." + argument.toLowerCase() + ".description");
    }

    /**
     * Get a special block remover message from the language file
     *
     * @param key Key of the message
     * @return Localized message
     */
    public static String getSpecialBlockRemoverMessage(String key) {
        return getString("special-block." + key.toLowerCase());
    }

    /**
     * Get a special block remover removed message from the language file
     * with location and arena placeholders replaced
     *
     * @param location Location of the special block
     * @param arena Arena ID
     * @return Localized message with placeholders replaced
     */
    public static String getSpecialBlockRemoverRemovedMessage(String location, String arena) {
        return getSpecialBlockRemoverMessage("removed")
                .replace("%location%", location)
                .replace("%arena%", arena);
    }

    /**
     * Reload the language configuration
     */
    public static void reload() {
        if (languageManager != null) {
            languageManager.reloadConfig();
        }
    }

    /**
     * Get the current language code
     *
     * @return Current language code
     */
    public static String getLanguageCode() {
        if (languageManager == null) {
            throw new IllegalStateException("SetupLanguageHelper not initialized! Call init() first.");
        }
        return languageManager.getLanguageCode();
    }
} 