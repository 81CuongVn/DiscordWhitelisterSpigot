package uk.co.angrybee.joe.configs;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import uk.co.angrybee.joe.DiscordWhitelister;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;


// discord-whitelister.yml
public class MainConfig extends Config {
    public static String default_token = "Discord bot token goes here, you can find it here: https://discordapp.com/developers/applications/";
    public MainConfig() {
        fileName = "discord-whitelister.yml";
        file = new File(DiscordWhitelister.getPlugin().getDataFolder(), fileName);
        fileConfiguration = new YamlConfiguration();
    }

    public FileConfiguration getFileConfiguration() {
        return fileConfiguration;
    }

    public boolean fileCreated = false;

    public void ConfigSetup() {


        // Create root folder for configs if it does not exist
        if (!file.getParentFile().exists())
            file.getParentFile().mkdirs();

        if (!file.exists()) {
            CreateConfig();
            DiscordWhitelister.getPluginLogger().warning("Configuration file created at: " + file.getPath() +
                    ", please edit this else the plugin will not work!");
        }
        LoadConfigFile();
        CheckEntries();
        SaveConfig();
    }


    private void CheckEntries() {
        CheckEntry("bot-enabled", true);

        CheckEntry("discord-bot-token",
                default_token);

        CheckEntry("use-id-for-roles", false);

        // Allowed to add and remove from the whitelist
        CheckEntry("add-remove-roles", Arrays.asList("Owner", "Admin"));

        // Only allowed to add to the whitelist
        CheckEntry("add-roles", Arrays.asList("Mod", "Whitelister"));

        // Roles that are allowed whitelist a limited amount of times
        CheckEntry("limited-add-roles", Collections.singletonList("LimitedWhitelister"));

        // The roles to add/remove when whitelisted/removed
        CheckEntry("whitelisted-roles", Collections.singletonList("Whitelisted"));

        CheckEntry("banned-roles", Collections.singletonList("Banned"));

        // For clear name & ban commands
        CheckEntry("clear-command-roles", Collections.singletonList("Admin"));

        CheckEntry("target-text-channels", Arrays.asList("000000000000000000", "111111111111111111"));

        // EasyWhitelist support (https://www.spigotmc.org/resources/easywhitelist-name-based-whitelist.65222/)
        CheckEntry("use-easy-whitelist", false);

        CheckEntry("allow-limited-whitelisters-to-unwhitelist-self", true);

        // If adding the whitelisted role to the discord user is enabled
        CheckEntry("whitelisted-role-auto-add", false);

        // If removing the whitelisted role from the discord user is enabled
        CheckEntry("whitelisted-role-auto-remove", false);

        // If the limited whitelist feature should be enabled
        CheckEntry("limited-whitelist-enabled", true);

        // Remove whitelisted role, assign to banned role, remove their whitelisted players
        CheckEntry("use-on-ban-events", false);

        CheckEntry("unwhitelist-and-clear-perms-on-name-clear", true);

        // The amount of times a non-staff user is allowed to whitelist
        CheckEntry("max-whitelist-amount", 3);

        CheckEntry("username-validation", true);

        CheckEntry("removed-list-enabled", true);

        CheckEntry("add-in-game-adds-and-removes-to-list", true);

        CheckEntry("use-custom-messages", false);

        CheckEntry("use-custom-prefixes", false);

        CheckEntry("show-player-skin-on-whitelist", true);

        CheckEntry("show-player-count", true);

        CheckEntry("show-vanished-players-in-player-count", false);

        CheckEntry("assign-perms-with-ultra-perms", false);

        CheckEntry("assign-perms-with-luck-perms", false);

        CheckEntry("use-on-whitelist-commands", false);

        CheckEntry("send-instructional-message-on-whitelist", false);

        CheckEntry("use-timer-for-instructional-message", false);

        CheckEntry("timer-wait-time-in-seconds", 5);

        CheckEntry("un-whitelist-on-server-leave", true);

        CheckEntry("remove-unnecessary-messages-from-whitelist-channel", false);

        CheckEntry("seconds-to-remove-message-from-whitelist-channel", 5);

        CheckEntry("set-removed-message-colour-to-red", false);

        CheckEntry("show-warning-in-command-channel", false);

        CheckEntry("hide-info-command-replies", false);

        CheckEntry("use-crafatar-for-avatars", false);

        CheckEntry("use-geyser/floodgate-compatibility", false);

        CheckEntry("geyser/floodgate prefix", "SetThisToWhateverPrefixYouUse");

        CheckEntry("un-whitelist-if-missing-role", false);

        CheckEntry("check-all-roles", false);

        CheckEntry("role-to-check-for", "Twitch Subscriber");

        // Remove old role entry if found, move role to new array (for people with v1.3.6 or below)
        if (fileConfiguration.get("whitelisted-role") != null) {
            DiscordWhitelister.getPluginLogger().warning("Found whitelisted-role entry, moving over to whitelisted-roles. Please check your config to make sure the change is correct");
            // Get the role from the old entry
            String whitelistedRoleTemp = fileConfiguration.getString("whitelisted-role");
            // Assign role from old entry to new entry as a list
            fileConfiguration.set("whitelisted-roles", Collections.singletonList(whitelistedRoleTemp));

            // Remove now un-used entry
            fileConfiguration.set("whitelisted-role", null);

            // Note to users that id for roles now affects the new entry
            if (fileConfiguration.getBoolean("use-id-for-roles")) {
                DiscordWhitelister.getPluginLogger().severe("You have 'use-id-for-roles' enabled please change the whitelisted-roles to ids as they now follow this setting");
            }
        }
    }
}
