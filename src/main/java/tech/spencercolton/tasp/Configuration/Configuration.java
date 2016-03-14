package tech.spencercolton.tasp.Configuration;

import java.util.List;
import java.util.Map;

/**
 * @author Spencer Colton
 */
public interface Configuration {

    Integer getInt(String path);
    String getString(String path);
    Boolean getBoolean(String path);
    List getList(String path);
    Map getMap(String path);

}
