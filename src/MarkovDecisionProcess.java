import java.uteil.set;

public class MarkovDecisionProcess<S, A> {

    Set<String> states = new HashSet<>();
    Set<String> actions = new HashSet<>();
    Set<String> actions_wo_S = new HashSet<>();

    // Add states to states
    states.add("RU8p");
    states.add("TU10p");
    states.add("RU10p");
    states.add("RD8p");
    states.add("RU8a");
    states.add("RD8a");
    states.add("TU10a");
    states.add("RU10a");
    states.add("RD10a");
    states.add("TD10a");
    states.add("11am");

    // Add actions to actions
    actions.add("P");
    actions.add("R");
    actions.add("S");

    // Add actions to actions_wo_S
    actions_wo_S.add("P");
    actions_wo_S.add("R");

    /**
	 * Get the set of states associated with the Markov decision process.
	 * 
	 * @return the set of states associated with the Markov decision process.
	 */
    Set<String> states() {
        return states;
    }

    /**
	 * Get the initial state s0 for this instance of a Markov
	 * decision process.
	 * 
	 * @return the initial state s0.
	 */
    public S getInitialState() {
        return "RU8p";
    }

    /**
	 * Get the set of actions for state s.
	 * 
	 * @param s
	 *            the state.
	 * @return the set of actions for state s.
	 */
    Set<String> getActions(String s) {
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
    public double reward(String s) {
        if (s == "P") {
            return 2.0;
        } else if (s == "R") {
            return 0.0;
        } else {
            return -1.0;
        }
    }
}
