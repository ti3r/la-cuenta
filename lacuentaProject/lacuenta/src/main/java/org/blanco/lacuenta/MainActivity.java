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
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prepareActionDrawer();

        SplitsFragment fragment = new SplitsFragment();
        listener.displayFragmentOnContent(fragment);
    }

    private void prepareActionDrawer() {
        getActionBar().setDisplayHomeAsUpEnabled(true);

        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.main_drawer_layout);

        mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                android.R.drawable.ic_menu_day, R.string.app_name, R.string.app_name);
        // Set the drawer toggle as the DrawerListener
        drawerLayout.setDrawerListener(mDrawerToggle);

        ListView drawerOpts = (ListView) findViewById(R.id.main_drawer_list);
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
        getMenuInflater().inflate(R.menu.main,menu);
        //Inflate the custom menu for the fragments
        if (currentFragment instanceof SplitsFragment){
            Log.d(TAG,"Adding menu for Splits");
            menu.add("Splits");
        }
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
        }
        return super.onOptionsItemSelected(item);
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
