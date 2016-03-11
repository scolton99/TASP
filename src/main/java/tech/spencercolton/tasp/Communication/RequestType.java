package tech.spencercolton.tasp.Communication;

import lombok.Getter;
import tech.spencercolton.tasp.Commands.Command;
import tech.spencercolton.tasp.Commands.TASPCommand;

/**
 * @author Spencer Colton
 */
public enum RequestType {

    AFK,
    ANTIDOTE,
    BLOCK,
    BROADCAST,
    BUDDHA,
    BURN,
    CLEAR,
    DISAPPEAR,
    FEED,
    FLY,
    FOM,
    GAMEMODE,
    GOD,
    HEAL,
    HELPME,
    HURT,
    INFO,
    INVSPY,
    KICK,
    KILL,
    MAIL,
    ME,
    MSG,
    MUTE,
    NICK,
    POTION,
    POWERTOOLTOGGLE,
    REPLY,
    SETSPEED,
    SHOCK,
    SRVINFO,
    STAHP,
    STALKER,
    STARVE,
    SU,
    TASP,
    TPA,
    TPALL,
    TPAR,
    TP,
    TPD,
    TPHERE,
    TPRHERE,
    TPLOC,
    TPR,
    TPT,
    TIME,
    UNBLOCK,
    WARP,
    WARPS,
    WEATHER,
    WORLD,
    XP;

    @Getter
    private final TASPCommand logic;

    RequestType() {
        this.logic = Command.getCommand(this.toString().toLowerCase());
    }

}
