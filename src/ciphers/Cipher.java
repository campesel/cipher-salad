package ciphers;

import gui.Main;
import keys.Key;
import utils.Alphabet;

public abstract class Cipher {

	protected static Alphabet alphabet = Main.getAlphabetInUse();
	
	abstract public String encrypt(String message, Key key);

	abstract public String decrypt(String message, Key key);
	
}
