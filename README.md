# Network Programming

## Cocktail Recipes 🍹 🍹

Let's help all cocktail lovers by creating an application where they can search for and share cocktail recipes. We'll develop it as a client-server application, with the server storing and managing the recipes, and users using the client to communicate with the server and search for and upload recipes.

### Cocktail Recipes Client

- Each user can upload cocktail recipes.
- Users can also search for cocktail recipes (by name or ingredients).
- Users can disconnect from the server at any time.

### Cocktail Recipes Server

- The server should be able to serve multiple clients simultaneously.
- The server receives commands from clients and returns appropriate results.

### Description of Client Commands

```bash
- create <cocktail_name> [<ingredient_name>=<ingredient_amount> ...] - sends a request to create a new cocktail recipe with the given name and list of ingredients. The name must consist of a single word (without whitespaces). Each cocktail contains at least one ingredient in the specified format (e.g., whisky=100ml, where "whisky" is the name of the ingredient, and "100ml" is the amount, both are arbitrary strings without whitespaces and the '=' character). Ingredients are listed on the command line separated by whitespace. We assume that there are no duplicate ingredients with different quantities in a recipe.

- get
  - get all – returns a list of all cocktail recipes
  - get by-name <cocktail_name> – returns the recipe for a cocktail with the given name
  - get by-ingredient <ingredient_name> – returns a list of all cocktail recipes that contain the specified ingredient

- disconnect – the user terminates their connection to the server
```

### Example

```bash
# 1. start a cocktail recipes client

# 2.1. create a new cocktail recipe
=> create manhattan whisky=100ml
=> {"status":"CREATED"}

# 2.2. attempt to create a recipe for a cocktail that already exists
=> create manhattan whisky=100ml
=> {"status":"ERROR","errorMessage":"cocktail manhattan already exists"}

# 3.1. list all cocktail recipes when there are none
=> get all
=> {"status":"OK","cocktails":[]}

# 3.2. list all cocktail recipes after creation
=> get all
=> {"status":"OK","cocktails":[{"name":"manhattan","ingredients":[{"name":"whisky","amount":"100ml"}]}]}

# 4. random
=> some random commands
=> Unknown command

# 5. disconnect from the server
=> disconnect
=> Disconnected from the server
```



