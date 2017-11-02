package StudentsVsZombies.State;

public class Shooting extends State {
    public void change(){}
    public void die(){}
    public State copy(){return new Shooting(); }
}
