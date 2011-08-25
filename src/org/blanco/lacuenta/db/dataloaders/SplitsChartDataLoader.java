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
package org.blanco.lacuenta.db.dataloaders;

import java.util.ArrayList;
import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import org.blanco.lacuenta.R;
import org.blanco.lacuenta.db.entities.Split;
import org.blanco.lacuenta.fragments.GraphFragment;

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
	
	/***
	 * Class that will hold the results of the process that will
	 * retrieve the splits from database and extra information 
	 * needed in order to build the result chart of this class;
	 * @author Alexandro Blanco <ti3r.bubblenet@gmail.com>
	 *
	 */
	private class SplitListBuildResult{
		public SplitListBuildResult(List<Split> splits, double avg, 
				double expenses, double maxExpense) {
			this.splits = splits; this.avg = avg; 
			this.expenses = expenses; this.maxExpense = maxExpense;
		}
		double avg = 0;
		double expenses = 0;	
		double maxExpense = 0;
		List<Split> splits = null;
	}
	
	public SplitsChartDataLoader(GraphFragment act){
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
	private SplitListBuildResult getSplits(String loadTarget){
		SplitListBuildResult result = new SplitListBuildResult(new ArrayList<Split>(),
				0,0,0);
		Cursor c = getDataCursor(loadTarget);
		
		while (c.moveToNext()){
			Split s = Split.fromCurrentCursorPosition(c);
			result.splits.add(s);
			result.expenses +=s.getResult();
			if (result.maxExpense==0 || result.maxExpense < s.getResult())
				result.maxExpense = s.getResult();
		}
		result.avg = (result.splits.size() > 0)? result.expenses / result.splits.size(): 0;
		Log.i("La Cuenta","Splits Char Data Load. Average: "+result.avg);
		Log.i("La Cuenta","Splits Char Data Load. Expenses: "+result.expenses);
		return result;
	}
	/***
	 * Prepares the XYMultipleSeriesDataset Object that will be used in 
	 * the Chart based on the passed Split list.
	 * @param splits A List of Split Objects that will be added to the
	 * dataset
	 * @return XYMultipleSeriesDataset Object
	 */
	private XYMultipleSeriesDataset prepareDataSet(List<Split> splits, double avg){
		XYMultipleSeriesDataset dataSet = new XYMultipleSeriesDataset();
			
		CategorySeries expensesSeries = new CategorySeries(this.activity.getString(R.string.splits));
		CategorySeries averageSeries = new CategorySeries(activity.getString(R.string.average));
		CategorySeries tipSeries = new CategorySeries(activity.getString(R.string.tips));
		for(Split s: splits){
					
			expensesSeries.add(String.valueOf(s.getDate()),s.getResult());
			averageSeries.add(String.valueOf(s.getDate()),avg); //Add the average for each split date
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
	private XYMultipleSeriesRenderer prepareRenderer(int maxValXAxis, int maxValYAxis){
		XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
		mRenderer.setXAxisMin(0);
		mRenderer.setYAxisMin(0);
		mRenderer.setXAxisMax(maxValXAxis);
		mRenderer.setYAxisMax(maxValYAxis);
		mRenderer.setChartTitle(this.activity.getString(R.string.expenses_label));
		mRenderer.setXTitle(this.activity.getString(R.string.records_label));
		mRenderer.setYTitle(this.activity.getString(R.string.money_sign));
		
		//Prepare the Series Renderers
		XYSeriesRenderer sRenderer = new XYSeriesRenderer();
		prepareXYSeriesRenderer(sRenderer, Color.GREEN, PointStyle.DIAMOND, 2f);
		mRenderer.addSeriesRenderer(sRenderer);
		
		XYSeriesRenderer avRenderer = new XYSeriesRenderer();
		prepareXYSeriesRenderer(avRenderer, Color.GRAY, PointStyle.CIRCLE, 2f);
		mRenderer.addSeriesRenderer(avRenderer);
		
		XYSeriesRenderer tipRenderer = new XYSeriesRenderer();
		prepareXYSeriesRenderer(tipRenderer, Color.YELLOW, PointStyle.SQUARE, 2f);
		mRenderer.addSeriesRenderer(tipRenderer);
		
		return mRenderer;
	}
	/***
	 * Set the specific render properties to the passed XYSeriesRenderer
	 * @param renderer the XYSeriesRenderer that the properties will be applied to
	 * @param color the int color that will be applied to the renderer
	 * @param style the PointStyle style that will be applied to the renderer
	 * @param width the float width that will be applied to the renderers line
	 */
	public void prepareXYSeriesRenderer(XYSeriesRenderer renderer, int color, 
			PointStyle style, float width){
		renderer.setFillPoints(true);
		renderer.setColor(color);
		renderer.setPointStyle(style);
		renderer.setLineWidth(width);
	}
	
	@Override	
	public View doInBackground(String... params) {
		SplitListBuildResult splits = getSplits(params[0]);
		if (Looper.myLooper() == null)
			Looper.prepare();
		XYMultipleSeriesDataset dataSet = prepareDataSet(splits.splits,splits.avg);
		//prepare the renderer with max X and Y Axises values as X=# of splits Records+1
		//and Y=Max Expense of the Splits + 10 %
		XYMultipleSeriesRenderer mRenderer = 
			prepareRenderer(splits.splits.size()+1, (int) (splits.maxExpense+(splits.maxExpense*.1)));
		
		GraphicalView v = ChartFactory.getLineChartView(activity.getActivity(), dataSet, mRenderer);
		//ChartFactory.getBarChartView(activity, dataSet, mRenderer, Type.DEFAULT );
			//ChartFactory.getRangeBarChartView(activity, dataSet, mRenderer, Type.DEFAULT);
		//ChartFactory.getTimeChartView(activity, dataSet, mRenderer, "dd/MM");
		
		v.setBackgroundColor(Color.BLACK);
		return v;
	}

	protected void onPostExecute(View result) {
		this.activity.getActivity().setContentView(result);
		this.activity.hideLoadDialog();
	}

	
	
}
