package com.programoo.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.programoo.imgdict.R;
import com.programoo.models.Customer;

public class CustomerAdapter extends ArrayAdapter<Customer> {
    private final String MY_DEBUG_TAG = "CustomerAdapter";
    private ArrayList<Customer> items;
    private ArrayList<Customer> itemsAll;
    private ArrayList<Customer> suggestions;
    private int viewResourceId;

    public CustomerAdapter(Context context, int viewResourceId, ArrayList<Customer> items) {
        super(context, viewResourceId, items);
        this.items = items;
        this.itemsAll = (ArrayList<Customer>) items.clone();
        this.suggestions = new ArrayList<Customer>();
        this.viewResourceId = viewResourceId;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(viewResourceId, null);
        }
        Customer customer = items.get(position);
        if (customer != null) {
            TextView customerNameLabel = (TextView) v.findViewById(viewResourceId);
            if (customerNameLabel != null) {
//              Log.i(MY_DEBUG_TAG, "getView Customer Name:"+customer.getName());
                customerNameLabel.setText(customer.getName());
            }
        }
        return v;
    }

    @Override
    public Filter getFilter() {
        return nameFilter;
    }

    Filter nameFilter = new Filter() {
        @Override
        public String convertResultToString(Object resultValue) {
            String str = ((Customer)(resultValue)).getName(); 
            return str;
        }
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if(constraint != null) {
                suggestions.clear();
                for (Customer customer : itemsAll) {
                    if(customer.getName().toLowerCase().startsWith(constraint.toString().toLowerCase())){
                        suggestions.add(customer);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            ArrayList<Customer> filteredList = (ArrayList<Customer>) results.values;
            if(results != null && results.count > 0) {
                clear();
                for (Customer c : filteredList) {
                    add(c);
                }
                notifyDataSetChanged();
            }
        }
    };

}
