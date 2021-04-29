public class stateNode {
    private String state;
    private double value;

    public stateNode() {}

    public stateNode(String s, double v) {
        this.state = s;
        this.value = v;
    }

    public void setState(String s) {
        this.state = s;
    }

    public void setValue(double v) {
        this.value = v;
    }

    public String getState() {
        return this.state;
    }

    public double getValue() {
        return this.value;
    }
}
