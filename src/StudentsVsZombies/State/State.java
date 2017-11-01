package StudentsVsZombies.State;

public abstract class State {
    abstract void change();
    abstract void die();
    public State copy(){ return null; }
}
