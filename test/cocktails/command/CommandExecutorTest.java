package cocktails.command;

import cocktails.business.Cocktail;
import cocktails.business.Ingredient;
import cocktails.business.storage.CocktailStorage;
import cocktails.business.storage.exception.CocktailAlreadyExistsException;
import cocktails.business.storage.exception.CocktailNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CommandExecutorTest {

    private CocktailStorage storage;
    private CommandExecutor cmdExecutor;

    String[] argumentsArray = new String[]{
            "testName", "testIngredientOne=testAmount1", "testIngredientTwo=testAmount2", "testIngredientThree=testAmount3"};

    private final String TEST_NAME = "testName";
    private final String TEST_INGREDIENT = "testIngredient";

    @BeforeEach
    public void setUp() {
        storage = mock(CocktailStorage.class);
        cmdExecutor = new CommandExecutor(storage);
    }

    @Test
    public void testCreateCocktailWhenCocktailIsNotExisting() throws CocktailAlreadyExistsException {
        doNothing().when(storage).createCocktail(isA(Cocktail.class));
        cmdExecutor.createCocktail(argumentsArray);
        verify(storage).createCocktail(any(Cocktail.class));
    }

    @Test
    public void testCreateCocktailWhenCocktailAlreadyExists() throws CocktailAlreadyExistsException {
        doThrow(CocktailAlreadyExistsException.class)
                .when(storage)
                .createCocktail(any(Cocktail.class));
        assertThrows(CocktailAlreadyExistsException.class, () ->
                cmdExecutor.createCocktail(argumentsArray), "expected exception was not thrown when cocktail already exists");
        verify(storage).createCocktail(any(Cocktail.class));
    }

    @Test
    public void testGetCocktailByNameWhenCocktailIsInStorage() throws CocktailNotFoundException {
        Cocktail expectedCocktail = mock(Cocktail.class);
        when(storage.getCocktail(TEST_NAME)).thenReturn(expectedCocktail);
        Cocktail actualCocktail = cmdExecutor.getCocktailByName(TEST_NAME);
        verify(storage).getCocktail(TEST_NAME);
        assertEquals(expectedCocktail, actualCocktail, "unexpected output when cocktail is in storage");
    }

    @Test
    public void testGetCocktailByNameWhenCocktailIsNotInStorage() throws CocktailNotFoundException {
        doThrow(CocktailNotFoundException.class)
                .when(storage)
                .getCocktail(TEST_NAME);
        assertThrows(CocktailNotFoundException.class, () ->
                cmdExecutor.getCocktailByName(TEST_NAME), "expected exception was not thrown when cocktail is not in storage");
        verify(storage).getCocktail(TEST_NAME);
    }

    @Test
    public void testGetCocktailsByIngredientWhenThereAreSuchIngredients() {
        Collection<Cocktail> expectedCollection = new ArrayList<>();
        Cocktail cocktail1 = mock(Cocktail.class);
        expectedCollection.add(cocktail1);
        Cocktail cocktail2 = mock(Cocktail.class);
        expectedCollection.add(cocktail2);

        when(storage.getCocktailsWithIngredient(TEST_INGREDIENT))
                .thenReturn(expectedCollection);
        Collection<Cocktail> actualCollection = cmdExecutor
                .getCocktailsByIngredient(TEST_INGREDIENT);

        assertEquals(expectedCollection, actualCollection, "unexpected collection content");
        verify(storage)
                .getCocktailsWithIngredient(TEST_INGREDIENT);
    }

    @Test
    public void testGetCocktailsByIngredientWhenThereIsNoSuchIngredient() {
        when(storage.getCocktailsWithIngredient(TEST_INGREDIENT))
                .thenReturn(Collections.emptyList());
        assertTrue(cmdExecutor.getCocktailsByIngredient(TEST_INGREDIENT)
                .isEmpty(), "unexpected collection size");
        verify(storage)
                .getCocktailsWithIngredient(TEST_INGREDIENT);
    }

    @Test
    public void testGetAllCocktailsWhenThereAreNoCocktailsInStorage() {
        when(storage.getCocktails()).thenReturn(Collections.emptyList());
        Collection<Cocktail> actualCollection = cmdExecutor.getAllCocktails();
        assertTrue(actualCollection.isEmpty(), "unexpected collection content when collection must be empty");
        verify(storage).getCocktails();
    }

    @Test
    public void testGetAllCocktailsWhenThereAreCocktailsInStorage() {
        Collection<Cocktail> expectedCollection = new ArrayList<>();
        Cocktail cocktail1 = mock(Cocktail.class);
        expectedCollection.add(cocktail1);
        Cocktail cocktail2 = mock(Cocktail.class);
        expectedCollection.add(cocktail2);
        when(storage.getCocktails()).thenReturn(expectedCollection);
        Collection<Cocktail> actualCollection = cmdExecutor.getAllCocktails();
        assertEquals(expectedCollection,actualCollection, "unexpected collection content");
        verify(storage).getCocktails();
    }
}
