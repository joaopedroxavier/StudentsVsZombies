package StudentsVsZombies.State;

public abstract class State {
    abstract public void change();
    abstract public void die();
    public State copy(){ return null; }
}
