package tech.spencercolton.tasp.Commands;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

/**
 * Global superclass for all specific TASP commands.
 * <p>
 *     {@link #getConsoleSyntax()} may return {@code null} if the command cannot be run from the console.
 * </p>
 *
 * @author Spencer Colton
 * @since 0.0.1-001
 */
public abstract class TASPCommand {

    public static final String name = "{{MISSING NAME}}";
    public static final String syntax = "{{MISSING SYNTAX}}";
    public static final String consoleSyntax = "{{MISSING CONSOLE SYNTAX}}";
    public static final String permission = "{{MISSING PERMISSION}}";

    /**
     * Contains the logic used to process a command.
     * <p>
     *     Unlike Spigot's {@code onCommand} method, this method does not return a type of {@code boolean}.  It is
     *     expected that the command code will effectively inform players when they have failed to correctly execute
     *     the command, therefore a return value is unnecessary.  All intervention will be taken care of using messages
     *     to the {@link CommandSender}.
     * </p>
     *
     * @param sender To be supplied by the {@link Command class}
     * @param args To be supplied by the {@link Command class}
     */
    public abstract void execute(CommandSender sender, String[] args);

    /**
     * Returns the command's expected in-game syntax in the form a human-readable {@code String}.
     * <p>
     *     The value is used in functions such as {@link Command#sendSyntaxError(CommandSender, TASPCommand)} to
     *     inform {@link CommandSender}s that they have incorrectly used a command.
     * </p>
     *
     * @return The in-game syntax for the command, in a human-readable {@code String}.
     */
    public abstract String getSyntax();

    /**
     * Returns the command's expected console syntax in the form a human-readable {@code String}.
     * <p>
     *     The value is used in functions such as
     *     {@link Command#sendConsoleSyntaxError(ConsoleCommandSender, TASPCommand)} to
     *     inform {@link CommandSender}s that they have incorrectly used a command.
     * </p>
     * @return {@code null} if the command cannot be run from the console, or a human-readable {@code String} with
     * the command's console syntax.
     */
    public abstract String getConsoleSyntax();

    public abstract String getPermission();

    public abstract boolean predictOthers(String[] s);

    public abstract String getName();
}
