package cocktails.business.storage;

import cocktails.business.Cocktail;
import cocktails.business.storage.exception.CocktailAlreadyExistsException;
import cocktails.business.storage.exception.CocktailNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DefaultCocktailStorageTest {

    CocktailStorage storage;

    private final String TEST_NAME = "testName";
    private final String TEST_INGREDIENT = "testIngredient";

    @BeforeEach
    public void setUp() {
        storage = new DefaultCocktailStorage();
    }

    @Test
    public void testCreateCocktailWhenCocktailDoesNotExist() throws CocktailAlreadyExistsException {
        Cocktail cocktail = mock(Cocktail.class);
        when(cocktail.getName()).thenReturn(TEST_NAME);
        storage.createCocktail(cocktail);
        assertTrue(storage.getCocktails().contains(cocktail), "unexpected content of the storage");
    }

    @Test
    public void testCreateCocktailWhenCocktailAlreadyExists() {
        Cocktail cocktail = mock(Cocktail.class);
        when(cocktail.getName()).thenReturn(TEST_NAME);

        storage.getCocktails().add(cocktail);

        assertThrows(CocktailAlreadyExistsException.class, () ->
                storage.createCocktail(cocktail), "expected exception was not thrown");
    }

    @Test
    public void testGetCocktailWhenCocktailExists() throws CocktailNotFoundException {
        Cocktail expectedCocktail = mock(Cocktail.class);
        when(expectedCocktail.getName()).thenReturn(TEST_NAME);
        storage.getCocktails().add(expectedCocktail);
        Cocktail actualCocktail = storage.getCocktail(TEST_NAME);
        assertEquals(expectedCocktail, actualCocktail, "unexpected output when cocktail is in storage");
    }

    @Test
    public void testGetCocktailWhenCocktailDoesNotExist() {
        assertThrows(CocktailNotFoundException.class, () ->
                storage.getCocktail(TEST_NAME));
    }

    @Test
    public void testGetCocktailWithIngredientWhenThereAreNoCocktailsWithIngredient() {
        Collection<Cocktail> actualCollection = storage.getCocktailsWithIngredient(TEST_INGREDIENT);
        assertTrue(actualCollection.isEmpty(), "unexpected collection content");
    }

    @Test
    public void testGetCocktailsWithIngredientWhenThereAreCocktailsWithIngredient() {
        Cocktail ct1 = mock(Cocktail.class);
        Cocktail ct2 = mock(Cocktail.class);
        Cocktail ct3 = mock(Cocktail.class);

        when(ct1.hasIngredient(TEST_INGREDIENT)).thenReturn(true);
        when(ct2.hasIngredient(TEST_INGREDIENT)).thenReturn(true);
        when(ct3.hasIngredient("unwantedIngredient")).thenReturn(false);

        Collection<Cocktail> expectedCollection = new ArrayList<>();
        expectedCollection.add(ct1);
        expectedCollection.add(ct2);

        storage.getCocktails().add(ct1);
        storage.getCocktails().add(ct2);
        storage.getCocktails().add(ct3);

        Collection<Cocktail> actualCollection =
                storage.getCocktailsWithIngredient(TEST_INGREDIENT);

        assertEquals(expectedCollection, actualCollection, "unexpected collection content");
    }

}
