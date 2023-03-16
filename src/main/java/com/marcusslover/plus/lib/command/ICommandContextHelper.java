package com.marcusslover.plus.lib.command;

import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public interface ICommandContextHelper<V> {

    /**
     * Executes the consumer if the sender is a player.
     *
     * @param player The consumer.
     * @return The current instance.
     */
    @NotNull V asPlayer(@NotNull Consumer<@NotNull Player> player);

    /**
     * Executes the consumer if the sender is a console.
     *
     * @param console The consumer.
     * @return The current instance.
     */
    @NotNull V asConsole(@NotNull Consumer<@NotNull ConsoleCommandSender> console);
}
