package org.blanco.lacuenta;

import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;

import org.blanco.lacuenta.fragments.SplitsFragment;

public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        SplitsFragment fragment = SplitsFragment.newInstance();
        displayFragmentOnContent(fragment);
    }


    private void displayFragmentOnContent(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.main_content,
                fragment).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
