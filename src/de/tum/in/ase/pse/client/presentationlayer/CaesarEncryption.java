package de.tum.in.ase.pse.client.presentationlayer;
import de.tum.in.ase.pse.client.applicationlayer.ApplicationLayerInterface;
import de.tum.in.ase.pse.client.networklayer.NetworkLayerInterface;

public class CaesarEncryption extends ChatEncryption implements PresentationLayerInterface
{
	
	private ApplicationLayerInterface applicationLayer;
	private NetworkLayerInterface networkLayer;
	private static final int ALPHABET_SIZE = 26;

	private final int key;
	

	public CaesarEncryption(int key) {
		if (key <= 0 || key >= ALPHABET_SIZE) {
			throw new IllegalArgumentException("The key must have a value between 1 to 25");
		}
		this.key = key;
	}

	@Override
	public String encrypt(String message) {
		return shift(message, key);
	}

	@Override
	public String decrypt(String message) {
		// handle special cases that are sent unencrypted
		if (this.isServerMessage(message)) {
			return message;
		}
		return findServerPrefix(message) + shift(findEncryptedMessage(message), ALPHABET_SIZE - key);
	}

	private static String shift(String message, int key) {
		StringBuilder result = new StringBuilder();
		for (char character : message.toCharArray()) {
			if (character >= 'A' && character <= 'Z') {
				character = shift('A', character, key);
			} else if (character >= 'a' && character <= 'z') {
				character = shift('a', character, key);
			}
			result.append(character);
		}
		return result.toString();
	}

	private static char shift(char offset, char input, int key) {
		return (char) (offset + (input - offset + key) % ALPHABET_SIZE);
	}
	
	@Override
	public void start() {
		getNetworkLayer().openConnection();
	
	}
	
	@Override
	public void stop() {
		getNetworkLayer().closeConnection();
	}
	
	@Override
	public void sendMessage(String message) {
		
		getNetworkLayer().sendMessage(encrypt(message));
	
	}
	
	@Override
	public void receiveMessage(String message) {
		getApplicationLayer().receiveMessage(decrypt(message));
	
	}
	
	@Override
	public ApplicationLayerInterface getApplicationLayer() {
		return applicationLayer;
	}
	
	@Override
	public void setApplicationLayer(ApplicationLayerInterface applicationLayer) {
		this.applicationLayer = applicationLayer;
	}
	
	@Override
	public NetworkLayerInterface getNetworkLayer() {
		return networkLayer;
	}
	
	@Override
	public void setNetworkLayer(NetworkLayerInterface networkLayer) {
		this.networkLayer = networkLayer;
	}
}
