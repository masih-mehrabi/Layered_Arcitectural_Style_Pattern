package de.tum.in.ase.pse.client.applicationlayer;

import de.tum.in.ase.pse.client.ChatClient;
import de.tum.in.ase.pse.client.presentationlayer.PresentationLayerInterface;

public class Application implements ApplicationLayerInterface {
	
	private final ChatClient chatClient;
	private PresentationLayerInterface presentationLayer;
	
	
	public Application(ChatClient chatClient) {
		this.chatClient = chatClient;
	}
	
	@Override
	public void start() {
		getPresentationLayer().start();
	}
	
	@Override
	public void stop() {
		getPresentationLayer().stop();
	}
	
	@Override
	public void sendMessage(String message) {
		getPresentationLayer().sendMessage(message);
	}
	
	@Override
	public void receiveMessage(String message) {
		getChatClient().receiveMessage(message);
	}
	
	@Override
	public void setPresentationLayer(PresentationLayerInterface presentationLayer) {
		this.presentationLayer = presentationLayer;
	}
	@Override
	public PresentationLayerInterface getPresentationLayer() {
		return this.presentationLayer;
	}
	
	public ChatClient getChatClient() {
		return chatClient;
	}
}
