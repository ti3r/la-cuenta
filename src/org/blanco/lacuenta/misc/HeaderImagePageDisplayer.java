package org.blanco.lacuenta.misc;

import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

public class HeaderImagePageDisplayer implements PageDisplayer {

	public View image = null;
	
	public HeaderImagePageDisplayer(View headerImage){
		image = headerImage;
	}
	
	@Override
	public void showCurrentPage(int pageIdx) {
		if (image != null){
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			params.addRule(RelativeLayout.ALIGN_PARENT_LEFT,(pageIdx == 1)?RelativeLayout.TRUE:0);
			params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,(pageIdx == 1)?0:RelativeLayout.TRUE);
			
			image.setLayoutParams(params);
			Log.d("la-cuenta", 
			((RelativeLayout.LayoutParams)image.getLayoutParams()).debug("La cuenta image layout"));
		}
	}

}
