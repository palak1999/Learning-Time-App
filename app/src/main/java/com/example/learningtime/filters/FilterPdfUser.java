package com.example.learningtime.filters;

import android.graphics.ColorSpace;
import android.widget.Filter;

import com.example.learningtime.adapters.AdapterPdfAdmin;
import com.example.learningtime.adapters.AdapterPdfUser;
import com.example.learningtime.models.ModelPdf;

import java.util.ArrayList;
import java.util.Locale;

public class FilterPdfUser extends Filter {

    ArrayList<ModelPdf> filterList;
    AdapterPdfUser adapterPdfUser;

    public FilterPdfUser(ArrayList<ModelPdf> filterList, AdapterPdfUser adapterPdfUser) {
        this.filterList = filterList;
        this.adapterPdfUser = adapterPdfUser;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results=new FilterResults();

        if(constraint!=null||constraint.length()>0){
            constraint=constraint.toString().toUpperCase();
            ArrayList<ModelPdf> filteredModels=new ArrayList<>();

            for(int i=0;i<filterList.size();i++){
                if(filterList.get(i).getTitle().toUpperCase().contains(constraint)){
                    filteredModels.add(filterList.get(i));
                }
            }
            results.count=filteredModels.size();
            results.values=filteredModels;
        }
        else{
            results.count=filterList.size();
            results.values=filterList;
        }

        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        adapterPdfUser.pdfArrayList=(ArrayList<ModelPdf>)results.values;

        adapterPdfUser.notifyDataSetChanged();
    }
}
