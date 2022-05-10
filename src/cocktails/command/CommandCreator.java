package cocktails.command;

import java.util.Arrays;
import java.util.regex.Pattern;

public class CommandCreator {
    private static final String CREATE = "create [a-zA-Z]+( [a-zA-Z]+=[a-zA-Z0-9]+)+";
    private static final String GET_ALL = "get all";
    private static final String GET_BY_NAME = "get by-name [a-zA-Z]+";
    private static final String GET_BY_INGREDIENT = "get by-ingredient [a-zA-Z]+";

    public static Command newCommand(String input) {
        boolean createMatches = Pattern.compile(CREATE).matcher(input).matches();
        boolean getAllMatches = Pattern.compile(GET_ALL).matcher(input).matches();
        boolean getByNameMatches = Pattern.compile(GET_BY_NAME).matcher(input).matches();
        boolean getByIngredientMatches = Pattern.compile(GET_BY_INGREDIENT).matcher(input).matches();

        if (createMatches || getAllMatches || getByNameMatches || getByIngredientMatches) {
            String[] tokens = input.split(" ");
            String[] args = Arrays.copyOfRange(tokens, 1, tokens.length);
            return new Command(tokens[0], args);
        }

        return null;
    }

}
