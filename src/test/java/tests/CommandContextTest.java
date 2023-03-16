package tests;

import com.marcusslover.plus.lib.command.CommandContext;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommandContextTest {
    @SuppressWarnings("DataFlowIssue") // i know that sender can't be null but it doesn't matter here
    @Test
    void childContextTest() {
        String[] parentArgs = new String[]{"world", "plus"};
        CommandContext context = new CommandContext(null, "hello", parentArgs);
        assertNull(context.parent());
        assertEquals("hello", context.label());
        assertArrayEquals(context.args(), parentArgs);

        CommandContext child = context.child(1);
        assertEquals(context, child.parent());
        assertEquals("world", child.label());
        assertArrayEquals(child.args(), new String[]{parentArgs[1]});
    }
}
