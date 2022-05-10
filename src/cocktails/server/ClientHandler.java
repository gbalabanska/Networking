package cocktails.server;

import cocktails.business.Cocktail;
import cocktails.business.storage.DefaultCocktailStorage;
import cocktails.business.storage.exception.CocktailAlreadyExistsException;
import cocktails.business.storage.exception.CocktailNotFoundException;
import cocktails.command.Command;
import cocktails.command.CommandCreator;
import cocktails.command.CommandExecutor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Collection;

public class ClientHandler implements Runnable {

    private final Socket socket;
    private final DefaultCocktailStorage storage;

    public ClientHandler(Socket socket, DefaultCocktailStorage storage) {
        this.socket = socket;
        this.storage = storage;
    }

    @Override
    public void run() {
        try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true); // autoflush on
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            String inputLine;
            while ((inputLine = in.readLine()) != null) { // read the message from the client
                System.out.println("Message received from client: " + inputLine);
                String output = commandHandle(inputLine);
                out.println(output); // send response back to the client
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String commandHandle(String inputLine) {
        CommandExecutor cmdExecutor = new CommandExecutor(this.storage);
        Command cmd = CommandCreator.newCommand(inputLine);
        String output;

        if (cmd == null) {
            output = "Illegal command!";
        } else {

            switch (cmd.getCommand()) {
                case "create":
                    try {
                        cmdExecutor.createCocktail(cmd.getArgs());
                        output = "status: CREATED";
                    } catch (CocktailAlreadyExistsException e) {
                        output = String.format("status: ERROR, Message: " +
                                "cocktail %s already exists", cmd.getArgs()[0]);
                    }
                    break;
                case "get": {
                    String commandType = cmd.getArgs()[0];
                    switch (commandType) {
                        case "all":
                            Collection<Cocktail> collection = cmdExecutor.getAllCocktails();
                            output = "status: OK, cocktails: " + collection.toString();
                            break;
                        case "by-name":
                            String cocktailName = cmd.getArgs()[1];
                            try {
                                Cocktail ct = cmdExecutor.getCocktailByName(cocktailName);
                                output = "status: OK, cocktail: " + ct.toString();
                            } catch (CocktailNotFoundException e) {
                                output = String.format("status: ERROR, Message: " +
                                        "cocktail %s not found", cmd.getArgs()[0]);
                            }
                            break;
                        case "by-ingredient":
                            String ingredientName = cmd.getArgs()[1];
                            output = "status: OK, cocktail: " +
                                    cmdExecutor.getCocktailsByIngredient(ingredientName).toString();
                            break;
                        default:
                            output = "Unknown command!";
                    }
                }
                break;
                default:
                    output = "Unknown command!";
            }
        }
        return output;
    }

}
