package cocktails.command;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CommandCreatorTest {

    @Test
    public void testCommandCreationWithUnknownCommand() {
        String command = "test";
        Command cmd = CommandCreator.newCommand(command);
        assertNull(cmd, "unknown command should be null");
    }

    @Test
    public void testCommandCreationWithCreateMatch() {
        String input =
                "create testName testIngrOne=testAmount1 testIngrTwo=testAmount2";
        Command cmd = CommandCreator.newCommand(input);
        assertEquals("create", cmd.getCommand(), "unexpected command name returned");
        assertEquals(3, cmd.getArgs().length, "unexpected command arguments count");
        assertEquals("testName", cmd.getArgs()[0], "unexpected argument returned");
        assertEquals("testIngrOne=testAmount1", cmd.getArgs()[1], "unexpected argument returned");
        assertEquals("testIngrTwo=testAmount2", cmd.getArgs()[2], "unexpected argument returned");
    }

    @Test
    public void testCommandCreationWithGetAllMatch() {
        String input = "get all";
        Command cmd = CommandCreator.newCommand(input);
        assertEquals("get", cmd.getCommand(), "unexpected command name returned");
        assertEquals("all", cmd.getArgs()[0], "unexpected argument returned");
        assertEquals(1, cmd.getArgs().length, "unexpected command arguments count");
    }

    @Test
    public void testCommandCreationWithGetByNameMatch() {
        String input = "get by-name testName";
        Command cmd = CommandCreator.newCommand(input);
        assertEquals("get", cmd.getCommand(), "unexpected command name returned");
        assertEquals("by-name", cmd.getArgs()[0], "unexpected argument returned");
        assertEquals("testName", cmd.getArgs()[1], "unexpected argument returned");
        assertEquals(2, cmd.getArgs().length, "unexpected command arguments count");
    }

    @Test
    public void testCommandCreationWithGetByIngredientMatch() {
        String input = "get by-ingredient testIngredient";
        Command cmd = CommandCreator.newCommand(input);
        assertEquals("get", cmd.getCommand(), "unexpected command name returned");
        assertEquals("by-ingredient", cmd.getArgs()[0], "unexpected argument returned");
        assertEquals("testIngredient", cmd.getArgs()[1], "unexpected argument returned");
        assertEquals(2, cmd.getArgs().length, "unexpected command arguments count");
    }
}