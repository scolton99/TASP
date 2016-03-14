package tech.spencercolton.tasp.Configuration;

import java.util.List;
import java.util.Map;

/**
 * @author Spencer Colton
 */
public interface Configuration {

    int getInt(String path);
    String getString(String path);
    boolean getBoolean(String path);
    List<String> getStringList(String path);
    Map getMap(String path);

}
