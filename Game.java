/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */

public class Game 
{

    private Parser parser;
    private Room currentRoom;
        
    /**
     * Create the game and initialise its internal map.
     */
    
    public Game() 
    {
        createRooms();
        parser = new Parser();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room entrada, did1, did2, did3, did4, did5, did6, saida;
     
        // create the rooms
        entrada = new Room("entrada da universidade");
        did1 = new Room("didatica 1");
        did2 = new Room("didatica 2");
        did3 = new Room("didatica 3");
        did4 = new Room("didatica 4");
        did5 = new Room("didatica 5");
        did6 = new Room("didatica 6");
        saida = new Room("saida da universidade");

        // initialise room exits
        entrada.setExit("leste", did6);
        entrada.setExit("norte", did2);
        entrada.setExit("oeste", did5);

        did1.setExit("sul", did6);
        did1.setExit("oeste", did2);

        did2.setExit("sul", entrada);
        did2.setExit("leste", did1);
        did2.setExit("oeste", did3);

        did3.setExit("sul", did5);
        did3.setExit("leste", did2);
        did3.setExit("oeste", did4);

        did4.setExit("oeste", saida);
        did4.setExit("leste", did3);

        did5.setExit("leste", entrada);
        did5.setExit("norte", did3);

        did6.setExit("oeste", entrada);
        did6.setExit("norte", did1);

        currentRoom = entrada;  // start game outside
    }

    /**
     *  Main play routine.  Loops until end of play.
     */

    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Obrigada por jogar. Até mais!");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Você está prestes a se formar na UFS!");
        System.out.println("Escape UFS é um jogo baseado na ideia de sair da UFS depois de formado, será que você consegue?");
        System.out.println("Digite '" + CommandWord.HELP + "' se precisar de ajuda.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        switch (commandWord) {
            case UNKNOWN:
                System.out.println("Não entendo esse comando...");
                break;

            case HELP:
                printHelp();
                break;

            case GO:
                goRoom(command);
                break;

            case QUIT:
                wantToQuit = quit(command);
                break;
        }
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("Você precisa do seu diploma");
        System.out.println("para finalmente se forrmar.");
        System.out.println();
        System.out.println("Seus comandos são: ");
        parser.showCommands();
    }

    /** 
     * Try to go in one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Aonde ir?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("Não tem um atalho nessa direção!");
        }
        else {
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
        }
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Trancar o quê?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
    
}
