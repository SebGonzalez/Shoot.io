package Client.IHM.Component;

public interface Component {
	public void update();
	public void render();
	public boolean isMouseEntered();
	public void doAction(ComponentListener listener);
}
