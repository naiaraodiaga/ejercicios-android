package com.naiaraodiaga.earthquake;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class LazyAdapter extends BaseAdapter{
	private Activity activity;
    private ArrayList<HashMap<String, String>> data;
	private static LayoutInflater inflater = null;
    
	public LazyAdapter() {
	}
	
	public LazyAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
        activity = a;
        data=d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
	
	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View vi=convertView;
        if(convertView==null){
            vi = inflater.inflate(R.layout.list_row_fragment, null);
        }    
 
        TextView mag = (TextView)vi.findViewById(R.id.magnitude); 
        TextView place = (TextView)vi.findViewById(R.id.place); 
        TextView time = (TextView)vi.findViewById(R.id.time); 
 
        HashMap<String, String> quake = new HashMap<String, String>();
        quake = data.get(position);
 
        // Setting all values in listview
//        mag.setText(quake.get(CustomizedListView.MAGNITUDE));
//        place.setText(quake.get(CustomizedListView.PLACE));
//        time.setText(quake.get(CustomizedListView.TIME));
        
        return vi;		
		
	}

}
