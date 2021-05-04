import java.util.*;

public class QLearning {
    
    MarkovDecisionProcess MDP = new MarkovDecisionProcess();

    Random rnd = new Random();

    String[] stateArray = {"RU8p", "TU10p", "RU10p", "RD10p", "RU8a", "RD8a", "TU10a", "RU10a", "RD10a", "TD10a", "11am"};
    String[] actionArray = {"P", "R", "S"};

    // Discount rate
    private final double lambda = 0.99;

    // Learning rate
    private final double alpha = 0.1;

    // Q(s_t, a_t)<-Q(s_t, a_t) + alpha[Reward_t+1 + lambda * max_a Q(s_t+1, a) - Q(s_t, a_t)]
    // Return the new Q value to assign the input state
    public double QValue(double[][] QTable, String s, String sPrime, String a) {
        //String nextState = MDP.getNextState(s, a);
        double sPrimeMaxQ = maxQValue(QTable, sPrime);
        double currentQ = findQValue(QTable, s, a);
        
        double newQValue = currentQ + alpha * (MDP.getReward(s, a) + lambda * sPrimeMaxQ - currentQ);

        return newQValue;
    }

    public double findQValue(double[][] QTable, String s, String a) {
        int ind1 = findStateInd(s);

        int ind2 = findActionInd(a);

        return QTable[ind1][ind2];
    }

    // Find the index of the input state
    public int findStateInd(String s) {
        int ind = -1;

        for (int i = 0; i < stateArray.length; i++) {
            if (s == stateArray[i]) {
                ind = i;
            }
        }

        return ind;
    }

    // Find the index of the action state
    public int findActionInd(String a) {
        int ind = -1;

        for (int i = 0; i < actionArray.length; i++) {
            if (a == actionArray[i]) {
                ind = i;
            }
        }

        return ind;
    }

    // Return the max Q value of the input state
    public double maxQValue(double[][] QTable, String sPrime) {
        int ind = findStateInd(sPrime);

        double max = -1.0;

        // Find the max Q value of the input state
        for (int j = 0 ; j < QTable[ind].length; j++) {
            if (max < QTable[ind][j]) {
                max = QTable[ind][j];
            }
        }

        return max;
    }

    // Create and initialize Q value table
    public double[][] createQTable() {
        double[][] QTable = new double[11][3];
        for (int i = 0; i < QTable.length; i ++) {
            for (int j = 0; j < QTable[i].length; j++) {
                QTable[i][j] = 0.0;
            }
        }
        return QTable;
    }

    // Run episodes untill the maximum change in Q values is less than 0.001
    public void runEpisode() {
        String currentState = "RU8p";
        ArrayList<Double> QDiffList = new ArrayList<>();

        double preQVal; // previous value
        double newQVal; // new value
        double nextQVal; // Q value for the next state
        double QValDiff; // Difference of two Q values
        double maxQValDiff; // Maximum change in Q values
        String a; // Action
        double r; // Reward
        String nextState; // next state

        double[][] QTable = createQTable();
        int numItr = 0;
        int stateInd = -1;
        int actionInd = -1;

        while(true){
            if (currentState == "RU8p") {
                System.out.println("This is episode #" + (numItr+1));
            }

            if (currentState == "11am") {
                numItr++;
                maxQValDiff = findMaxList(QDiffList);
                System.out.println("The current state is " + currentState);
                System.out.println("The maximum change in Q value in this episode was " + maxQValDiff);
                System.out.println();
                if (maxQValDiff < 0.001) {
                    break;
                }
                QDiffList.clear();
                currentState = "RU8p";
                continue;
            }

            // Get an action
            a = pickAction(currentState);

            // Get previous value (i.e. current value)
            // preValue = MDP.states.get(currentState);
            stateInd = findStateInd(currentState);
            actionInd = findActionInd(a);
            preQVal = QTable[stateInd][actionInd];


            // get next state
            nextState = MDP.getNextState(currentState, a);

            // Get the Q value for the next state
            nextQVal = maxQValue(QTable, nextState);

            // Get the new Q value for the current state
            newQVal = QValue(QTable, currentState, nextState, a);

            // Assign the new Q value to table
            QTable[stateInd][actionInd] = newQVal;

            // Get reward
            r = MDP.getReward(currentState, a);

            // Take the difference of previous and new Q values
            QValDiff = Math.abs(preQVal - newQVal);

            // Add the difference to the list
            QDiffList.add(QValDiff);

            System.out.println("The current state is " + currentState);
            System.out.println("The previous Q value is " + preQVal);
            System.out.println("The new Q value is " + newQVal);
            System.out.println("The immediate reward is " + r);
            System.out.println("The Q value for the next state is " + nextQVal);
            System.out.println();

            // Update the current state
            currentState = nextState;
        }

        System.out.println("The number of episodes is " + numItr);
        System.out.println("The final Q values are:");
        printQValues(QTable);
        System.out.println();

        System.out.print("The final optimal policy is ");
        printOptimalPolicy(QTable);
    }

    // Print optimal policy
    public void printOptimalPolicy(double[][] QTable) {
        String a;
        for (int i = 0; i < QTable.length; i++) {
            if (stateArray[i] == "TU10a" || stateArray[i] == "RU10a" || stateArray[i] == "RD10a" || stateArray[i] == "TD10a") {
                System.out.print(stateArray[i] + "=any ");
                continue;    
            } else if (stateArray[i] == "11am") {
                System.out.print(stateArray[i] + "=None ");
                continue;
            }
             a = actionArray[maxQValAction(QTable[i])];
            System.out.print(stateArray[i] + "=" + a + " ");
        }
    }

    // Return the index of action with maximum Q value
    public int maxQValAction(double[] QTable) {
        int ind = -1;
        double max = Double.NEGATIVE_INFINITY;

        for (int i = 0; i < QTable.length; i++) {
            if (max < QTable[i]) {
                max = QTable[i];
                ind = i;
            }
        }

        return ind;
    }

    // Print Q values
    public void printQValues(double[][] QTable) {
        for (int i = 0; i < QTable.length; i++) {
            for (int j = 0; j < QTable[i].length; j++) {
                System.out.print("Q(" + stateArray[i] + "," + actionArray[j] + ") = " + QTable[i][j] + ", ");
            }
            System.out.println();
        }
    }


    public double findMaxList(ArrayList<Double> myList) {
        double max = Double.NEGATIVE_INFINITY;

        for (int i = 0; i < myList.size(); i++) {
            if (max < myList.get(i)) {
                max = myList.get(i);
            }
        }

        return max;
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
}