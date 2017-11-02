package StudentsVsZombies.State;

public class Dying extends State {
    public void change(){}
    public void die(){}
    public State copy(){return new Dying(); }
}
