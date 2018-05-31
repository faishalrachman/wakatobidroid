package com.aruna.kliknelayanwakatobi.customui;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Filter;

import java.util.ArrayList;
import java.util.List;

public class AdapterSuggestionContains extends ArrayAdapter<String> {

//    private Context context;
    //    private int resource, textViewResourceId;
    private List<String> items, tempItems, suggestions;

    public AdapterSuggestionContains(Context context, int textViewResourceId, List<String> items) {
        super(context, textViewResourceId, items);
//        this.context = context;
//        this.resource = resource;
//        this.textViewResourceId = textViewResourceId;
        this.items = items;
        tempItems = new ArrayList<>(items); // this makes the difference.
        suggestions = new ArrayList<>();
    }

//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        View view = convertView;
//        if (convertView == null) {
//            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            view = inflater.inflate(R.layout.autocomplete_item, parent, false);
//        }
//        String names = items.get(position);
//        if (names != null) {
//            TextView lblName = (TextView) view.findViewById(R.id.lbl_name);
//            if (lblName != null)
//                lblName.setText(names.name);
//        }
//        return view;
//    }

    @Override
    public Filter getFilter() {
        return nameFilter;
    }

    /**
     * Custom Filter implementation for custom suggestions we provide.
     */
    Filter nameFilter = new Filter() {
        //        @Override
//        protected FilterResults performFiltering(CharSequence charSequence) {
//            return null;
//        }
//
//        @Override
//        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
//
//        }
//        @Override
//        public CharSequence convertResultToString(String resultValue) {
//            String str = resultValue;
//            return str;
//        }
//
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();
                for (String names : tempItems) {
                    if (names.toLowerCase().contains(constraint.toString().toLowerCase())) {
                        suggestions.add(names);
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
            List<String> filterList = (ArrayList<String>) results.values;
            if (results != null && results.count > 0) {
                clear();
                for (String names : filterList) {
                    add(names);
                    notifyDataSetChanged();
                }
            }
        }
    };
}