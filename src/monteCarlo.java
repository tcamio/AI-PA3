import java.util.*;

// Run for 50 episodes
// For each episode, have your program print out the agent's sequence of experience
// (i.e. the ordered sequence of states/actions/rewards that ocur in the episodes)
// Use an alpha (learning rate) value of 0.1.

public class monteCarlo {

    MarkovDecisionProcess MDP = new MarkovDecisionProcess();
    
    // Learning rate
    private final double alpha = 0.1;

    Random rnd = new Random();

    // V(s)<-v(s)+alpha[Reward-v(s)]
    public void updateValue(String s, String a) {
        double currentValue = MDP.states.get(s);
        double reward = MDP.getReward(s);
        double newValue = currentValue + alpha * (reward - currentValue);
        MDP.states.put(s, newValue);
    }

    public void runEpisode() {    
        // MDP.setStates();

        System.out.println("Initial values: " + MDP.states);

        // MDP.states.put("RU8p", 1.0);

        // System.out.println("States and value: " + MDP.states);

        String currentState = "RU8p";
        double totalReward;

        while(true){
            if (currentState == "11am") {
                break;
            }
            // Get an action
            String a = pickAction(currentState);

            // Get reward
            double r = getReward(currentState, a);

            // get next state
            String nextState = MDP.getNextState(currentState, a);

            currentState = nextState;
        }

        System.out.println("The sequence of states is ");
        System.out.println("The sequence of actions is ");
        System.out.println("The sequence of rewards is ");
        System.out.println("The sum of the rewards is ");
    }


    public String pickAction(String currentState) {
        Set<String> actions = MDP.getActions(currentState);

        // When there are only 2 possible actions (i.e. current state is TU10p, RD10p, or RD8a)
        if (actions.size() == 2) {
            if (rnd.nextBoolean()) {
                return "R";
            } else {
                return "P";
            }
        }

        int i = rnd.nextInt(3);

        // When there are 3 possible actions
        if (i == 0) {
            return "R";
        } else if (i == 1) {
            return "P";
        } else {
            return "S";
        }
    }

    public double getReward(String s, String a) {
            if (s == "TU10a") {
                return -1.0;
            } else if (s == "RU10a") {
                return 0.0;
            } else if (s == "RD10a") {
                return 4.0;
            } else if (s == "TD10a") {
                return 3.0;
            }
    
            if (a == "P") {
                return 2.0;
            } else if (a == "R") {
                return 0.0;
            } else { // when a == "S"
                return -1.0;
            }

    }


    //System.out.println("This is monteCarlo class.");
}