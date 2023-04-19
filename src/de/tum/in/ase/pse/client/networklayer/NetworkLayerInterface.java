package de.tum.in.ase.pse.client.networklayer;

import de.tum.in.ase.pse.client.presentationlayer.PresentationLayerInterface;

public interface NetworkLayerInterface {

	void sendMessage(String message);

	void receiveMessage(String message);

	void openConnection();

	void closeConnection();

	void setPresentationLayer(PresentationLayerInterface presentationLayer);
	PresentationLayerInterface getPresentationLayer();
}
