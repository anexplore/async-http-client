package org.asynchttpclient.exception;

import java.io.IOException;

@SuppressWarnings("serial")
public class TooLongResponseBodyException extends IOException {
  
  public TooLongResponseBodyException (String message) {
    super(message);
  }
}
