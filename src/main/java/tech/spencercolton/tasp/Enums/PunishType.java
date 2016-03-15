package tech.spencercolton.tasp.Enums;

import lombok.Getter;

/**
 * @author Spencer Colton
 */
public enum PunishType {

    BAN("ban %%player%% %%reason%%"),
    TEMPBAN("tempban %%player%% %%duration%% %%reason%%"),
    KICK("kick %%player%% %%reason%%"),
    MUTE("gmute %%player%%");

    @Getter
    private final String logic;

    PunishType(String s) {
        this.logic = s;
    }

}
