package tech.spencercolton.tasp.Commands;

import org.bukkit.command.CommandSender;

public abstract class TASPCommand {

    public abstract void execute(CommandSender sender, String[] args);

}
