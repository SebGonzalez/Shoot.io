package Client.IHM.Component;

import java.util.ArrayList;

import org.lwjgl.Sys;

public class MultiAnimator implements Component {

	private ArrayList<Component> c;
	private ArrayList<TypeAnimation> typeAnimation;
	private ArrayList<Integer> xBase;
	private ArrayList<Integer> yBase;
	private ArrayList<Integer> translateX;
	private ArrayList<Integer> translateY;
	private ArrayList<Integer> duree;
	private boolean autoSuppression;
	private int nbAnimation;
	private int currentAnimation;
	
	private double tempsPasse;
	private long lastFrame;
	
	public MultiAnimator(Component c, TypeAnimation typeAnimation, int translateX, int translateY, int duree) {
		this.c = new ArrayList<>();
		this.xBase = new ArrayList<>();
		this.yBase = new ArrayList<>();
		this.translateX = new ArrayList<>();
		this.translateY = new ArrayList<>();
		this.c.add(c);
		this.duree = new ArrayList<>();
		this.xBase.add(c.getX());
		this.yBase.add(c.getY());
		this.typeAnimation.add(typeAnimation);
		this.translateX.add(translateX);
		this.translateY.add(translateY);
		this.duree.add(duree);
		nbAnimation = 1;
		currentAnimation = 0;
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
		if(currentAnimation >= nbAnimation) {
			autoSuppression = true;
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
			int newX = c.get(currentAnimation).getX() + (int)(delta*translateX.get(currentAnimation)/duree.get(currentAnimation));
			int newY = c.get(currentAnimation).getY() + (int)(delta*translateY.get(currentAnimation)/duree.get(currentAnimation));
			if(xBase.get(currentAnimation)+translateX.get(currentAnimation) > xBase.get(currentAnimation))
				if(newX > xBase.get(currentAnimation)+translateX.get(currentAnimation)) newX = xBase.get(currentAnimation)+translateX.get(currentAnimation);
			else if(xBase.get(currentAnimation)+translateX.get(currentAnimation) < xBase.get(currentAnimation))
				if(newX < xBase.get(currentAnimation)+translateX.get(currentAnimation)) newX = xBase.get(currentAnimation)+translateX.get(currentAnimation);
			if(yBase.get(currentAnimation)+translateY.get(currentAnimation) > xBase.get(currentAnimation))
				if(newY > yBase.get(currentAnimation)+translateY.get(currentAnimation)) newY = yBase.get(currentAnimation)+translateY.get(currentAnimation);
			else if(yBase.get(currentAnimation)+translateY.get(currentAnimation) < yBase.get(currentAnimation))
				if(newY < yBase.get(currentAnimation)+translateY.get(currentAnimation)) newY = yBase.get(currentAnimation)+translateY.get(currentAnimation);
			
			c.get(currentAnimation).setBounds(newX, newY, c.get(currentAnimation).getWidth(), c.get(currentAnimation).getHeight());
			//System.out.println(newX + " " + newY);
		}
		else {
			c.get(currentAnimation).setBounds(xBase.get(currentAnimation)+translateX.get(currentAnimation), yBase.get(currentAnimation)+translateY.get(currentAnimation), c.get(currentAnimation).getWidth(), c.get(currentAnimation).getHeight());
			currentAnimation++;
		}
	}
	
	private void updateRotate() {
		
	}
	
	public void addAnimation(Component c, TypeAnimation typeAnimation, int translateX, int translateY, int duree) {
		this.c.add(c);
		this.duree = new ArrayList<>();
		this.xBase.add(c.getX());
		this.yBase.add(c.getY());
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
}
