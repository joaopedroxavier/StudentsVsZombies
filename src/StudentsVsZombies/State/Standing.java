package StudentsVsZombies.State;

public class Standing extends State {
    public void change(){}
    public void die(){}
    public State copy(){return new Standing();}
}
