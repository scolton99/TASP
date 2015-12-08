package tech.spencercolton.tasp;

import org.bukkit.plugin.java.JavaPlugin;
import tech.spencercolton.tasp.Commands.Command;

public class TASP extends JavaPlugin {

        @Override
        public void onEnable() {
            getLogger().info("Loading TASP plugin...");
            initCommands();
        }

        @Override
        public void onDisable() {
            getLogger().info("Disabling TASP plugin...");
        }

        private void initCommands() {
            Command.populateList();
        }

}
