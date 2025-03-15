package Connect4;
public class Board {
    private String[][] board; //arrays for board
    private String emptySpace; //string for unfilled board space
    private boolean playerTurn; // Player 1 = true, Player 2 = false;
    private boolean stalemate;  // Determine whether a stalemate has been reached
    private int totalPieces; // Counts board pieces for stalemate
    private boolean game; // Whether the game is ongoing
    private String player1Name; // Player names
    private String player2Name;
    private boolean player1IsYellow; // Player colours

    public Board(String name1, String name2, boolean player1IsYellow){ //constructor, assign above variables
        this.board = new String[7][6]; // 7 by 6 board
        this.playerTurn = player1IsYellow;
        this.stalemate = false;
        this.emptySpace = "[ - ]";
        this.totalPieces = 0;
        this.game = true;
        if (this.playerTurn){
            this.player1Name = name1;
            this.player2Name = name2;
        } else {
            this.player1Name = name2;
            this.player2Name = name1;
        }
    }

    public boolean getGame() {
        return this.game;
    } // Determines whether players are in game or menu

    public void setPlayerTurn(boolean turn) {
        this.playerTurn = turn;
    } // turn variable which alternates


    public boolean GetPlayerTurn() {
        return playerTurn;
    }


    public void prepBoard(){ //create board for of empty spaces
        for (int i = 0; i < 6; i++){
            for (int j = 0; j < 7; j++){
                board[j][i] = emptySpace;
            }
        }
    }

    public void displayGrid(){ //show current board state
        for (int i = 5; i >= 0; i--){
            for (int j = 0; j < 7; j++){
                System.out.print(board[j][i]);
            }
            System.out.println();
        }
    }

    public void displayBoard(){ // guidelines for players
        System.out.println("\n  1    2    3    4    5    6    7");
        System.out.println("  |    |    |    |    |    |    |");
        displayGrid();
    }

    // Method used for inserting a new piece in a column

    public boolean AddPiece(boolean playerTurn, int column){
        int col = column - 1; //columns will line up with array indexes
        if (!board[col][5].equals(emptySpace)) return false;
        String playerPiece = playerTurn ? "[ Y ]" : "[ R ]"; // Player 1 = "Y" Player 2 = "R"

        for (int i = 0; i <= 5; i++){
            if (board[col][i].equals(emptySpace)){
                board[col][i] = playerPiece;
                totalPieces++; //add a count to total pieces on the board
                if(CheckVictory(col, i, playerPiece)) //checks if the new piece results in a victory
                {
                    //show the board, end the game
                    displayGrid();
                    if(playerTurn) {
                        System.out.println("\nThe winner is " + player1Name);
                    } else {
                        System.out.println("\nThe winner is " + player2Name);
                    }
                    this.game = false;
                    break;
                }
                if(stalemate) //board is full and no winner has been decided
                {
                    displayGrid();
                    System.out.println("The game is a draw. ");
                    this.game = false;
                }
                break;
            }
        }

        return true;
    }


    public boolean CheckVictory(int x, int y, String playerPiece) {
        //vertical check
        int chain = 1;
        for(int vCheck=1; vCheck<=3; vCheck++) { //checks the 3 pieces below the newly dropped piece
            if ((y - vCheck) >= 0 && board[x][y - vCheck].equals(playerPiece)) {
                chain++;

                if (chain >= 4) { //if the chain is 4 long
                    return true;
                }
            }
            else //chain is interrupted by an opposing piece
                break;
        }

        //horizontal check

        chain =1; //reassign chain to 1 to begin the check
        for(int lCheck=1; lCheck<=3; lCheck++) {
            if ((x - lCheck) >= 0 && board[x - lCheck][y].equals(playerPiece)) {
                //Checks up to 3 spaces left if uninterrupted
                chain++;
                if(chain >= 4) {
                    //if the horizontal chain length is 4
                    return true;
                }
            } else { //terminate the chain addition if empty space / opposing space encountered
                break;
            }
        }

        for(int rCheck=1; rCheck<=3; rCheck++) {
            if ((x + rCheck) <= 6 && board[x + rCheck][y].equals(playerPiece)) {
                //Checks up to 3 spaces right
                chain++;

                if(chain >= 4) {
                    //if the horizontal chain length is 4
                    return true;
                }

            }

            else { //terminate the chain addition if empty space / opposing space encountered
                break;
            }

        }


        //diagonal check SE check + NW check

        chain =1;
        for(int southWestCheck=1; southWestCheck<=3; southWestCheck++) {
            if ((x - southWestCheck) >= 0 && (y - southWestCheck >= 0)) { //sw check, first check if in bounds

                if (board[x - southWestCheck][y - southWestCheck].equals(playerPiece)) {
                    chain++;
                    if(chain >= 4) {
                        //if the horizontal chain length is 4
                        return true;
                    }
                }
                else { //terminates check if chain is interrupted
                    break;
                }
            }
        }

        for(int northEastCheck=1; northEastCheck<=3; northEastCheck++) {
            if ((x + northEastCheck) <= 6 && (y + northEastCheck <= 5)) { //nw check, does not reset chain length from sw
                if(board[x + northEastCheck][y + northEastCheck].equals(playerPiece))
                {
                    chain++;
                    if(chain >= 4) {
                        return true;
                    }
                }

                else { //terminates check if chain is interrupted
                    break;
                }
            }

            // System.out.println(chain);


        }

        chain = 1; //reset chain for diagonal check in opposite direction

        for(int southEastCheck=1; southEastCheck<=3; southEastCheck++) {
            if ((x + southEastCheck) <= 6 && (y - southEastCheck >= 0)) { //se check

                if (board[x + southEastCheck][y - southEastCheck].equals(playerPiece)) {
                    //increase chain length
                    chain++;
                    if(chain >= 4) {
                        //if the horizontal chain length is 4
                        return true;
                    }
                }
                else { //terminate interrupted chain
                    break;
                }
            }
        }

        for(int northWestCheck=1; northWestCheck<=3; northWestCheck++) {
            if ((x - northWestCheck) >= 0 && (y + northWestCheck <= 5)) { //nw check
                if(board[x - northWestCheck][y + northWestCheck].equals(playerPiece)) {
                    chain++;
                    if(chain >= 4) {
                        return true;
                    }
                }
                else
                {
                    break;
                }
            }
        }

        //check if the board is full after no winner has been found
        if(totalPieces >= 42)
        {
            stalemate = true;
            return false;
        }
        return false;
    }

    //validates user input only letting them enter numbers from 1 to 7
    public int checkValidColumn(String userInput)
    {
        int validNumber;
        boolean validInput = false;

        try
        {
            validNumber = Integer.parseInt(userInput);
            //entered a number but not within bounds
            if(validNumber<=7 && validNumber >=1 )
            {
                //user entered a number between 1 and 7
                return validNumber;
            }
            System.out.println("The number must be between 1 and 7");
        }
        //user entered another data type
        catch (NumberFormatException e) {
            System.out.println("Please enter a number between 1 and 7");
            return -1;
        }

        //user input is not valid
        return -1;
    }

    //check if the column is not full
    public boolean checkEmptyColumn(int userColumn)
    {
        //if the top of the column is empty
        if(board[userColumn-1][5].equals(emptySpace))
        {
            return true;
        }
        //if it is full
        System.out.println("That column is full, please choose another column. ");
        return false;
    }
}