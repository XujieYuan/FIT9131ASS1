import java.util.Scanner;
/**
 * Write a description of class Game here.
 *
 * @author (Xujie Yuan)
 * @version (2019.9.7)
 */
public class Game
{
    public void start()//start the program
    {
        displayMenu();
    }
    public void displayMenu()//display the start menu and give user information about the program. The program also can start from this step
    {
        Player humanPlayer = null;//humanPlayer should be null before start the game
        while(true)//while(Player humanPlayer = null){...}
        {
            System.out.println("Please select from the following options");
            System.out.println("Press 1 to register a player");
            System.out.println("Press 2 to start a new game");
            System.out.println("Press 3 to view a help menu");
            System.out.println("Press 4 to exit");
            Scanner console = new Scanner(System.in);
            String option = console.nextLine();
            System.out.println();
            switch(option)
            {
                case "1":
                humanPlayer = registerPlayer();
                break;
                case "2":
                if(humanPlayer != null)
                {
                 startGame(humanPlayer);   
                }
                else
                {
                    System.out.println("Please register before start game");
                }
                break;
                case "3":displayHelpMenu();
                break;
                case "4":
                    System.out.println("*****Exit*****");
                    System.exit(0);
                break;
                default:
                System.out.println("Invalid option, please choose again!");
                break;
            }
            
        }
    }
    public Player registerPlayer()//before start a new game, must register a human player
    {
        System.out.println("please enter your username");
        Scanner console = new Scanner(System.in);
        String name = console.nextLine();
        while(name.length() > 10 || name.length() < 3)//keep scanning until a valid name. But I wonder how to solve when enter a String.
        {
            System.out.println("Invalid name. Your user name must between 3-10 inclusive");
            name = console.nextLine();
        }
        Player humanPlayer = new Player();
        humanPlayer.setName(name);
        return humanPlayer;
    }
    public void startGame(Player humanPlayer) //main method of the whole game
    {
        Player computerPlayer = new Player();
        computerPlayer.setName("computer");
        humanPlayer.reSetRoundsWon();
        int gameRounds = wishedRound();
        int completeRounds = 0;
        while (completeRounds < gameRounds)//for(int completeRounds = 0; completeRounds < gameRounds; completeRounds++){...}
        {
            int i = completeRounds + 1;//this is for print in which round, see next line, completeRounds start from 0, so the first round should be 0+1=1
            System.out.println("Round " + i);
            humanPlayer.reSetTiles();
            humanPlayer.reSetLastTilePlayed();
            humanPlayer.reSetScore();
            computerPlayer.reSetTiles();
            computerPlayer.reSetLastTilePlayed();
            computerPlayer.reSetScore();
            int turn = decideFirstPlayer();//decide who play first(random number)
            playOneRound(humanPlayer, computerPlayer, turn);
            chooseOneRoundWinner(humanPlayer, computerPlayer);
            displayRoundsWon(humanPlayer);
            displayRoundsWon(computerPlayer);
            completeRounds++;
        }
        chooseFinalWinner(humanPlayer, computerPlayer);
    }
    public int decideFirstPlayer()//get random number to decide who play first
    {
        RNG rNG = new RNG();
        return rNG.getRandomNumber(1,0);//get random number between 0 and 1
    }
    public int wishedRound()// user can enter an integer of round he wish to play
    {
        System.out.println("Please enter the number of rounds");
        Scanner console = new Scanner(System.in);
        int gameRounds = console.nextInt();
        while(gameRounds < 1 || gameRounds >= 10)
        {
            System.out.println("The number of rounds playable must be greater than 0 and less than 10. Please enter again!");
            gameRounds = console.nextInt();
        }
        return gameRounds;
    }
    public void playOneRound(Player humanPlayer, Player computerPlayer, int turn)//play each round
    {
        int gameTotal = 0;
        int bout = 1;
        while (checkValidation(gameTotal))
        {
                System.out.println("bout " + bout);
                displayNotUsedTiles(humanPlayer);
                System.out.println();
                displayNotUsedTiles(computerPlayer);
                if (turn == 1)//if random number = 1, computer play first
                {
                    System.out.println();
                    System.out.println("*****Computer First*****");
                    gameTotal = computerPlay(humanPlayer, computerPlayer, gameTotal);
                    System.out.println("Total Value is " + gameTotal);
                    if (checkValidation(gameTotal) == false) {
                        break;
                    }
                    System.out.println("human play...");
                    gameTotal = humanPlay(humanPlayer, gameTotal);
                    System.out.println("Total Value is " + gameTotal);
                } else {
                    System.out.println();
                    System.out.println("*****Human First*****");
                    gameTotal = humanPlay(humanPlayer, gameTotal);
                    System.out.println("Total Value is " + gameTotal);
                    if (checkValidation(gameTotal) == false) {
                        break;
                    }
                    System.out.println("Computer play...");
                    gameTotal = computerPlay(humanPlayer, computerPlayer, gameTotal);
                    System.out.println("Total Value is " + gameTotal);
                }
                bout++;
        }
        System.out.println();
        System.out.println("**********Round over**********");
    }
    public boolean checkValidation(int gameTotal)//check whether the total value is over 21. When over 21, end the round.
    {
        boolean under21 = true;
        if(gameTotal >= 21)
        {
            under21 = false;
        }
        return under21;
    }
    public void displayNotUsedTiles(Player player)//display which tiles has not already used
    {
        System.out.println(player.getName() + " has");
        for(int i = 0; i < player.getTiles().length; i++)
        {
            if(player.getTiles()[i] != null)
            {
                System.out.print(player.getTiles()[i].getValue() + " ");
            }
        }
        
    }
    public int humanPlay(Player humanPlayer, int gameTotal)//after human use a tile, the program will receive a number of total value
    {
        System.out.println("Please enter the tile you want to use:");
        Scanner console = new Scanner(System.in);
        int tile = console.nextInt();
        console.nextLine();
        while(checkTileExist(humanPlayer, tile) == false)
        {
            System.out.println("Invalid choice, please enter again");
            displayNotUsedTiles(humanPlayer);
            tile = console.nextInt();
            console.nextLine();
        }
        System.out.println(humanPlayer.getName() + " plays tile " + tile );
        deleteUsedTile(humanPlayer, tile);
        gameTotal += tile;
        if(gameTotal <= 21)
        {
            humanPlayer.addScore(humanPlayer.getLastTilePlayed().getScore());
        }
        System.out.println(humanPlayer.getName() + "'s score is " + humanPlayer.getScore());
        return gameTotal;
    }
    public boolean checkTileExist(Player player, int tile)//check if the tile humanPlayer wish to use is not null
    {
        boolean valid = false;
        for(int i = 0; i < player.getTiles().length; i++)
        {
            if(player.getTiles()[i] != null)
            {
                if(tile == player.getTiles()[i].getValue())
                {
                    player.setLastTilePlayed(player.getTiles()[i]);
                    valid = true;
                    break;
                }
            }
        }
        return valid;
    }
    public void deleteUsedTile(Player player, int tile)//set used tile to null
    {
        for(int i = 0; i < player.getTiles().length; i++)
        {
            if(player.getTiles()[i] != null)
            {
                if(tile == player.getTiles()[i].getValue())//this tile in here equals to user's input in humanPlay and the tile in computerPlay method(the same as in the checkIfExist method)
                {
                    player.getTiles()[i] = null;
                }
            }
        }
    }
    public int computerPlay(Player humanPlayer, Player computerPlayer, int gameTotal)//computer use a tile and return total value. Can delete "Player humanPlayer".
    {
        int assumeComputerScore;
        int largestScore = -2;//the possible lowest score is 1 - 3 = -2
        int largestScoreTile = 1;//value 1 has the largest score 5
        int tile;//this tile here can be found in next several lines ,it will add gameTotal to check is it over 21
        for(int i = 0; i < computerPlayer.getTiles().length; i++)
        {
            if(computerPlayer.getTiles()[i] != null)
            {
                tile = computerPlayer.getTiles()[i].getValue();
                if((tile + gameTotal) <= 21)
                {
                    assumeComputerScore = computerPlayer.getScore() + computerPlayer.getTiles()[i].getScore();
                    System.out.println("Computer tries tile " + tile + ". Total Value is under 21. AssumeComputerScore is " + assumeComputerScore);
                }else
                {
                    assumeComputerScore = computerPlayer.getScore();
                    System.out.println("Computer tries tile " + tile + ". Total Value is over 21. AssumeComputerScore is " + assumeComputerScore);
                }

                if(assumeComputerScore > largestScore)
                {
                    largestScore = assumeComputerScore;
                    largestScoreTile = tile;
                    computerPlayer.setLastTilePlayed(computerPlayer.getTiles()[i]);
                    System.out.println("Computer wish to play tiles: " + computerPlayer.getLastTilePlayed().getValue());
                }
            }
        }
        System.out.println("ComputerPlayer plays tile " + largestScoreTile);
        deleteUsedTile(computerPlayer, largestScoreTile);
        gameTotal += largestScoreTile;
        if(gameTotal <= 21)
        {
            computerPlayer.addScore(computerPlayer.getLastTilePlayed().getScore());
        }
        System.out.println(computerPlayer.getName() + "'s score is " + computerPlayer.getScore());
        return gameTotal;
    }
    public int checkDiscipline(Player player)//who did not use tile with value 5 will lose 3 points
    {
        int penaltyScore = 0;
        for(int i = 0; i < player.getTiles().length; i++)
        {
            if (player.getTiles()[3] != null)
            {
                penaltyScore = 3;
            }
        }
        return penaltyScore;
    }
    public void chooseOneRoundWinner(Player humanPlayer, Player computerPlayer)//choose winner of each round
    {
        int penaltyScore;
        System.out.println("Calculating " + humanPlayer.getName() + "'s score...");
        penaltyScore = checkDiscipline(humanPlayer);
        System.out.println(humanPlayer.getName() + "'s penaltyScore is " + penaltyScore);
        humanPlayer.addScore(-penaltyScore);
        displayScore(humanPlayer);
        System.out.println("Calculating " + computerPlayer.getName() + "'s score...");
        penaltyScore = checkDiscipline(computerPlayer);
        System.out.println("computer's penaltyScore is " + penaltyScore);
        computerPlayer.addScore(-penaltyScore);
        displayScore(computerPlayer);
        if(humanPlayer.getScore() > computerPlayer.getScore())
        {
            humanPlayer.addScore(5);
            humanPlayer.addroundsWon(1);
            System.out.println();
            System.out.println("******************************************************");
            System.out.println(humanPlayer.getName() + " win 1 round and get 5 points!");
            System.out.println("******************************************************");
            System.out.println();
        }else if(humanPlayer.getScore() < computerPlayer.getScore())
        {
            computerPlayer.addScore(5);
            computerPlayer.addroundsWon(1);
            System.out.println();
            System.out.println("**************************************");
            System.out.println("Computer win 1 round and get 5 points!");
            System.out.println("**************************************");
            System.out.println();
        }else
        {
            System.out.println();
            System.out.println("**********Draw**********");
            System.out.println();
        }
    }
    public void displayScore(Player player)//display human or computer's score
    {
        System.out.println(player.getName() + "'s score is " + player.getScore());
    }
    public void displayRoundsWon(Player player)//display how many rounds human or computer has won
    {
        System.out.println(player.getName() + " won " + player.getRoundsWon() + " round(s)");
        System.out.println();
    }
    public void chooseFinalWinner(Player humanPlayer, Player computerPlayer)//who won more rounds will be the winner
    {
        if(humanPlayer.getRoundsWon() > computerPlayer.getRoundsWon())
        {
            System.out.println();
            System.out.println("*****************************************************");
            System.out.println("Congratulations!! " + humanPlayer.getName() + " is the winner!!");
            System.out.println("*****************************************************");
            System.out.println();
        }else if(humanPlayer.getRoundsWon() < computerPlayer.getRoundsWon())
        {
            System.out.println();
            System.out.println("********************************************************");
            System.out.println("Congratulations!! " + computerPlayer.getName() + " is the winner!!");
            System.out.println("********************************************************");
            System.out.println();
        }else
        {
            System.out.println();
            System.out.println("**********Draw**********");
            System.out.println();
        }
    }
    public void displayHelpMenu()//when user press 3 in start menu, display help menu
    {
        System.out.println();
        System.out.println("**********Help Menu**********");
        System.out.println("Option 1 allows a player to register themselves before playing the game.");
        System.out.println("Option 2 allows a player to play the game against the computer.");
        System.out.println("Option 3 shows a guide menu for user.");
        System.out.println("Option 4 to exit the game.");
        System.out.println();
        System.out.println("Game Rules: ");
        System.out.println();
        System.out.println("1. Each player starts the game with zero points.");
        System.out.println("2. Prior to each round, each player is given the following set of five tiles. Each tile has a value and an associated score.");
        System.out.println("3. No other tile value can be played other than the above primes.");
        System.out.println("4. Each player has the same set of 5 tiles. Each player can use each of their 5 files only ONCE in each round.");
        System.out.println("5. Before each round, the game will automatically decide if the human player goes first or the computer player goes first.");
        System.out.println("6. For each round, each player will play ONE tile, with the tile value adding to the game total for that round. ");
        System.out.println("   Provided the game total is less than or equal to 21, the player will get the score for using that tile. ");
        System.out.println("   However, if the game total is greater than 21, no score is allocated to the player who played the last tile causing the score to become greater than 21.");
        System.out.println("*****************************");
        System.out.println();
    }

}
