/***
 *  La-Cuenta for Android, a Small application that allows users to split
 *  the restaurant check between the people that assists.
 *  Copyright (C) 2013  Alexandro Blanco <ti3r.bubblenet@gmail.com>
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
package org.blanco.lacuenta.dos;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import org.blanco.lacuenta.dos.R;
import org.blanco.lacuenta.SettingsActivity;
import org.blanco.lacuenta.fragments.GraphFragment;
import org.blanco.lacuenta.fragments.SplitsFragment;
import org.blanco.lacuenta.listeners.LaCuentaDrawerItemClickListener;
import org.blanco.lacuenta.misc.LaCuentaActionDrawerAdapter;

import static org.blanco.lacuenta.fragments.GraphFragment.CHART_TARGET;
import static org.blanco.lacuenta.fragments.GraphFragment.TABLE_TARGET;

/**
 * Main activity of the app.
 */
public class MainActivity extends FragmentActivity {

    /**Tag property to be used with logs */
    public static final String TAG = "LaCuenta";

    /***
     * preference name where the last version will be stored.
     */
    private static final String LAST_VERSION_RUN = "app_last_version_run_setting";
    public static final String MY_ADD_UNITID = "a151facc271fd9f";

    /** Toggle object for the open close of the drawer */
    ActionBarDrawerToggle mDrawerToggle = null;
    /** The Drawer Item Click listener in charge of swapping the fragments  */
    LaCuentaDrawerItemClickListener listener = null;
    /** The current fragment being displayed in the activity */
    Fragment currentFragment = null;

    ListView drawerOpts = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prepareActionDrawer();
        if (savedInstanceState == null) //If no saved bundle. Launch main fragment. (new start)
            launchSplitsSwap();
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        Log.d(TAG, "Set current fragment to: " + currentFragment);
        currentFragment = fragment;
        Log.d(TAG, "Invalidating options Menu");
        invalidateOptionsMenu();
    }

    @Override
    protected void onStart() {
        AlertDialog ad = checkInitialDisplay();
        if (ad != null)
            ad.show();
        super.onStart();
    }

    /**
     * Launch the initial fragment when app start.
     */
    private void launchSplitsSwap() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                listener.onItemClick(null,null,0,0);
                View v =
                    drawerOpts.getAdapter().getView(0,null,null);
                Log.d(TAG, String.valueOf(v));
            }
        },100);

    }

    /**
     * Prepares the action drawer for the application.
     */
    private void prepareActionDrawer() {
        if (getActionBar() != null)
            getActionBar().setDisplayHomeAsUpEnabled(true);

        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.main_drawer_layout);

        mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                android.R.drawable.ic_menu_day, R.string.app_name, R.string.app_name);
        // Set the drawer toggle as the DrawerListener
        drawerLayout.setDrawerListener(mDrawerToggle);

        drawerOpts = (ListView) findViewById(R.id.main_drawer_list);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_list_item_single_choice,
//                getResources().getStringArray(R.array.main_drawer_options));
        LaCuentaActionDrawerAdapter adapter = new LaCuentaActionDrawerAdapter(this,
                getResources().getStringArray(R.array.main_drawer_options));
        drawerOpts.setAdapter(adapter);

        listener = new LaCuentaDrawerItemClickListener(getSupportFragmentManager(),drawerLayout,
                drawerOpts);
        drawerOpts.setOnItemClickListener(listener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        Log.d(TAG,"Creating options menu. Value of current fragment: "+currentFragment);
        //Inflate the custom menu for the fragments
        if (currentFragment instanceof SplitsFragment){
            Log.d(TAG,"Adding menu for Splits");
            getMenuInflater().inflate(R.menu.splits_fragment_menu, menu);
        }else if (currentFragment instanceof GraphFragment){
            Log.d(TAG,"Adding menu for graphs");
            getMenuInflater().inflate(R.menu.graph_fragment_menu, menu);
        }
        //Inflate the main menu (Settings)
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()){
            case R.id.action_settings:
                launchSettingsActivity();
                return true;
            case R.id.splits_fragment_save_expense_item:
                saveResultToDb();
                return true;
            case R.id.graph_action_bar_btn_change_target:
                changeGraphTarget(item);
                return true;
            case R.id.main_activity_main_menu_exit_item:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Calls the changeDisplayTarget method for the GraphFragment if this is the
     * current fragment being displayed in order to change the display target of the
     * fragment. Then it sets the corresponding icon to the menu item to display
     * correct UI.
     * @param item The menu item that triggered this function and where correct next icon will be
     *             set
     */
    private void changeGraphTarget(MenuItem item) {
        if (!(currentFragment instanceof GraphFragment)){
            Log.w(TAG,"Click on change graph target. but not Current GraphFragment found");
            return;
        }
        GraphFragment frag = ((GraphFragment)currentFragment);
        int disp = frag.getDisplayTarget();
        frag.changeDisplayTarget( ( disp == CHART_TARGET) ? TABLE_TARGET :
                CHART_TARGET
        );
        item.setIcon( (disp == CHART_TARGET) ? R.drawable.chart : R.drawable.table);
    }

    /**
     * Calls the saveResultToDb method for the SplitsFragment if this is the
     * current fragment in order to store the result of the split to db.
     */
    private void saveResultToDb() {
        if (!(currentFragment instanceof SplitsFragment)){
            Log.w(TAG,"Click on save result but current fragment is not Splits. " +
                    "Ignoring Check this");
            return;
        }
        SplitsFragment frag = (SplitsFragment) currentFragment;
        String msg = frag.saveResultToDb(this);
        if (!"".equalsIgnoreCase(msg)){
            Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Launches the settings activity for the user
     */
    private void launchSettingsActivity() {
        Intent settsIntent = new Intent(SettingsActivity.INTENT_ACTION_NAME);
        startActivity(settsIntent);
    }

    /***
     * This method is designed to check when the application starts for the
     * first time on a new version and display the relative changeLog to the
     * user in order to let him/her know the changes applied in the version.
     *
     * @return the AlertDialog class that should be shown in case the initial
     *         message needs to be displayed, null otherwise
     */
    protected AlertDialog checkInitialDisplay() {
        try {
            PackageInfo pkgInfo;
            pkgInfo = getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(getApplicationContext().getPackageName(), 0);
            int versionCode = pkgInfo.versionCode;
            int lastVersion = PreferenceManager.getDefaultSharedPreferences(
                    this).getInt(LAST_VERSION_RUN, 0);
            if (lastVersion < versionCode) // if last version is less than the
            // current version of the manifest
            {
                // Show the initial Dialog
                int CLResourceId = getResources().getIdentifier(
                        "changelog_" + versionCode, "array",
                        "org.blanco.lacuenta");
                if (CLResourceId > 0) {
                    String lines[] = getResources()
                            .getStringArray(CLResourceId);
                    StringBuilder text = new StringBuilder();
                    for (String line : lines)
                        text.append(line).append("\n");

                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setCancelable(false);
                    builder.setPositiveButton(getString(R.string.str_ok),
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    dialog.dismiss();
                                }
                            });
                    builder.setMessage(text.toString());
                    builder.setTitle(R.string.change_log_dialog_title);

                    // Store the last run version on the preferences
                    PreferenceManager.getDefaultSharedPreferences(this).edit()
                            .putInt(LAST_VERSION_RUN, versionCode).commit();
                    return builder.create();
                } else {
                    Log.i(TAG,
                            "Error retrieving resource id, 0 returned for change_log"
                                    + versionCode);
                    return null;
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "could not check the version of the app", e);
        } catch (NullPointerException e){
            Log.w(TAG, "could not check the version of the app. NullPointerEx");
        }
        return null;
    }

}
