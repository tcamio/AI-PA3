import java.util.*;

// Run for 50 episodes
// For each episode, have your program print out the agent's sequence of experience
// (i.e. the ordered sequence of states/actions/rewards that ocur in the episodes)
// Use an alpha (learning rate) value of 0.1.

public class monteCarlo {

    MarkovDecisionProcess MDP = new MarkovDecisionProcess();
    
    // Learning rate
    private final double alpha = 0.1;

    private double total50Rewards = 0.0;

    Random rnd = new Random();

    // V(s)<-v(s)+alpha[Reward-v(s)]
    public void updateValue(String s, String a, double r) {
        double currentValue = MDP.states.get(s);
        double newValue = currentValue + alpha * (r - currentValue);
        MDP.states.put(s, newValue);
    }

    public void runEpisode() {

        String currentState = "RU8p";
        double totalReward = 0.0;
        ArrayList<String> stateList = new ArrayList<>();
        ArrayList<String> actionList = new ArrayList<>();
        ArrayList<Double> rewardList = new ArrayList<>();

        stateList.add(currentState);
        
        while(true){
            if (currentState == "11am") {
                break;
            }
            // Get an action
            String a = pickAction(currentState);

            // Add to action list
            actionList.add(a);

            // Get reward
            double r = MDP.getReward(currentState, a);

            // Add to reward list
            rewardList.add(r);

            // Update value
            updateValue(currentState, a, r);

            // Increment total reward
            totalReward += r;

            // get next state
            String nextState = MDP.getNextState(currentState, a);

            // Add state list
            stateList.add(nextState);

            // Update current state
            currentState = nextState;
        }
        
        this.total50Rewards += totalReward;

        System.out.println("The sequence of states is " + stateList.toString());
        System.out.println("The sequence of actions is " + actionList.toString());
        System.out.println("The sequence of rewards is " + rewardList.toString());
        System.out.println("The sum of the rewards is " + totalReward);
        System.out.println("The average reward is " + (totalReward/rewardList.size()));
    }

    // Pick action randomly
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

    
    public void run50Episodes() {
        for (int i = 0; i < 50; i++) {
            System.out.println("Episode " + (i+1));
            runEpisode();
            System.out.println();
        }

        System.out.println("The values of all states at the end of the experiment is " + MDP.states);
    }

}