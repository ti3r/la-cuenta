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
package org.blanco.lacuenta.misc;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.blanco.lacuenta.R;

import java.util.List;
import java.util.Map;

/**
 * Class to display the different options available in the action drawer
 * of the application
 * Created by Alexandro Blanco <ti3r.bubblenet@gmail.com>
 * on 7/31/13.
 */
public class LaCuentaActionDrawerAdapter implements ListAdapter{

    Context mContext;
    String[] mLabels;

    public LaCuentaActionDrawerAdapter(Context context, String[] labels) {
        this.mContext = context;
        this.mLabels = labels;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }

    @Override
    public boolean isEnabled(int position) {
        return true;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
        //Do nothing no data observers supported yet
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
        //Do nothing no data observers supported yet
    }

    @Override
    public int getCount() {
        return (mLabels != null)? mLabels.length : 0;
    }

    @Override
    public Object getItem(int position) {
        return mLabels[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Object item = getItem(position);
        if (convertView == null){
            int type = getItemViewType(position);
            if (type == 1){
                convertView = LayoutInflater.from(mContext)
                    .inflate(R.layout.drawer_list_item_splits,null);
            }else{
                convertView = LayoutInflater.from(mContext)
                        .inflate(R.layout.drawer_list_item_chart, null);
            }
        }
        TextView text = (TextView) convertView.findViewById(R.id.drawer_list_item_text);
        text.setText(mLabels[position]);
        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        return (position == 0)? 1 : 2;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public boolean isEmpty() {
        return (mLabels != null && mLabels.length > 0);
    }
}
