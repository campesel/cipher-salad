package gui.controllers;

import exceptions.CharacterNotSupportedException;
import exceptions.InvalidKeyException;
import keys.Key;

public interface KeyGeneratorController {
	
	Key generateKey() throws CharacterNotSupportedException, InvalidKeyException;

}
