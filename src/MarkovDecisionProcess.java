import java.util.*;

public class MarkovDecisionProcess {

    // Initial state
    // private stateNode s0 = new stateNode("RU8p", 0);
    // Set<stateNode> states = new HashSet<>();

    Random rnd = new Random();

    Map<String, Double> states = new HashMap<>();
    
    Set<String> actions = new HashSet<>();
    Set<String> actions_wo_S = new HashSet<>();


    // Add states to states
    public MarkovDecisionProcess() {

        this.states.put("RU8p", 0.0);
        this.states.put("TU10p", 0.0);
        this.states.put("RU10p", 0.0);
        this.states.put("RD10p", 0.0);
        this.states.put("RU8a", 0.0);
        this.states.put("RD8a", 0.0);
        this.states.put("TU10a", 0.0);
        this.states.put("RU10a", 0.0);
        this.states.put("RD10a", 0.0);
        this.states.put("RD10a", 0.0);
        this.states.put("11am", 0.0);

        // Add actions to actions
        actions.add("P");
        actions.add("R");
        actions.add("S");
        
        // Add actions to actions_wo_S
        actions_wo_S.add("P");
        actions_wo_S.add("R");

        // Add to the set of states
        /*
        this.states.add(s0);
        this.states.add(new stateNode("TU10p", 0));
        this.states.add(new stateNode("RU10p", 0));
        this.states.add(new stateNode("RD10p", 0));
        this.states.add(new stateNode("RU8a", 0));
        this.states.add(new stateNode("RD8a", 0));
        this.states.add(new stateNode("TU10a", 0));
        this.states.add(new stateNode("RU10a", 0));
        this.states.add(new stateNode("RD10a", 0));
        this.states.add(new stateNode("RD10a", 0));
        this.states.add(new stateNode("11am", 0));
        */
        
    }
    

    // Add actions to actions
    public void setActions() {
        actions.add("P");
        actions.add("R");
        actions.add("S");
    }
    

    // Add actions to actions_wo_S
    public void setActions_wo_s(){
        actions_wo_S.add("P");
        actions_wo_S.add("R");    
    }
    
    public String getNextState(String s, String a) {
        if (s == "RU8p") {
            if (a == "P") {
                return "TU10p";
            } else if (a == "R") {
                return "RU10p";
            } else if (a == "S") {
                return "RU10p";
            }
        }

        if (s == "TU10p") {
            if (a == "P") {
                return "RU10a";
            } else if (a == "R") {
                return "RU8a";
            }
        }

        if (s == "RU10p") {
            if (a == "R") {
                return "RU8a";
            } else if (a == "P") {
                if (rnd.nextBoolean()) {
                    return "RU8a";
                } else {
                    return "RU10a";
                }
            } else if (a == "S") {
                return "RD8a";
            }
        }

        if (s == "RD10p") {
            if (a == "R") {
                return "RD8a";
            } else if (a == "P") {
                if (rnd.nextBoolean()) {
                    return "RD8a";
                } else {
                    return "RD10a";
                }
            }
        }

        if (s == "RU8a") {
            if (a == "P") {
                return "TU10a";
            } else if (a == "R") {
                return "RU10a";
            } else if (a == "S") {
                return "RD10a";
            }
        }

        if (s == "RD8a") {
            if (a == "R") {
                return "RD10a";
            } else if (a == "P") {
                return "TD10a";
            }
        }
        return "11am";
    }

    /**
	 * Get the set of states associated with the Markov decision process.
	 * 
	 * @return the set of states associated with the Markov decision process.
	 */
    Map<String, Double> states() {
        return this.states;
    }

    /**
	 * Get the initial state s0 for this instance of a Markov
	 * decision process.
	 * 
	 * @return the initial state s0.
	 */
    public String getInitialState() {
        return "RU8p";
    }

    /**
	 * Get the set of actions for state s.
	 * 
	 * @param s
	 *            the state.
	 * @return the set of actions for state s.
	 */
    public Set<String> getActions(String s) {
        if (s == "RD10p" || s == "RD8a") {
            return actions_wo_S;
        } else {
            return actions;
        }
    }

    /**
	 * Return the probability of going from state s using action a to s' based
	 * on the underlying transition model P(s' | s, a).
	 * 
	 * @param sPrime
	 *            the state s' being transitioned to.
	 * @param s
	 *            the state s being transitions from.
	 * @param a
	 *            the action used to move from state s to s'.
	 * @return the probability of going from state s using action a to s'.
	 */
    public double transitionProbability(String sPrime, String s, String a) {
        if (s == "RU10p" || s == "RD10p") {
            if (a == "P") {
                return 0.5;
            }
        }
        return 1.0;
    }

    /**
	 * Get the reward associated with being in state s.
	 * 
	 * @param s
	 *            the state whose award is sought.
	 * @return the reward associated with being in state s.
	 */
    public double getReward(String s) {
        if (s == "P") {
            return 2.0;
        } else if (s == "R") {
            return 0.0;
        } else {
            return -1.0;
        }
    }
}
