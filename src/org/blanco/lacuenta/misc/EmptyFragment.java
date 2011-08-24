package org.blanco.lacuenta.misc;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.view.SurfaceView;
import android.view.View;
/**
 * Fragment that returns an empty view, Just for testing purposes
 * 
 * @author Alexandro Blanco <ti3r.bubblenet@gmail.com>
 * @version 1.0	08/22/2011
 *
 */
public class EmptyFragment extends Fragment {

	@Override
	public View getView() {
		SurfaceView v = new SurfaceView(getActivity());		
		v.setBackgroundColor(Color.BLUE);
		return v;
	}
	
	
}
