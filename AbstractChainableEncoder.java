package de.uniwue.jpp.encoder;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractChainableEncoder implements Encoder {
	private Encoder delegate;
	public AbstractChainableEncoder(Encoder delegate) throws EncoderCreationException {
		if(delegate== null)throw new EncoderCreationException();

		this.delegate=delegate;

	}

	public Encoder getDelegate() {
		return  delegate;
	}
}