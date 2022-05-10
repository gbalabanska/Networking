package cocktails.command;

import cocktails.business.Cocktail;
import cocktails.business.Ingredient;
import cocktails.business.storage.CocktailStorage;
import cocktails.business.storage.exception.CocktailAlreadyExistsException;
import cocktails.business.storage.exception.CocktailNotFoundException;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class CommandExecutor {

    private static final String INGREDIENT_AMOUNT_SEPARATOR = "=";

    private final CocktailStorage storage;

    public CommandExecutor(CocktailStorage storage) {
        this.storage = storage;
    }

    public void createCocktail(String[] args) throws CocktailAlreadyExistsException {
        String cocktailName = args[0];
        Set<Ingredient> ingredients = new HashSet<>();
        for (int i = 1; i < args.length; i++) {
            String[] ingredientPair = args[i].split(INGREDIENT_AMOUNT_SEPARATOR);
            Ingredient ingredient = new Ingredient(ingredientPair[0], ingredientPair[1]);
            ingredients.add(ingredient);
        }

        Cocktail cocktail = new Cocktail(cocktailName, ingredients);
        storage.createCocktail(cocktail);
    }

    public Cocktail getCocktailByName(String cocktailName) throws CocktailNotFoundException {
        return storage.getCocktail(cocktailName);
    }

    public Collection<Cocktail> getCocktailsByIngredient(String ingredientName) {
        return storage.getCocktailsWithIngredient(ingredientName);
    }

    public Collection<Cocktail> getAllCocktails() {
        return storage.getCocktails();
    }

}
