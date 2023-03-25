package com.marcusslover.plus.lib.command;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

public record CommandContext(@NotNull Command commandData,
                             @NotNull CommandSender sender,
                             @NotNull String label,
                             @NotNull String[] args,
                             @Nullable CommandContext parent) implements ICommandContextHelper<CommandContext> {
    public CommandContext(@NotNull Command commandData, @NotNull CommandSender sender, @NotNull String label, @NotNull String[] args) {
        this(commandData, sender, label, args, null);
    }

    public @NotNull CommandContext asPlayer(@NotNull Consumer<@NotNull Player> player) {
        if (this.sender instanceof Player p) {
            player.accept(p);
        }
        return this;
    }

    public @NotNull CommandContext asConsole(@NotNull Consumer<ConsoleCommandSender> console) {
        if (this.sender instanceof ConsoleCommandSender c) {
            console.accept(c);
        }
        return this;
    }

    /**
     * Creates a child context.
     *
     * @param consumedArguments The amount of consumed arguments
     * @return A child context with this context as the parent, the last consumed argument as the label,
     * and all unconsumed arguments as args.
     * Created by tecc
     */
    public @NotNull CommandContext child(int consumedArguments) {
        List<String> originalArgs = List.of(this.args());
        if (originalArgs.size() < 1) {
            throw new IndexOutOfBoundsException("At least one argument must be available to make a child context");
        }

        String label = originalArgs.get(consumedArguments - 1);
        CommandSender sender = this.sender();
        int remainingArgsLength = originalArgs.size() - consumedArguments;
        List<String> remainingArgs = remainingArgsLength > 0 ? originalArgs.subList(consumedArguments, originalArgs.size()) : Collections.emptyList();

        // 03.25.2023 - MarcusSlover: Added this.commandData to the constructor.
        return new CommandContext(this.commandData, sender, label, remainingArgs.toArray(new String[0]), this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommandContext that = (CommandContext) o;
        return sender.equals(that.sender) && label.equals(that.label) && Arrays.equals(args, that.args) && parent.equals(that.parent);
    }

    @Override
    public int hashCode() {
        int result = sender.hashCode();
        result = 31 * result + label.hashCode();
        result = 31 * result + Arrays.hashCode(args);
        if (parent != null) {
            result = 31 * result + parent.hashCode();
        }
        return result;
    }

    @Override
    public String toString() {
        return "CommandContext{" +
                "sender=" + sender +
                ", label='" + label + '\'' +
                ", args=" + Arrays.toString(args) +
                ", parent=" + parent +
                '}';
    }
}
