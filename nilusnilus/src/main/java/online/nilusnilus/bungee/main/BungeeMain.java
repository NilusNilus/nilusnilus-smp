package online.nilusnilus.bungee.main;

import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import online.nilusnilus.bungee.commands.CommandPing;
import online.nilusnilus.bungee.commands.CommandWarp;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

@SuppressWarnings("all")
public class BungeeMain extends Plugin {

    // ======================================================================
    // Paragraph symbol: §          Checkmark symbol: ✔
    // ======================================================================

    // ======================================================================

    @Override
    public void onEnable() {
        getProxy().getPluginManager().registerCommand(this, new CommandWarp(this, "warp"));
        getProxy().getPluginManager().registerCommand(this, new CommandPing("ping"));
    }

    // ======================================================================

    public static class CustomConfig {

        // ======================================================================

        // Enter the name of the config to check if it exists in resources or to create a new one
        public CustomConfig(Plugin plugin, String configName) {
            this.plugin = plugin;
            this.configName = configName;
        }

        // ======================================================================

        // Declare variables
        private Plugin plugin;
        private String configName;
        private File file;
        private Configuration config;

        // ======================================================================

        // Creates file and checks if a pre-made config already exists in resources
        public boolean initialize() {
            plugin.getLogger().info("§6[" + configName + "] Initializing config file.");

            // ======================================================================

            // Checks if the data-folder belonging to the plugin exists

            plugin.getLogger().info("§6[" + configName + "] Checking if data-folder exists.");
            if(!plugin.getDataFolder().exists()) {
                plugin.getLogger().info("§6[" + configName + "] Data-folder does not exist. Creating it.");
                plugin.getDataFolder().mkdirs();
            } else
                plugin.getLogger().info("§6[" + configName + "] Data-folder does exist.");

            // ======================================================================

            // If there already is a config file present in the data-folder, it will load that one.

            file = new File(plugin.getDataFolder(), configName);
            if(file.exists()) {
                plugin.getLogger().info("§6[" + configName + "] Config file exists. Using that one.");
                reload();
                plugin.getLogger().info("§a[" + configName + "] Config file loaded.");
                return true;
            }

            // ======================================================================

            // This checks if there is a pre-made config file in the resources folder. Otherwise it will create an empty config.

            plugin.getLogger().info("§6[" + configName + "] Checking for pre-made config.");
            InputStream premadeFile = plugin.getResourceAsStream("configs/" + configName);
            if(premadeFile == null) {
                plugin.getLogger().info("§6[" + configName + "] No pre-made config found.");
                plugin.getLogger().info("§6[" + configName + "] Creating a new one.");
                try {
                    file.createNewFile();
                    plugin.getLogger().info("§a[" + configName + "] Config file created.");
                    return true;
                } catch (IOException e) {
                    plugin.getLogger().info("§c[" + configName + "] Failed to create config.");
                    e.printStackTrace();
                    return false;
                }
            }

            // ======================================================================

            // This will copy the contents of the pre-made config into the new one.

            plugin.getLogger().info("§6[" + configName + "] Copying pre-made config to new config.");
            try {
                Files.copy(premadeFile, file.toPath());
                plugin.getLogger().info("§a[" + configName + "] Config file created.");
                return true;
            } catch (IOException e) {
                plugin.getLogger().info("§c[" + configName + "] Failed to create config.");
                e.printStackTrace();
                return false;
            }

            // ======================================================================
        }

        // ======================================================================

        // Reloads the config
        public boolean reload() {
            try {
                plugin.getLogger().info("§6[" + configName + "] Reloading config file.");
                config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
                plugin.getLogger().info("§a[" + configName + "] Reloaded config file.");
                return true;
            } catch (IOException e) {
                plugin.getLogger().info("§c[" + configName + "] Failed reloading config file.");
                e.printStackTrace();
                return false;
            }
        }

        // Saves the config
        public boolean save() {
            try {
                plugin.getLogger().info("§6[" + configName + "] Saving config file.");
                ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, file);
                plugin.getLogger().info("§a[" + configName + "] Config file saved.");
                return true;
            } catch (IOException e) {
                plugin.getLogger().info("§c[" + configName + "] Failed saving config file.");
                e.printStackTrace();
                return false;
            }
        }

        // ======================================================================

        // Sets a value in the config
        public boolean set(String key, Object obj) {
            config.set(key, obj);
            return save();
        }

        // Gets a value from the config
        public Object get(String key) {
            reload();
            return config.get(key);
        }

        // Getter for config file
        public Configuration getConfig() {
            return config;
        }

        // ======================================================================

    }

}
