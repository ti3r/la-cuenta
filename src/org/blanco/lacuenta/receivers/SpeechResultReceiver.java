package org.blanco.lacuenta.receivers;

import java.text.NumberFormat;
import java.util.Locale;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;

/***
 * Class that implements the ResultReceiver Interface in order to 
 * express the calculation result though voice.
 * 
 * @author Alexandro Blanco <ti3r.bubblenet@gmail.com>
 *
 */
public class SpeechResultReceiver implements ResultReceiver, OnInitListener {

	Locale locale = null;
	TextToSpeech ttp = null;
	NumberFormat formatter= null;
	public SpeechResultReceiver(Context ctx, Locale locale){
		ttp = new TextToSpeech(ctx, this);
		formatter = NumberFormat.getCurrencyInstance(locale);
		this.locale = locale;
	}
	
	@Override
	public void showResult(double result) {
		ttp.speak(formatter.format(result), TextToSpeech.QUEUE_ADD, null);
	}

	@Override
	public void onInit(int status) {
		if (ttp != null && (ttp.isLanguageAvailable(locale) != TextToSpeech.LANG_NOT_SUPPORTED ||
				ttp.isLanguageAvailable(locale) != TextToSpeech.LANG_MISSING_DATA))
			ttp.setLanguage(locale);
		else // Set English a default language
			ttp.setLanguage(Locale.ENGLISH); 
	}

	
	@Override
	public void destroy(){
		// TODO Auto-generated method stub
		//finalize the text to speech service
		if (ttp != null)
			ttp.shutdown();
	}
	
}
