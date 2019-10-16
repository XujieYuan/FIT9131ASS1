
/**
 * Write a description of class Player here.
 *
 * @author (Xujie Yuan)
 * @version (2019.9.1)
 */
public class Player
{
   //declare class fields here
   private String name;
   private int score;
   private Tile[] tiles;
   private Tile lastTilePlayed;
   private int roundsWon;
   /**
     * Constructor for objects of class Player
     */
   public Player()
   {
      name = "";
      score = 0;
      tiles = new Tile[5];
      tiles[0] = new Tile(1,5);
      tiles[1] = new Tile(2,4);
      tiles[2] = new Tile(3,3);
      tiles[3] = new Tile(5,2);
      tiles[4] = new Tile(7,1);
      lastTilePlayed = new Tile();
      roundsWon = 0;
   }
   public Player(String newName, int newScore, Tile[] newTiles, Tile newLastTilePlayed, int newRoundsWon)
   {
       name = newName;
       score = newScore;
       tiles = newTiles;
       lastTilePlayed = newLastTilePlayed;
       roundsWon = newRoundsWon;
   }
   public String getName()
   {
       return name;
   }
   public int getScore()
   {
       return score;
   }
   public Tile getLastTilePlayed()
   {
       return lastTilePlayed;
   }
   public int getRoundsWon()
   {
       return roundsWon;
   }
   public void setName(String newName)
   {
       name = newName;
   }
   public void setScore(int newScore)
   {
       score = newScore;
   }
   public void setLastTilePlayed(Tile newLastTilePlayed)
   {
       lastTilePlayed = newLastTilePlayed;
   }
   public void setRoundsWon(int newRoundsWon)
   {
       roundsWon = newRoundsWon;
   }
   public Tile[] getTiles()
   {
       return tiles;
   }
   public void setTiles(Tile[] newTiles)
   {
       tiles = newTiles;
   }
   public void reSetTiles()
   {
      tiles = new Tile[5];
      tiles[0] = new Tile(1,5);
      tiles[1] = new Tile(2,4);
      tiles[2] = new Tile(3,3);
      tiles[3] = new Tile(5,2);
      tiles[4] = new Tile(7,1);
   }
   public void reSetLastTilePlayed()
   {
       lastTilePlayed = new Tile();
   }
   public void reSetScore()
   {
       score = 0;
   }
   public void reSetRoundsWon()
   {
       roundsWon = 0;
   }
   public void addScore(int points)
   {
       score = score + points;
   }
   public void addroundsWon(int rounds)
   {
       roundsWon += rounds;
   }
   public String showState()
   {
       String state = name + "won" + roundsWon + "!";
       return state;
   }
}
