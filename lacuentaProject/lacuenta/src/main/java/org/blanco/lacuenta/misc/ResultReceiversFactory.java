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
package org.blanco.lacuenta.misc;

import android.content.Context;
import android.preference.PreferenceManager;
import android.widget.TextView;

import org.blanco.lacuenta.SettingsActivity;
import org.blanco.lacuenta.receivers.DialogResultReceiver;
import org.blanco.lacuenta.receivers.ResultReceiver;
import org.blanco.lacuenta.receivers.SpeechResultReceiver;
import org.blanco.lacuenta.receivers.TextViewResultReceiver;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * The class in charge of creating the result receivers that will be attached to
 * the CalculateClickListener objects in order to present the results of a
 * calculus in the way the user desires it.
 *
 * @author Alexandro Blanco <ti3r.bubblenet@gmail.com>
 * @version 1.0 08/30/2011
 *
 */
public class ResultReceiversFactory {

    /***
     * This method will return the result Receiver that will be used when
     * displaying the calculus results. It will return an instance of an object
     * that implements the ResultReceiver interface depending on established
     * application settings.
     *
     * @param context
     *            The Context object where to retrieve the user preferences
     * @param textView
     *            The TextView object where to display the result if this option
     *            has been enabled by the user (the result is not displayed on a
     *            result dialog).
     * @return A List of ResultReceiver objects based in the user settings.
     */
    public static List<ResultReceiver> getResultReceivers(Context context,
                                                          TextView textView) {
        boolean showResOnDialog = PreferenceManager
                .getDefaultSharedPreferences(context).getBoolean(
                        SettingsActivity.SHOW_RES_DIALOG_SETTING_NAME, false);
        List<ResultReceiver> result = new ArrayList<ResultReceiver>(2);
        if (showResOnDialog) {
            result.add(new DialogResultReceiver(context));
        } else {
            result.add(new TextViewResultReceiver(context, textView));
        }
        boolean textToSpeech = PreferenceManager.getDefaultSharedPreferences(
                context).getBoolean(SettingsActivity.SAY_RES_OUT_LOUD, false);
        if (textToSpeech)
            result.add(new SpeechResultReceiver(context, Locale.getDefault()));

        return result;
    }

}