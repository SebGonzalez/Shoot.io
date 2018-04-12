package Client.IHM.OpenGlGraphics.Animator;

import java.util.ArrayList;
import java.util.Arrays;

import org.lwjgl.Sys;

import Client.IHM.OpenGlGraphics.Component;
import Client.IHM.OpenGlGraphics.ComponentListener;

public class ComponentMultiAnimator implements Component {

	private Component c;
	private ArrayList<TypeAnimation> typeAnimation;
	private int xBase;
	private int yBase;
	private ArrayList<Integer> translateX;
	private ArrayList<Integer> translateY;
	private ArrayList<Integer> duree;
	private ArrayList<Integer> waitTime;
	private boolean autoSuppression;
	private boolean loop;
	private boolean wait = false;
	private int nbAnimation;
	private int currentAnimation;
	
	private double tempsPasse;
	private long lastFrame;
	
	public ComponentMultiAnimator(Component c, TypeAnimation typeAnimation[], Integer translateX[], Integer translateY[], Integer duree[], Integer waitTime[]) {
		this.c = c;
		this.typeAnimation = new ArrayList<>(Arrays.asList(typeAnimation));
		this.translateX = new ArrayList<>(Arrays.asList(translateX));
		this.translateY = new ArrayList<>(Arrays.asList(translateY));
		this.duree = new ArrayList<>(Arrays.asList(duree));
		this.waitTime = new ArrayList<>(Arrays.asList(waitTime));
		this.xBase = c.getX();
		this.yBase = c.getY();
		nbAnimation = typeAnimation.length;
		currentAnimation = 0;
		lastFrame = getTime();
		loop = false;
	}

	public void setLoop(boolean loop) {
		this.loop = loop;
	}
	
	@Override
	public void update() {
		if(currentAnimation >= nbAnimation) {
			if(loop) { currentAnimation = 0; tempsPasse = 0; }
			else autoSuppression = true;
		}
		else if(wait) {
			waitUpdate();
		}
		else {
			switch(typeAnimation.get(currentAnimation)) {
				case TRANSLATE:
					updateTranslate();
				case ROTATE:
					updateRotate();
			}	
		}
		
	}
	
	private void updateTranslate() {
		if(tempsPasse < duree.get(currentAnimation)) {
			double delta = getDelta();
			tempsPasse += delta;
			int newX = c.getX() + (int)(delta*translateX.get(currentAnimation)/duree.get(currentAnimation));
			int newY = c.getY() + (int)(delta*translateY.get(currentAnimation)/duree.get(currentAnimation));
			if(xBase+translateX.get(currentAnimation) > xBase)
				if(newX > xBase+translateX.get(currentAnimation)) newX = xBase+translateX.get(currentAnimation);
			else if(xBase+translateX.get(currentAnimation) < xBase)
				if(newX < xBase+translateX.get(currentAnimation)) newX = xBase+translateX.get(currentAnimation);
			if(yBase+translateY.get(currentAnimation) > xBase)
				if(newY > yBase+translateY.get(currentAnimation)) newY = yBase+translateY.get(currentAnimation);
			else if(yBase+translateY.get(currentAnimation) < yBase)
				if(newY < yBase+translateY.get(currentAnimation)) newY = yBase+translateY.get(currentAnimation);
			
			c.setBounds(newX, newY, c.getWidth(), c.getHeight());
			//System.out.println(newX + " " + newY);
		}
		else {
			c.setBounds(xBase+translateX.get(currentAnimation), yBase+translateY.get(currentAnimation), c.getWidth(), c.getHeight());
			tempsPasse = 0;
			wait = true;
		}
	}
	
	private void updateRotate() {
		
	}
	
	private void waitUpdate() {
		if(tempsPasse < waitTime.get(currentAnimation)) {
			double delta = getDelta();
			tempsPasse += delta;
		}
		else {
			wait = false;
			tempsPasse = 0;
			xBase = c.getX();
			yBase = c.getY();
			currentAnimation++;
		}
	}
	
	public void addAnimation(TypeAnimation typeAnimation, int translateX, int translateY, int duree) {
		this.typeAnimation.add(typeAnimation);
		this.translateX.add(translateX);
		this.translateY.add(translateY);
		this.duree.add(duree);
		nbAnimation++;
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
	
	//unused function
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
	public boolean getVisible() {
		return true;
	}

	@Override
	public void setVisible(boolean visible) {
	}
}
