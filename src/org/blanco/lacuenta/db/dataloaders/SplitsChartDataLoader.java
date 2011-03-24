package org.blanco.lacuenta.db.dataloaders;

import java.util.ArrayList;
import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import org.blanco.lacuenta.R;
import org.blanco.lacuenta.SplitsActivity;
import org.blanco.lacuenta.db.entities.Split;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Looper;
import android.util.Log;
import android.view.View;

/***
 * Class that will load the splits from database and build a lines
 * chart to present the results.
 * @author Alexandro Blanco <ti3r.bubblenet@gmail.com>
 *
 */
public class SplitsChartDataLoader extends AbstractSplitsDataLoader {

	double avg = 0;
	double expenses = 0;	
		
	public SplitsChartDataLoader(SplitsActivity act){
		super(act);
	}
	/***
	 * Build a List of Split Objects based on the passed load
	 * target. It makes use of the getDataCursor(String ) method.
	 * @param loadTarget The Load date target that will be used. It
	 * can be one of the following values: 
	 * SplitsActivity.TODAY_LOAD, SplitsActivity.WEEK_LOAD or
	 * SpitsActivity.MONTH_LOAD.
	 * @return A List of Split objects matching the target
	 */
	private List<Split> getSplits(String loadTarget){
		List<Split> result = new ArrayList<Split>();
		Cursor c = getDataCursor(loadTarget);
		expenses = 0;
		while (c.moveToNext()){
			Split s = Split.fromCurrentCursorPosition(c);
			result.add(s);
			expenses +=s.getResult();
		}
		avg = (result.size() > 0)? expenses / result.size(): 0;
		Log.i("La Cuenta","Splits Char Data Load. Average: "+avg);
		Log.i("La Cuenta","Splits Char Data Load. Expenses: "+expenses);
		return result;
	}
	/***
	 * Prepares the XYMultipleSeriesDataset Object that will be used in 
	 * the Chart based on the passed Split list.
	 * @param splits A List of Split Objects that will be added to the
	 * dataset
	 * @return XYMultipleSeriesDataset Object
	 */
	private XYMultipleSeriesDataset prepareDataSet(List<Split> splits){
		XYMultipleSeriesDataset dataSet = new XYMultipleSeriesDataset();
			
		CategorySeries expensesSeries = new CategorySeries(this.activity.getString(R.string.splits));
		CategorySeries averageSeries = new CategorySeries(activity.getString(R.string.average));
		CategorySeries tipSeries = new CategorySeries(activity.getString(R.string.tips));
		for(Split s: splits){
					
			expensesSeries.add(String.valueOf(s.getDate()),s.getResult());
			averageSeries.add(String.valueOf(s.getDate()),this.avg); //Add the average for each split date
			tipSeries.add(String.valueOf(s.getDate()),(s.getResult()*s.getTip())/100);
		}
		//Add the series to the dataset
		dataSet.addSeries(expensesSeries.toXYSeries());
		dataSet.addSeries(averageSeries.toXYSeries());
		dataSet.addSeries(tipSeries.toXYSeries());
		
		return dataSet;
	}
	/***
	 * It prepares the XYMultipleSeriesRenderer Object that will
	 * be used in the chart. It builds the renderer based
	 * on hardcoded properties.
	 * @return XYMultipleSeriesRenderer Object
	 */
	private XYMultipleSeriesRenderer prepareRenderer(){
		XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
		
		mRenderer.setChartTitle(this.activity.getString(R.string.expenses_label));
		mRenderer.setXTitle(this.activity.getString(R.string.records_label));
		mRenderer.setYTitle(this.activity.getString(R.string.money_sign));
		
		//Prepare the Series Renderers
		XYSeriesRenderer sRenderer = new XYSeriesRenderer();
		sRenderer.setColor(Color.GREEN);
		sRenderer.setFillPoints(true);
		sRenderer.setLineWidth(2.0f);
		
		mRenderer.addSeriesRenderer(sRenderer);
		
		XYSeriesRenderer avRenderer = new XYSeriesRenderer();
		avRenderer.setColor(Color.GRAY);
		avRenderer.setFillPoints(true);
		avRenderer.setLineWidth(2.0f);
		
		mRenderer.addSeriesRenderer(avRenderer);
		
		XYSeriesRenderer tipRenderer = new XYSeriesRenderer();
		tipRenderer.setColor(Color.YELLOW);
		tipRenderer.setFillPoints(true);
		tipRenderer.setLineWidth(2.0f);
		
		mRenderer.addSeriesRenderer(tipRenderer);
		
		return mRenderer;
	}
	
	@Override	
	protected View doInBackground(String... params) {
		List<Split> splits = getSplits(params[0]);
		if (Looper.myLooper() == null)
			Looper.prepare();
		XYMultipleSeriesDataset dataSet = prepareDataSet(splits);
		XYMultipleSeriesRenderer mRenderer = prepareRenderer();
		
		GraphicalView v = ChartFactory.getLineChartView(activity, dataSet, mRenderer);
		//ChartFactory.getBarChartView(activity, dataSet, mRenderer, Type.DEFAULT );
			//ChartFactory.getRangeBarChartView(activity, dataSet, mRenderer, Type.DEFAULT);
		//ChartFactory.getTimeChartView(activity, dataSet, mRenderer, "dd/MM");
		
		v.setBackgroundColor(Color.BLACK);
		return v;
	}

	protected void onPostExecute(View result) {
		this.activity.setContentView(result);
		this.activity.hideLoadDialog();
	}

	
	
}
