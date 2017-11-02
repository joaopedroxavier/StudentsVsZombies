package StudentsVsZombies.State;

public class Eating extends State {
    public void change(){}
    public void die(){}
    public State copy(){return new Eating(); }
}
