package tech.spencercolton.tasp.Configuration;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;
import java.util.Map;

/**
 * @author Spencer Colton
 */
public class YAMLConfiguration implements Configuration {

    private FileConfiguration fc;

    public YAMLConfiguration(FileConfiguration fc) {
        this.fc = fc;
    }

    public List<String> getStringList(String path) {
        return this.fc.getStringList(path);
    }

    public String getString(String path) {
        return this.fc.getString(path);
    }

    public int getInt(String path) {
        return this.fc.getInt(path);
    }

    public boolean getBoolean(String path) {
        return this.fc.getBoolean(path);
    }

    public Map getMap(String path) {
        return this.fc.getConfigurationSection(path).getValues(true);
    }

}
