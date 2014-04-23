package laughing.lemon.foreign.exchange;
//Created by Shaun

//random number generator, which allows mocking
public interface RandomGenerator {
    public double random();

    public int randomNumber(int maxValue);
}
