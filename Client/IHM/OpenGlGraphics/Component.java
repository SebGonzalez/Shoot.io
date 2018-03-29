package Client.IHM.OpenGlGraphics;

public interface Component {
	public void setBounds(int x, int y, int width, int height);
	public int getX();
	public int getY();
	public int getWidth();
	public int getHeight();
	public void update();
	public void render();
	public boolean isMouseEntered();
	public void doAction(ComponentListener listener);
	public boolean isFocused();
	public boolean autoSupression();
}
