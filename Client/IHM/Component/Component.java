package Client.IHM.Component;

public interface Component {
	public void setBounds(int x, int y, int width, int height);
	public void update();
	public void render();
	public boolean isMouseEntered();
	public void doAction(ComponentListener listener);
	public boolean isFocused();
}
