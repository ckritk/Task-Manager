package test;

public interface Monitor {
    public void trackProgress(Team team);
    public <U extends Executor> void trackProgress(Team team, U user);
}
