package Client.IHM.OpenGlGraphics.Animator;

import org.lwjgl.Sys;

import Client.IHM.OpenGlGraphics.Component;
import Client.IHM.OpenGlGraphics.ComponentListener;

public class ComponentAnimator implements Component {

	private Component c;
	private TypeAnimation typeAnimation;
	private int xBase;
	private int yBase;
	private int animatorX;
	private int animatorY;
	private int duree;
	private boolean autoSuppression;
	private int radius;
	
	private double tempsPasse;
	private long lastFrame;
	
	public ComponentAnimator(Component c, TypeAnimation typeAnimation, int animatorX, int animatorY, int duree) {
		this.c = c;
		this.xBase = c.getX();
		this.yBase = c.getY();
		this.typeAnimation = typeAnimation;
		this.animatorX = animatorX;
		this.animatorY = animatorY;
		this.duree = duree;
		lastFrame = getTime();
	}
	
	public ComponentAnimator(Component c, TypeAnimation typeAnimation, int radius, int duree) {
		this(c, typeAnimation, radius, radius, duree);
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
		switch(typeAnimation){
		 case TRANSLATE:
			 updateTranslate();
			 break;
		 case ROTATE:
			 System.out.println("NON IMPLEMENTE");
			 break;
		 case SCALE:
			 updateScale();
			 break;
		}
	}
	
	private void updateScale() {
		if(tempsPasse < duree) {
			double delta = getDelta();
			tempsPasse += delta;
			int newX = (int)(delta*animatorX/duree);
			int newY = (int)(delta*animatorY/duree);
			if(xBase+animatorX > xBase)
				if(newX > xBase+animatorX) newX = xBase+animatorX;
			else if(xBase+animatorX < xBase)
				if(newX < xBase+animatorX) newX = xBase+animatorX;
			if(yBase+animatorY > xBase)
				if(newY > yBase+animatorY) newY = yBase+animatorY;
			else if(yBase+animatorY < yBase)
				if(newY < yBase+animatorY) newY = yBase+animatorY;
			
			c.setBounds(c.getX() - newX, c.getY() - newY, c.getWidth() + newX, c.getHeight() + newY);
			//System.out.println(newX + " " + newY);
		}
		else {
			c.setBounds(xBase-animatorX, yBase-animatorY, c.getWidth()+animatorX, c.getHeight()+animatorY);
			//System.out.println(xBase + " " + translateX + " " + (xBase+translateX) + " "+ c.getX());
			autoSuppression = true;
		}
	}

	private void updateTranslate() {
		if(tempsPasse < duree) {
			double delta = getDelta();
			tempsPasse += delta;
			int newX = c.getX() + (int)(delta*animatorX/duree);
			int newY = c.getY() + (int)(delta*animatorY/duree);
			if(xBase+animatorX > xBase)
				if(newX > xBase+animatorX) newX = xBase+animatorX;
			else if(xBase+animatorX < xBase)
				if(newX < xBase+animatorX) newX = xBase+animatorX;
			if(yBase+animatorY > xBase)
				if(newY > yBase+animatorY) newY = yBase+animatorY;
			else if(yBase+animatorY < yBase)
				if(newY < yBase+animatorY) newY = yBase+animatorY;
			
			c.setBounds(newX, newY, c.getWidth(), c.getHeight());
			//System.out.println(newX + " " + newY);
		}
		else {
			c.setBounds(xBase+animatorX, yBase+animatorY, c.getWidth(), c.getHeight());
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
	
	@Override
	public boolean getVisible() {
		return true;
	}

	@Override
	public void setVisible(boolean visible) {
	}
}
