package de.tum.in.ase.pse.client.applicationlayer;

import de.tum.in.ase.pse.client.presentationlayer.PresentationLayerInterface;

public interface ApplicationLayerInterface {

	void start();

	void stop();
	void sendMessage(String message);

	void receiveMessage(String message);
	void setPresentationLayer(PresentationLayerInterface presentationLayer);
	
	PresentationLayerInterface getPresentationLayer();
}
