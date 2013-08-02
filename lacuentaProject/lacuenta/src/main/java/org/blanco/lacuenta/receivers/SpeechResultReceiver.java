/***
 *  La-Cuenta for Android, a Small application that allows users to split
 *  the restaurant check between the people that assists.
 *  Copyright (C) 2011  Alexandro Blanco <ti3r.bubblenet@gmail.com>
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.blanco.lacuenta.receivers;

import android.content.Context;
import android.media.AudioManager;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;

/***
 * Class that implements the ResultReceiver Interface in order to 
 * express the calculation result though voice.
 * 
 * @author Alexandro Blanco <ti3r.bubblenet@gmail.com>
 *
 */
public class SpeechResultReceiver implements ResultReceiver, OnInitListener{

	Locale locale = null;
	TextToSpeech ttp = null;
	NumberFormat formatter= null;
	HashMap<String,String> params = new HashMap<String,String>();
	Context context = null;
	
	public SpeechResultReceiver(Context ctx, Locale locale){
		this.context = ctx;
		ttp = new TextToSpeech(ctx, this);
		formatter = NumberFormat.getCurrencyInstance(locale);
		this.locale = locale;
		params.put(TextToSpeech.Engine.KEY_PARAM_STREAM, 
				String.valueOf(AudioManager.STREAM_NOTIFICATION));
	}
	
	@Override
	public void showResult(double result) {
		ttp.speak(formatter.format(result), TextToSpeech.QUEUE_ADD, params);
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
