import java.util.*;

public class valueIteration{
    //System.out.println("This is valueIteration class.")

    MarkovDecisionProcess MDP = new MarkovDecisionProcess();
    
    // Discount rate
    private final double lambda = 0.99;

    Random rnd = new Random();

    // V_k+1(s)<-max sigma(v(s)+alpha[Reward-v(s)])
    public void updateValue(String s, String a, double r) {
        /*
        double currentValue = MDP.states.get(s);
        double newValue = currentValue + alpha * (r - currentValue);
        MDP.states.put(s, newValue);
        */
    }

    public double calculateQ(String s, String a){

    }
}