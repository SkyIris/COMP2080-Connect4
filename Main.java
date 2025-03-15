/* Winter 2024 COMP 2080 Assignment 2
   Akeen Zhong          101462287
   Kevin Bhangu         101418717
   Pablo Arango Gomez   101153741
   Tomer Edelman        101400506
   Ying Wu              101153072
*/

package Connect4;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        /*
         * Defining a Scanner called s
         * Defining a String called playerPosition
         * Defining an Int called playerColumn
         * Defining a Boolean called gameActive
         */
        Scanner s = new Scanner(System.in);
        String playerPosition;
        int playerColumn;
        boolean gameActive = true;
        // While gameActive is true, loop through the game
        while (gameActive){
            System.out.println("----------------------------------");
            System.out.println("    Welcome To Connect 4         ");
            System.out.println("----------------------------------");
            System.out.println("Would you like");
            System.out.println("1: Single Player (Coming Soon)");
            System.out.println("2: Multiplayer");
            System.out.println("3: Exit");
            System.out.print("\nSelection: ");
            // Taking an int and determines what result to do from that input
            // Try / catch makes sure that the input is an int
            int in;
            try {
                in = s.nextInt();
                s.nextLine();
            } catch(InputMismatchException e){
                s.nextLine();
                in = 0;
            }
            System.out.println("----------------------------------");
            switch(in) {
                // Single player has not been implemented
                case 1:
                    System.out.println("\nFeature is currently not available.");
                    break;
                // Multi player code
                case 2:
                    boolean player = true;
                    System.out.println("\nMultiplayer Chosen");

                    // Assigning variables for Multi player game
                    boolean player1NameValid, player2NameValid, colorChoiceValid;
                    player1NameValid = player2NameValid = colorChoiceValid = false;
                    String name1, name2, player1Color; name1 = name2 = player1Color= "";

                    // loop for setting the first player name
                    while (!player1NameValid){
                        System.out.print("\nEnter name for player 1: ");
                        name1 = s.nextLine();

                        if (name1 != null && !name1.isEmpty()){
                            player1NameValid = true;
                        } else {
                            System.out.println("Invalid input; please enter a valid name.");
                        }
                    }

                    // loop for setting the first player Color
                    while (!colorChoiceValid){
                        System.out.print("\nPlayer 1 color (Enter Y for Yellow or R for Red): ");
                        player1Color = s.nextLine();

                        if (player1Color.matches("^[YyRr]$")){
                            colorChoiceValid = true;
                        } else {
                            System.out.println("Invalid input; please enter a valid choice.");
                        }
                    }
                    // loop for setting the Second player name
                    while (!player2NameValid){
                        System.out.print("\nEnter name for player 2: ");
                        name2 = s.nextLine();

                        if (name2 != null && !name2.isEmpty()){
                            player2NameValid = true;
                        } else {
                            System.out.println("Invalid input; please enter a valid name.");
                        }
                    }

                    // setting the Player playing yellow to go first
                    // setting the Player playing red to go Second
                    boolean player1IsYellow = player1Color.equals("Y") || player1Color.equals("y");
                    String player1, player2;
                    if (player1IsYellow){
                        player1 = name1; player2 = name2;
                    } else {
                        player1 = name2; player2 = name1;
                    }

                    // Calling Board class
                    // Calling Prep board which fills the board array
                    Board b = new Board(name1, name2, player1IsYellow);
                    b.prepBoard();

                    // loops until game is complete
                    // Display Board prints the board array
                    // Checks if it's player 1 or 2's turn
                    while(b.getGame()) {
                        b.displayBoard();
                        if(player) {
                            System.out.println(player1 + ", enter the column(1-7) you want to place your piece:");
                            playerPosition = s.next();
                            // checks if the players selected column is valid
                            playerColumn = b.checkValidColumn(playerPosition);
                            while(playerColumn == -1 || !b.checkEmptyColumn(playerColumn))
                            {
                                playerPosition = s.next(); //valid column
                                playerColumn = b.checkValidColumn(playerPosition); // checks if the game is won
                            }

                            // Adds a Yellow piece to the board
                            b.AddPiece(true, playerColumn);
                        }
                        else  {
                            System.out.println(player2 + ", enter the column(1-7) you want to place your piece:");
                            playerPosition = s.next();
                            // checks if the players selected column is valid
                            playerColumn = b.checkValidColumn(playerPosition);
                            while(playerColumn == -1 || !b.checkEmptyColumn(playerColumn))
                            {
                                playerPosition = s.next(); //valid column
                                playerColumn = b.checkValidColumn(playerPosition);// checks if the game is won
                            }

                            // Adds a Red piece to the board
                            b.AddPiece(false, playerColumn);
                        }
                        player = !player;
                    }
                    // After the game is done, print the last board state and waits for the next player input to progress
                    System.out.print("\nPress 'Enter' to continue...\n");
                    s.nextLine(); //consumes the new line character for formatting
                    s.nextLine(); //waits on user input
                    break;
                // ends the Game loop
                case 3:
                    gameActive = false;
                    break;
                // if the input doesn't match any of the conditions
                default:
                    System.out.println("Invalid input; please enter a valid choice.");
            }
        }
        // Closes the Scanner
        s.close();
    }
}
