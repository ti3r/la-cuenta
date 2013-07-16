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
package org.blanco.lacuenta.fragments;

import org.blanco.lacuenta.R;
import org.blanco.lacuenta.SettingsActivity;
import org.blanco.lacuenta.db.entities.Split;
import org.blanco.lacuenta.listeners.CalculateClickListener;
import org.blanco.lacuenta.misc.NumPad;
import org.blanco.lacuenta.misc.ResultReceiversFactory;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.text.method.DigitsKeyListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * The Fragment that will handle all the calculus of a split.
 *
 * Note: The most code of the main Activity has been migrated to this class.
 *
 * @author Alexandro Blanco <ti3r.bubblenet@gmail.com>
 * @version 1.0 08/22/2011
 *
 */
public class SplitsFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mainLayout = inflater.inflate(R.layout.main_layout, null);
        // save the sate of the GUI components
        initComponents();
        return mainLayout;
    }

    /***
     * Initialises the components for the current activity. Visual and non
     * visual members of this context.
     */
    public void initComponents() {
        edtTotal = (EditText) mainLayout
                .findViewById(R.id.main_activity_edt_bill_total);
        // Set the key listener when the orientation is landscape and the input
        // is done through the softkeyboard
        if (getActivity().getWindowManager().getDefaultDisplay()
                .getOrientation() == Configuration.ORIENTATION_LANDSCAPE) {
            edtTotal.setKeyListener(new DigitsKeyListener(false, true));
        }
        txtResult = (TextView) mainLayout
                .findViewById(R.id.main_activity_txt_result);
        spnTip = (Spinner) mainLayout.findViewById(R.id.main_activity_spn_tip);
        spnPeople = (Spinner) mainLayout
                .findViewById(R.id.main_activity_spn_people);
        btnCalculate = (Button) mainLayout
                .findViewById(R.id.main_activity_btn_calculate);
        clickListener = new CalculateClickListener(edtTotal, spnTip, spnPeople);
        btnCalculate.setOnClickListener(clickListener);
        numPad = (NumPad) mainLayout.findViewById(R.id.main_activity_num_pad);

        if (numPad != null) // Landscape layout will not have numPad
            numPad.setText(edtTotal);
    }

    /***
     * Load the control values from a preferences file in order to present the
     * user the same interface that when it left
     */
    private void loadControlPreferences() {
        edtTotal.setText(getActivity().getSharedPreferences(
                "control_preferences", Activity.MODE_PRIVATE).getString(
                "edtTotal", "0"));
        spnTip.setSelection(getActivity().getSharedPreferences(
                "control_preferences", Activity.MODE_PRIVATE).getInt("spnTip",
                0));
        spnPeople.setSelection(getActivity().getSharedPreferences(
                "control_preferences", Activity.MODE_PRIVATE).getInt(
                "spnPeople", 0));
    }

    @Override
    public void onStart() {
        boolean savePrefs = PreferenceManager.getDefaultSharedPreferences(
                getActivity()).getBoolean(
                SettingsActivity.SAVE_PREFS_SETTING_NAME, false);
        if (savePrefs) {
            loadControlPreferences();
        }
        // set the result receivers of the calculus
        clickListener.setResultReveivers(ResultReceiversFactory
                .getResultReceivers(getActivity(), txtResult));
        // Set the visibility of the result label
        boolean showResOnDialog = PreferenceManager
                .getDefaultSharedPreferences(getActivity()).getBoolean(
                        SettingsActivity.SHOW_RES_DIALOG_SETTING_NAME, false);
        this.txtResult.setVisibility((showResOnDialog) ? View.GONE
                : View.VISIBLE);
        super.onStart();
    }

    @Override
    public void onPause() {
        boolean savePrefs = PreferenceManager.getDefaultSharedPreferences(
                getActivity()).getBoolean(
                SettingsActivity.SAVE_PREFS_SETTING_NAME, false);
        if (savePrefs)
            saveControlPreferences();
        super.onPause();
    }

    /***
     * Save the control values in a preferences file in order to be restored on
     * the next application execution.
     */
    private void saveControlPreferences() {
        getActivity()
                .getSharedPreferences("control_preferences",
                        Activity.MODE_PRIVATE).edit()
                .putString("edtTotal", edtTotal.getText().toString()).commit();
        getActivity()
                .getSharedPreferences("control_preferences",
                        Activity.MODE_PRIVATE).edit()
                .putInt("spnTip", spnTip.getSelectedItemPosition()).commit();
        getActivity()
                .getSharedPreferences("control_preferences",
                        Activity.MODE_PRIVATE).edit()
                .putInt("spnPeople", spnPeople.getSelectedItemPosition())
                .commit();
    }

    @Override
    public void onDestroy() {
        // finalize the result receivers, it will free the text to speech
        // service in case it is activated
        if (clickListener != null)
            clickListener.Destroy();
        super.onDestroy();
    }

    public String saveResultToDb(Context ctx) {
        Split res = clickListener.getResult();
        if (res != null) {
            Uri uri = Split.insert(res, ctx);
            StringBuilder msg = new StringBuilder(getString(R.string.record));
            msg.append(" ").append(ContentUris.parseId(uri)).append(" ")
                    .append(getString(R.string.created));
            return msg.toString();
        } else
            return getString(R.string.no_calculus_done_yet_msg);
    }

    /* Class members */
    View mainLayout = null;
    /* GUI Components */
    EditText edtTotal = null;
    Button btnCalculate = null;
    Spinner spnTip = null;
    Spinner spnPeople = null;
    TextView txtResult = null;
    NumPad numPad = null;
	/* End of GUI Components */

    CalculateClickListener clickListener = null;
	/* End of Class members */
}