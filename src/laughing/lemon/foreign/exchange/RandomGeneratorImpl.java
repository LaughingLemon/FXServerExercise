package laughing.lemon.foreign.exchange;
//Created by Shaun

//wrapper around Math.random to allow mocking
public class RandomGeneratorImpl implements RandomGenerator {
    public double random() {
        return Math.random();
    }

    //produces a random integer between 0 and maxValue, inclusive
    public int randomNumber(int maxValue) {
        return (int) (Math.random() * (maxValue + 1));
    }
}
