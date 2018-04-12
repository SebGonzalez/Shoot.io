package Client.IHM.OpenGlGraphics.Components;

import Client.IHM.OpenGlGraphics.Component;
import Client.IHM.OpenGlGraphics.GestionnaireComposant;

public interface OpenGlPanel extends DrawableComponent {
	public void displayComponent();
	public void actionPerformed(Component c);
}
