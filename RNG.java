
/**
 * Write a description of class RNG here.
 *
 * @author (Xujie Yuan)
 * @version (2019.9.7)
 */
public class RNG
{
    private int maximumValue;
    private int minimumValue;
    /**
     * Constructor for objects of class RNG
     */
    public RNG()
    {
        maximumValue = 1;
        minimumValue = 0;
    }
    public int getMaximumValue()
    {
        return maximumValue;
    }
    public int getMinimumValue()
    {
        return minimumValue;
    }
    public void setMaximumValue(int newMaximumValue)
    {
        maximumValue = newMaximumValue;
    }
    public void setMinimumValue(int newMinimumValue)
    {
        minimumValue = newMinimumValue;
    }
    public int getRandomNumber(int maximumValue, int minimumValue)
    {
         return minimumValue + (int)(Math.random()*(maximumValue-minimumValue+1));
    }
    
    public void checkRandomNumber()//check whether the method getRandomNumber work well
    {
        for(int i = 0; i < 20; i++)
        {
            int turn = getRandomNumber(3, 1);
            System.out.println(turn);
            
        }
    }
    
}
