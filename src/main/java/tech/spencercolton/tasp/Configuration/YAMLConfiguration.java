package tech.spencercolton.tasp.Configuration;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;
import java.util.Map;

/**
 * @author Spencer Colton
 */
class YAMLConfiguration implements Configuration {

    private FileConfiguration fc;

    YAMLConfiguration() {
        this.fc = Bukkit.getPluginManager().getPlugin("TASP").getConfig();
    }

    @Override
    public String getString(String path) {
        return this.fc.getString(path);
    }

    @Override
    public Integer getInt(String path) {
        return this.fc.getInt(path);
    }

    @Override
    public Boolean getBoolean(String path) {
        return this.fc.getBoolean(path);
    }

    @Override
    public List getList(String path) { return this.fc.getList(path); }

    @Override
    public Map getMap(String path) {
        return this.fc.getConfigurationSection(path).getValues(true);
    }

}
