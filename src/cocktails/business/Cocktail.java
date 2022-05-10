package cocktails.business;

import java.util.Set;

public class Cocktail {
    String name;
    Set<Ingredient> ingredients;

    public Cocktail(String name, Set<Ingredient> ingredients) {
        this.name = name;
        this.ingredients = ingredients;
    }

    public String getName() {
        return name;
    }

    public Set<Ingredient> getIngredients() {
        return ingredients;
    }

    public boolean hasIngredient(String ingredientName) {
        for (Ingredient ingredient : ingredients) {
            if (ingredient.getName().equalsIgnoreCase(ingredientName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "{" + this.name +
                ": " + ingredients.toString() + "}";
    }

    @Override
    public boolean equals(Object cocktail) {
        return this.name.equalsIgnoreCase(((Cocktail) cocktail).getName());
    }
}
