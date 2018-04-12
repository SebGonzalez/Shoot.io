package Client.IHM.OpenGlGraphics;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.management.ListenerNotFoundException;

import org.lwjgl.input.Keyboard;

import Client.IHM.OpenGlGraphics.Components.DrawableComponent;
import Client.IHM.OpenGlGraphics.Components.OpenGlAlertBox;

public class GestionnaireComposant {
	private ComponentListener listener;
	private List<Component> listComponent;
	private List<Component> listComponentTmp;
	private DrawableComponent drawableComponent;
	
	public GestionnaireComposant(DrawableComponent drawableComponent) {
		listComponent = new ArrayList<>();
		listComponentTmp = new ArrayList<>();
		this.drawableComponent = drawableComponent;
	}
	
	public GestionnaireComposant(ComponentListener listener, DrawableComponent drawableComponent) {
		listComponent = new ArrayList<>();
		listComponentTmp = new ArrayList<>();
		this.listener = listener;
		this.drawableComponent = drawableComponent;
	}
	
	public List<Component> getComponent() {
		return listComponent;
	}
	
	public void addComponent(Component c) {
		listComponentTmp.add(c);
	}
	
	public void render() {
		Iterator<Component> componentIterator = listComponent.iterator();
		while (componentIterator.hasNext()) {
			Component c = componentIterator.next();
			if(c.getVisible())
				c.render();
		}
	}
	
	public void update() {
		clearBufferText();
		/*for(Component c : listComponent)
			c.update();*/
		drawableComponent.paintComponent(); 
		Iterator<Component> componentIterator = listComponent.iterator();
		while (componentIterator.hasNext()) {
			Component c = componentIterator.next();
			if(c.getVisible())
				c.update();
			if(c.autoSupression()) componentIterator.remove();
		}
		
		listComponent.addAll(listComponentTmp);
		listComponentTmp.clear();
	}
	
	public void doAction() {
		/*for(Component c : listComponent)
			c.doAction(listener);*/
		
		Iterator<Component> componentIterator = listComponent.iterator();
		while (componentIterator.hasNext()) {
			Component c = componentIterator.next();
			if(c.getVisible())
				c.doAction(listener);
		}
	}
	
	public void clear() {
		//listComponent.clear();
		listComponent = new ArrayList<>();
		/*Iterator<Component> componentIterator = listComponent.iterator();
		while (componentIterator.hasNext()) {
			componentIterator.next();
			componentIterator.remove();
		}*/
	}
	
	public void clearBufferText() {
		for(Component c : listComponent) {
			if(c.isFocused()) return;
		}
		
		while (Keyboard.next()) {
			//on vide le buffer
		}
	}

	public void removeComponent(Component component) {
		listComponentTmp.addAll(listComponent);
		listComponent.clear();
		listComponentTmp.remove(component);
	}
}