package cocktails.business.storage;

import cocktails.business.Cocktail;
import cocktails.business.storage.exception.CocktailAlreadyExistsException;
import cocktails.business.storage.exception.CocktailNotFoundException;

import java.util.*;
import java.util.stream.Collectors;

public class DefaultCocktailStorage implements CocktailStorage {

    private final Collection<Cocktail> cocktails;

    public DefaultCocktailStorage() {
        this.cocktails = new ArrayList<>();
    }

    @Override
    public void createCocktail(Cocktail cocktail) throws CocktailAlreadyExistsException {
        if (cocktailsContain(cocktail.getName())) {
            throw new CocktailAlreadyExistsException();
        } else {
            cocktails.add(cocktail);
        }
    }

    @Override
    public Cocktail getCocktail(String name) throws CocktailNotFoundException {
        for (Cocktail cocktail : this.cocktails) {
            if (cocktail.getName().equalsIgnoreCase(name)) {
                return cocktail;
            }
        }
        throw new CocktailNotFoundException();
    }

    @Override
    public Collection<Cocktail> getCocktailsWithIngredient(String ingredientName) {
        return this.cocktails
                .stream()
                .filter(cl -> cl.hasIngredient(ingredientName))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Cocktail> getCocktails() {
        return this.cocktails;
    }

    private boolean cocktailsContain(String name) {
        for (Cocktail cocktail : this.cocktails) {
            if (cocktail.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

}
