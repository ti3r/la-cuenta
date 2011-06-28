package org.blanco.lacuenta.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.widget.RemoteViews;

public class LaCuentaWidgetProvider extends AppWidgetProvider {
	@Override
	public void onUpdate(Context context, AppWidgetManager appwidgetmanager,
			int[] appWidgetIds) {
				RemoteViews views = new RemoteViews(context.getPackageName(), 
						org.blanco.lacuenta.R.layout.widget_initial_layout);
								
				super.onUpdate(context, appwidgetmanager, appWidgetIds);
	}
	
	
	
}
