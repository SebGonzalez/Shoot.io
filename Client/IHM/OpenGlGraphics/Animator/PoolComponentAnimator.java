package Client.IHM.OpenGlGraphics.Animator;

import org.lwjgl.Sys;

import Client.IHM.OpenGlGraphics.Component;
import Client.IHM.OpenGlGraphics.ComponentListener;

public class PoolComponentAnimator implements Component {

	private Component c[];
	private TypeAnimation typeAnimation;
	private int xBase[];
	private int yBase[];
	private int translateX;
	private int translateY;
	private int duree;
	private boolean autoSuppression;
	
	private double tempsPasse;
	private long lastFrame;
	
	public PoolComponentAnimator(Component c[], TypeAnimation typeAnimation, int translateX, int translateY, int duree) {
		this.c = c;
		this.xBase = new int[c.length];
		this.yBase = new int[c.length];
		for(int i=0; i<c.length; i++) {
			this.xBase[i] = c[i].getX();
			this.yBase[i] = c[i].getY();
		}
		this.typeAnimation = typeAnimation;
		this.translateX = translateX;
		this.translateY = translateY;
		this.duree = duree;
		lastFrame = getTime();
	}

	@Override
	public void update() {
		if(tempsPasse < duree) {
			double delta = getDelta();
			tempsPasse += delta;
			
			for(int i=0; i<c.length; i++) {
				int newX = c[i].getX() + (int)(delta*translateX/duree);
				int newY = c[i].getY() + (int)(delta*translateY/duree);
				
				
				if(xBase[i]+translateX > xBase[i])
					if(newX > xBase[i]+translateX) newX = xBase[i]+translateX;
				else if(xBase[i]+translateX < xBase[i])
					if(newX < xBase[i]+translateX) newX = xBase[i]+translateX;
				if(yBase[i]+translateY > xBase[i])
					if(newY > yBase[i]+translateY) newY = yBase[i]+translateY;
				else if(yBase[i]+translateY < yBase[i])
					if(newY < yBase[i]+translateY) newY = yBase[i]+translateY;
				
				c[i].setBounds(newX, newY, c[i].getWidth(), c[i].getHeight());
			}
		}
		else {
			for(int i=0; i<c.length; i++) {
				c[i].setBounds(xBase[i]+translateX, yBase[i]+translateY, c[i].getWidth(), c[i].getHeight());
			}
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
	
	//unused method
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
	public boolean getVisible() {return true;}
	@Override
	public void setVisible(boolean visible) {}
}
