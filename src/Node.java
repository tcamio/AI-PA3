public class Node<S, A> {
	private final S state;
	private final Node<S, A> parent;
	private final A action;
	private final double rewards;

	public Node(S state) {
		this(state, null, null, 0.0);
	}

	public Node(S state, Node<S, A> parent, A action, double rewards) {
		this.state = state;
		this.parent = parent;
		this.action = action;
		this.rewards = rewards;
	}

	public Node<S, A> getParent() {
		return parent;
	}

	public A getAction() {
		return action;
	}

	public boolean isRootNode() {
		return parent == null;
	}

	public String toString() {
		return "[parent=" + parent + ", action=" + action + ", state=" + getState() + ", rewards=" + rewards + "]";
	}
}