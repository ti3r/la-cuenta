package org.blanco.lacuenta;

/***
 * @author Alexandro Blanco <ti3r.bubblenet@gmail.com>
 * Interface that must show the result of a bill split process
 * when triggered.
 * Known subclasses: TextViewResultReceiver
 */
public interface ResultReceiver {

	public void showResult(double result);	
}
