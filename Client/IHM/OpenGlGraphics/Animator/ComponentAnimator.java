package Client.IHM.OpenGlGraphics.Animator;

import org.lwjgl.Sys;

import Client.IHM.OpenGlGraphics.Component;
import Client.IHM.OpenGlGraphics.ComponentListener;

public class ComponentAnimator implements Component {

	private Component c;
	private TypeAnimation typeAnimation;
	private int xBase;
	private int yBase;
	private int translateX;
	private int translateY;
	private int duree;
	private boolean autoSuppression;
	
	private double tempsPasse;
	private long lastFrame;
	
	public ComponentAnimator(Component c, TypeAnimation typeAnimation, int translateX, int translateY, int duree) {
		this.c = c;
		this.xBase = c.getX();
		this.yBase = c.getY();
		this.typeAnimation = typeAnimation;
		this.translateX = translateX;
		this.translateY = translateY;
		this.duree = duree;
		lastFrame = getTime();
	}
	
	@Override
	public void setBounds(int x, int y, int width, int height) {
	}
	@Override
	public int getX() {return 0;}
	@Override
	public int getY() {return 0;}
	@Override
	public int getWidth() {return 0;}
	@Override
	public int getHeight() {return 0;}

	@Override
	public void update() {
		if(tempsPasse < duree) {
			double delta = getDelta();
			tempsPasse += delta;
			int newX = c.getX() + (int)(delta*translateX/duree);
			int newY = c.getY() + (int)(delta*translateY/duree);
			if(xBase+translateX > xBase)
				if(newX > xBase+translateX) newX = xBase+translateX;
			else if(xBase+translateX < xBase)
				if(newX < xBase+translateX) newX = xBase+translateX;
			if(yBase+translateY > xBase)
				if(newY > yBase+translateY) newY = yBase+translateY;
			else if(yBase+translateY < yBase)
				if(newY < yBase+translateY) newY = yBase+translateY;
			
			c.setBounds(newX, newY, c.getWidth(), c.getHeight());
			//System.out.println(newX + " " + newY);
		}
		else {
			c.setBounds(xBase+translateX, yBase+translateY, c.getWidth(), c.getHeight());
			//System.out.println(xBase + " " + translateX + " " + (xBase+translateX) + " "+ c.getX());
			autoSuppression = true;
		}
	}

	@Override
	public void render() {
		
	}

	@Override
	public boolean isMouseEntered() {
		return false;
	}

	@Override
	public void doAction(ComponentListener listener) {
	}

	@Override
	public boolean isFocused() {
		return false;
	}
	
	private long getTime() {
        return (Sys.getTime() * 1000) / Sys.getTimerResolution();
    }

    private double getDelta() {
        long currentTime = getTime();
        double delta = (double) (currentTime - lastFrame);
        lastFrame = getTime();
        return delta;
    }

	@Override
	public boolean autoSupression() {
		return autoSuppression;
	}
}
