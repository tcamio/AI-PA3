import java.util.*;

public class valueIteration{
    //System.out.println("This is valueIteration class.")

    MarkovDecisionProcess MDP = new MarkovDecisionProcess();
    
    // Discount rate
    private final double lambda = 0.99;

    Random rnd = new Random();

    // V_k+1(s)<-max_a sigma(v(s)+lambda * [Reward-v(s')]) ??
    // V_k+1(s)<-max_a Q(s, a)
    // Return the dictionaly of action and Q value
    public Map<String, Double> BellmanUpdateVector(String s) {
        Set<String> actions = MDP.getActions(s);

        // Vector of values for each action
        Map<String, Double> V= new HashMap<>();

        // double newValue;

        if (actions.size() == 2) {
            double Q1 = QValue(s, "P");
            double Q2 = QValue(s, "R");
            // newValue = Math.max(Q1, Q2);
            // MDP.states.put(s, newValue);
            // return newValue;
            V.put("P", Q1);
            V.put("R", Q2);
            return V;
        }
        //double currentValue = MDP.states.get(s);
        //double newValue = currentValue + alpha * (r - currentValue);
        
        double Q1 = QValue(s, "P");
        double Q2 = QValue(s, "R");
        double Q3 = QValue(s, "S");
        // newValue = Math.max(Q1, Q2);
        // newValue = Math.max(newValue, Q3);
        
        //MDP.states.put(s, newValue);
        V.put("P", Q1);
        V.put("R", Q2);
        V.put("S", Q3);
        return V;
        //return newValue;
    }

    // Q(s,a)<-sigma[P(s'|s,a)(reward + lambda * v(s'))]
    public double QValue(String s, String a){
        // Set<String> actions = MDP.getActions(s);
        
        if (s == "RU10p" && a == "P") {
            double firstTerm = 0.5 * (MDP.getReward(s, a) + lambda * MDP.states.get("RU8a"));
            double secondTerm = 0.5 * (MDP.getReward(s, a) + lambda * MDP.states.get("RU10a"));
            double QValue = firstTerm + secondTerm;
            return QValue;

        } else if (s == "RD10p" && a == "P") {
            double firstTerm = 0.5 * (MDP.getReward(s, a) + lambda * MDP.states.get("RD8a"));
            double secondTerm = 0.5 * (MDP.getReward(s, a) + lambda * MDP.states.get("RD10a"));
            double QValue = firstTerm + secondTerm;
            return QValue;
        }

        String nextState = MDP.getNextState(s, a);

        double QValue = 1 * (MDP.getReward(s, a) + lambda * MDP.states.get(nextState));

        return QValue;
    }


    public void runIteration() {
        // Number of iteration
        // int numItr =0;

        //double maxValueDiff = Double.NEGATIVE_INFINITY;
        //double valueDiff = Double.NEGATIVE_INFINITY;

        String currentState = "RU8p";
        String[] stateArray = {"RU8p", "TU10p", "RU10p", "RD10p", "RU8a", "RD8a", "TU10a", "RU10a", "RD10a", "TD10a", "11am"};
        double[] valueDiffArray = new double[11];
        String[] optimalPolicy = new String[11];
        int i = 0;
        int len = stateArray.length;
        String nextState = "";
        

        
        while (true) {
            if (currentState == "11am") {
                if ((i % len == 10) && checkConvergence(valueDiffArray)) {
                    break;
                }
                System.out.println("The array of value difference is " + Arrays.toString(valueDiffArray));
                System.out.println("The checkConvergence function return " + checkConvergence(valueDiffArray));
                System.out.println("The curent i is " + (i%len));
                System.out.println();
                nextState = stateArray[(i+1) % len];
                i += 1;
                currentState = nextState;
                continue;
            }

            // Get previous value (i.e. current value)
            double preValue = MDP.states.get(currentState);

            // Get the dictionaly of action key and Q value
            Map<String, Double> V = BellmanUpdateVector(currentState);

            String a = selectOptimalAction(V);

            double newValue = V.get(a);

            System.out.println("The current state is " + currentState);
            //System.out.println("The vector of Bellman Update is " + V);

            System.out.println("The previous value is " + preValue);
            System.out.println("The new value is " + newValue);
            System.out.println("The estimated value of each action is " + V);
            System.out.println("The action selected is " + a);
            optimalPolicy[i % len] = a;

            System.out.println("The array of value difference is " + Arrays.toString(valueDiffArray));
            System.out.println("The checkConvergence function return " + checkConvergence(valueDiffArray));
            System.out.println("The curent i is " + i);
            System.out.println("The curent index is " + (i%len));
            
            System.out.println();

            nextState = stateArray[(i+1) % len];
            
            valueDiffArray[i % len] = Math.abs(preValue - newValue);

            // Assign new value to v(s)
            MDP.states.put(currentState, newValue);

            // valueDiff = Math.abs(preValue - newValue);

            /*
            if (maxValueDiff < valueDiff && (currentState != "TU10a" || currentState != "RU10a" || currentState != "RD10a" || currentState != "TD10a")) {
                maxValueDiff = valueDiff;
            }
            */

            currentState = nextState;
            i += 1;
        }
        
        

        // The number of times updated each state
        System.out.println("The number of iterations is " + (i / len + 1));

        System.out.println("The final values for each state is " + MDP.states);
        System.out.print("The final optimal policy is");
        for (int j = 0; j < optimalPolicy.length; j++) {
            if (stateArray[j] == "TU10a" || stateArray[j] == "RU10a" || stateArray[j] == "RD10a" || stateArray[j] == "TD10a") {
                System.out.print(" " + stateArray[j] + "=any");
                continue;    
            }
            System.out.print(" " + stateArray[j] + "=" + optimalPolicy[j]);
        }
        System.out.println();
    }

    // Return optimal action which has the maximum Q value
    public String selectOptimalAction(Map<String, Double> V) {
        String a = "";
        double maxQ = Double.NEGATIVE_INFINITY;

        for (Map.Entry<String, Double> entry: V.entrySet()) {
            if (maxQ < entry.getValue()) {
                maxQ = entry.getValue();
                a = entry.getKey();
            }
        }

        return a;
    }

    public boolean checkConvergence(double[] arr) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > 0.001) {
                return false;
            }
        }
        return true;
    }

    /*
    public Map<String, String> getPolicy(Map<String, Double> V) {
        Map<String, String> policy = new HashMap<>();
        String a = "";

        for (Map.Entry<String, Double> entry; V.entrySet()) {
            entry.get
        }
    }
    */
}