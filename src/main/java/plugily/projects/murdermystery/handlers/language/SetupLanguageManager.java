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

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import plugily.projects.minigamesbox.classic.utils.configuration.ConfigUtils;
import plugily.projects.murdermystery.Main;

import java.io.File;

/**
 * @author Your Name
 * <p>
 * Class for managing setup menu language files
 */
public class SetupLanguageManager {

    private final Main plugin;
    private FileConfiguration config;
    private final String pluginPath;
    private final String fileName = "setup_lang.yml";
    private String languageCode = "en"; // Default language

    public SetupLanguageManager(Main plugin) {
        this.plugin = plugin;
        this.pluginPath = plugin.getDataFolder().getPath();
        setupFiles();
    }

    /**
     * Setup configuration files
     */
    private void setupFiles() {
        // Save default setup_lang.yml if it doesn't exist
        File defaultFile = new File(pluginPath + File.separator + fileName);
        if (!defaultFile.exists()) {
            plugin.saveResource(fileName, false);
        }
        
        // Load the configuration
        config = ConfigUtils.getConfig(plugin, fileName);
        
        // Try to load language-specific file if specified in config
        languageCode = config.getString("Language", "en");
        
        // Check if language-specific file exists
        File langFile = new File(pluginPath + File.separator + "setup_lang_" + languageCode + ".yml");
        if (langFile.exists()) {
            // Load language-specific file
            config = ConfigUtils.getConfig(plugin, "setup_lang_" + languageCode + ".yml");
        }
    }

    /**
     * Reload the language configuration
     */
    public void reloadConfig() {
        setupFiles();
    }

    /**
     * Get a string from the language file
     *
     * @param path Path to the string
     * @return String from the language file or path if not found
     */
    public String getString(String path) {
        if (config.isSet(path)) {
            return config.getString(path);
        }
        
        // If not found in language-specific file, try the default file
        if (!languageCode.equals("en")) {
            FileConfiguration defaultConfig = ConfigUtils.getConfig(plugin, fileName);
            if (defaultConfig.isSet(path)) {
                return defaultConfig.getString(path);
            }
        }
        
        plugin.getLogger().warning("Missing language key: " + path + " in " + fileName);
        return path;
    }

    /**
     * Get the language code
     *
     * @return Current language code
     */
    public String getLanguageCode() {
        return languageCode;
    }
} 