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
package org.blanco.lacuenta;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.Toast;

import org.blanco.lacuenta.fragments.SplitsFragment;
import org.blanco.lacuenta.listeners.LaCuentaDrawerItemClickListener;

/**
 * Main activity of the app.
 */
public class MainActivity extends FragmentActivity
        implements LaCuentaDrawerItemClickListener.LaCuentaFragmentChangedListener {

    /**Tag property to be used with logs */
    public static final String TAG = "LaCuenta";

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

    private void launchSplitsSwap() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                listener.onItemClick(null,null,0,0);
                View v =
                    drawerOpts.getAdapter().getView(0,null,null);
                Log.d(TAG, String.valueOf(v));
                CheckedTextView v2 = (CheckedTextView) v;
                v2.setChecked(true);
            }
        },500);

    }

    private void prepareActionDrawer() {
        getActionBar().setDisplayHomeAsUpEnabled(true);

        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.main_drawer_layout);

        mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                android.R.drawable.ic_menu_day, R.string.app_name, R.string.app_name);
        // Set the drawer toggle as the DrawerListener
        drawerLayout.setDrawerListener(mDrawerToggle);

        drawerOpts = (ListView) findViewById(R.id.main_drawer_list);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_single_choice,
                getResources().getStringArray(R.array.main_drawer_options));
        drawerOpts.setAdapter(adapter);

        listener = new LaCuentaDrawerItemClickListener(getSupportFragmentManager(),drawerLayout,
                drawerOpts, this);
        drawerOpts.setOnItemClickListener(listener);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        //Inflate the custom menu for the fragments
        if (currentFragment instanceof SplitsFragment){
            Log.d(TAG,"Adding menu for Splits");
            getMenuInflater().inflate(R.menu.splits_fragment_menu,menu);
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
            case R.id.main_activity_main_menu_exit_item:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveResultToDb() {
        if (!(currentFragment instanceof SplitsFragment)){
            Log.w(TAG,"Click on save result but current fragment is not Splits. " +
                    "Ignoring Check this");
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

    @Override
    public void onFragmentChanged(Fragment fragment) {
        Log.d(TAG,"Invalidating options Menu");
        currentFragment = fragment;
        invalidateOptionsMenu();
    }
}
