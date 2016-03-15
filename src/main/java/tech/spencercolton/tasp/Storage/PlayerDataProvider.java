package tech.spencercolton.tasp.Storage;

import java.util.List;
import java.util.Map;

/**
 * @author Spencer Colton
 */
public interface PlayerDataProvider {

    Integer getInt(String path);
    Boolean getBoolean(String path);
    String getString(String path);
    Map getMap(String path);
    List getList(String path);

    void setString(String path, String set);
    void setInt(String path, Integer set);
    void setMap(String path, Map set);
    void setList(String path, List set);
    void setBoolean(String path, Boolean set);

    boolean isValid();

}
