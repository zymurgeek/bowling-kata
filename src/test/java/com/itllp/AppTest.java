package com.itllp;

import java.io.PrintStream;
import java.util.ResourceBundle;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

/**
 * Unit test for simple App.
 */
@ExtendWith(MockitoExtension.class)
public class AppTest {

	protected static final ResourceBundle testMessages = ResourceBundle.getBundle("testMessages");

	@Captor
	private ArgumentCaptor<String> captorLoggingMessage;

	PrintStream errorStream;
	PrintStream loggingStream;

	@BeforeEach
	public void setUp() {
		errorStream = System.err;
		loggingStream = spy(errorStream);
		System.setErr(loggingStream);
	}

	@AfterEach
	public void tearDown() {
		System.setErr(errorStream);
	}

	@Test
	public void testApp() {
		App.main(new String[] {});

		verify(loggingStream, atLeastOnce()).println(captorLoggingMessage.capture());
		final String loggingMessage = captorLoggingMessage.getValue();
		assertEquals("[main] INFO App - Hello world!", loggingMessage);
	}

	@Test
	public void testAppWithName() {
		App.main(new String[] {"John"});

		verify(loggingStream, atLeastOnce()).println(captorLoggingMessage.capture());
		final String loggingMessage = captorLoggingMessage.getValue();
		assertEquals("[main] INFO App - Hello John! /by " + testMessages.getString("artifactId"), loggingMessage);
	}
}