package com.tinypace.mobistore.exception;

public class LoginAccountAuthException extends RuntimeException {
   
   /**
	 * 
	 */
	private static final long serialVersionUID = -4640165253813592353L;

public LoginAccountAuthException() {
       super();
   }

   public LoginAccountAuthException(String message) {
       super(message);
   }

   public LoginAccountAuthException(Throwable cause) {
       super(cause);
   }

   public LoginAccountAuthException(String message, Throwable cause) {
       super(message, cause);
   }

	
}
