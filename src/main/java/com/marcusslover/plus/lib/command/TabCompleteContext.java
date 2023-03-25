package com.marcusslover.plus.lib.command;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.function.Consumer;

public record TabCompleteContext(
        @NotNull Command commandData,
        @NotNull CommandSender sender,
        @NotNull String label,
        @NotNull String alias,
        @NotNull String[] args) implements ICommandContextHelper<TabCompleteContext> {
    @Override
    public @NotNull TabCompleteContext asPlayer(@NotNull Consumer<@NotNull Player> player) {
        if (this.sender instanceof Player p) {
            player.accept(p);
        }
        return this;
    }

    @Override
    public @NotNull TabCompleteContext asConsole(@NotNull Consumer<@NotNull ConsoleCommandSender> console) {
        if (this.sender instanceof ConsoleCommandSender c) {
            console.accept(c);
        }
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TabCompleteContext that = (TabCompleteContext) o;
        return sender.equals(that.sender) && Arrays.equals(args, that.args);
    }

    @Override
    public int hashCode() {
        int result = sender.hashCode();
        result = 31 * result + Arrays.hashCode(args);
        return result;
    }

    @Override
    public String toString() {
        return "TabCompleteContext{" +
                "sender=" + sender +
                ", args=" + Arrays.toString(args) +
                '}';
    }
}
