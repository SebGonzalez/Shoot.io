package Client.IHM.Component;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;

public class GestionnaireComposant {
	private ComponentListener listener;
	private List<Component> listComponent;
	
	public GestionnaireComposant() {
		listComponent = new ArrayList<>();
	}
	
	public GestionnaireComposant(ComponentListener listener) {
		listComponent = new ArrayList<>();
		this.listener = listener;
	}
	
	public void addComponent(Component c) {
		listComponent.add(c);
	}
	
	public void render() {
		for(Component c : listComponent)
			c.render();
	}
	
	public void update() {
		clearBufferText();
		for(Component c : listComponent)
			c.update();
	}
	
	public void doAction() {
		for(Component c : listComponent)
			c.doAction(listener);
	}
	
	public void clear() {
		listComponent.clear();
	}
	
	public void clearBufferText() {
		for(Component c : listComponent) {
			if(c.isFocused()) return;
		}
		
		while (Keyboard.next()) {
			//on vide le buffer
		}
	}
}
