package Client.IHM.OpenGlGraphics.Animator;

import java.util.ArrayList;
import java.util.Arrays;

import org.lwjgl.Sys;

import Client.IHM.OpenGlGraphics.Component;
import Client.IHM.OpenGlGraphics.ComponentListener;

public class PoolComponentMultiAnimator implements Component {

	private ArrayList<Component> c;
	private ArrayList<TypeAnimation> typeAnimation;
	private ArrayList<Integer> xBase;
	private ArrayList<Integer> yBase;
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
	
	public PoolComponentMultiAnimator(Component c[], TypeAnimation typeAnimation[], Integer translateX[], Integer translateY[], Integer duree[], Integer waitTime[]) {
		this.c = new ArrayList<>(Arrays.asList(c));
		this.typeAnimation = new ArrayList<>(Arrays.asList(typeAnimation));
		this.translateX = new ArrayList<>(Arrays.asList(translateX));
		this.translateY = new ArrayList<>(Arrays.asList(translateY));
		this.duree = new ArrayList<>(Arrays.asList(duree));
		this.waitTime = new ArrayList<>(Arrays.asList(waitTime));
		
		xBase = new ArrayList<>();
		yBase = new ArrayList<>();
		for(int i=0; i<this.c.size(); i++) {
			this.xBase.add(this.c.get(i).getX());
			this.yBase.add(this.c.get(i).getY());
		}
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
			for(int i=0; i<c.size(); i++) {
				int newX = c.get(i).getX() + (int)(delta*translateX.get(currentAnimation)/duree.get(currentAnimation));
				int newY = c.get(i).getY() + (int)(delta*translateY.get(currentAnimation)/duree.get(currentAnimation));
				if(xBase.get(i)+translateX.get(currentAnimation) > xBase.get(i))
					if(newX > xBase.get(i)+translateX.get(currentAnimation)) newX = xBase.get(i)+translateX.get(currentAnimation);
				else if(xBase.get(i)+translateX.get(currentAnimation) < xBase.get(i))
					if(newX < xBase.get(i)+translateX.get(currentAnimation)) newX = xBase.get(i)+translateX.get(currentAnimation);
				if(yBase.get(i)+translateY.get(currentAnimation) > xBase.get(i))
					if(newY > yBase.get(i)+translateY.get(currentAnimation)) newY = yBase.get(i)+translateY.get(currentAnimation);
				else if(yBase.get(i)+translateY.get(currentAnimation) < yBase.get(i))
					if(newY < yBase.get(i)+translateY.get(currentAnimation)) newY = yBase.get(i)+translateY.get(currentAnimation);
				
				c.get(i).setBounds(newX, newY, c.get(i).getWidth(), c.get(i).getHeight());
				}
		}
		else {
			for(int i=0; i<c.size(); i++) {
				c.get(i).setBounds(xBase.get(i)+translateX.get(currentAnimation), yBase.get(i)+translateY.get(currentAnimation), c.get(i).getWidth(), c.get(i).getHeight());
			}
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
			for(int i=0; i<c.size(); i++) {
				xBase.set(i, c.get(i).getX());
				yBase.set(i, c.get(i).getY());
			}
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
	public boolean getVisible() {return true;}
	@Override
	public void setVisible(boolean visible) {}
}
