package de.tum.in.ase.pse.client;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import de.tum.in.ase.pse.client.applicationlayer.Application;
import de.tum.in.ase.pse.client.applicationlayer.ApplicationLayerInterface;
import de.tum.in.ase.pse.client.networklayer.TcpNetworkLayer;
import de.tum.in.ase.pse.client.presentationlayer.AesEncryption;

public class ChatClient {
	private static final String SERVER_HOST = "localhost";
	private static final int SERVER_PORT = 1337;
	private static final String LOGOUT_MESSAGE = ".logout";
	private Thread waitForUserInputThread;
	private String lastMessageReceived;
	private final ApplicationLayerInterface applicationLayer;
	public static void main(String[] args) {
		ChatClient chatClient = new ChatClient(SERVER_HOST, SERVER_PORT);
		chatClient.start();
	}
	
	public ChatClient(String host, int port) {
		applicationLayer = new Application(this);
		getApplicationLayer().setPresentationLayer(new AesEncryption("0123456701234567"));
		getApplicationLayer().getPresentationLayer().setNetworkLayer(new TcpNetworkLayer(host, port));
		getApplicationLayer().getPresentationLayer().getNetworkLayer().setPresentationLayer(getApplicationLayer().getPresentationLayer());
		getApplicationLayer().getPresentationLayer().getNetworkLayer().getPresentationLayer().setApplicationLayer(getApplicationLayer());
	}
	
	
	
	public void start() {
		applicationLayer.start();
		waitForUserInputThread = new Thread(this::waitForUserInput);
		waitForUserInputThread.start();
		System.out.println("ChatClient started.");
	}
	
	private void waitForUserInput() {
		try (Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8)) {
			while (!Thread.interrupted()) {
				String outgoingMessage = scanner.nextLine();
				if (LOGOUT_MESSAGE.equals(outgoingMessage)) {
					shutDown();
					return;
				}
				sendMessage(outgoingMessage);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void receiveMessage(String message) {
		lastMessageReceived = message;
		printMessage(getLastMessageReceived());
	}
	
	public void sendMessage(String outgoingMessage) {
		this.applicationLayer.sendMessage(outgoingMessage);
	}
	
	private void shutDown() {
		this.applicationLayer.stop();
	}
	
	private static void printMessage(String message) {
		System.out.println(message);
	}
	
	
	public ApplicationLayerInterface getApplicationLayer() {
		return applicationLayer;
	}
	
	public String getLastMessageReceived() {
		return lastMessageReceived;
	}
	
	
}
