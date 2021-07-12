package com.busrasonmez.kitapgunlugu;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ListviewAdaptor extends BaseAdapter implements Filterable {

    private LayoutInflater layoutInflater;
    private ArrayList<KutuphaneListview> kutuphaneArrayList;
    private ArrayList<KutuphaneListview> kutuphaneArrayListFiltre;
    private Context context;

    public ListviewAdaptor(Activity activity, ArrayList<KutuphaneListview> kutuphaneArrayList){
        this.layoutInflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.kutuphaneArrayList=kutuphaneArrayList;
        this.kutuphaneArrayListFiltre=kutuphaneArrayList;
        this.context=activity;

    }

    @Override
    public int getCount() {
        return kutuphaneArrayListFiltre.size();
    }

    @Override
    public Object getItem(int position) {
        return kutuphaneArrayListFiltre.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listView;
        convertView = layoutInflater.inflate(R.layout.listview,null);
        ImageView kitap_resmi = (ImageView)convertView.findViewById(R.id.kitap_resmi);
        TextView kitap_adi = (TextView)convertView.findViewById(R.id.kitapadi);
        TextView kitap_yazari = (TextView)convertView.findViewById(R.id.kitapyazari);
        TextView kitap_turu = (TextView)convertView.findViewById(R.id.kitapturu);
        TextView kitap_durumu = (TextView)convertView.findViewById(R.id.kitapdurumu);

        KutuphaneListview kutuphane = kutuphaneArrayListFiltre.get(position);

        kitap_adi.setText(kutuphane.getKitapAdi());
        kitap_yazari.setText(kutuphane.getYazari());
        kitap_turu.setText(kutuphane.getTuru());
        kitap_durumu.setText(kutuphane.getKdurumu());
        Bitmap bitmap = BitmapFactory.decodeByteArray(kutuphane.getResim(),0,kutuphane.getResim().length);
        kitap_resmi.setImageBitmap(bitmap);
        return convertView;

    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter(){

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                FilterResults filterResults = new FilterResults();
                if(constraint == null || constraint.length()==0){
                    filterResults.count = kutuphaneArrayList.size();
                    filterResults.values = kutuphaneArrayList;
                }
                else {
                    String searchstr = constraint.toString();
                    ArrayList<KutuphaneListview> resultData = new ArrayList<>();
                    for (KutuphaneListview kutuphaneListview:kutuphaneArrayList){
                        if(kutuphaneListview.getKitapAdi().toLowerCase().contains(searchstr) ||
                                kutuphaneListview.getYazari().toLowerCase().contains(searchstr) ||
                                kutuphaneListview.getKdurumu().toLowerCase().contains(searchstr) ||
                                kutuphaneListview.getTuru().toLowerCase().contains(searchstr)){
                            resultData.add(kutuphaneListview);
                        }

                        filterResults.count = resultData.size();
                        filterResults.values = resultData;
                    }
                }


                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                kutuphaneArrayListFiltre = (ArrayList<KutuphaneListview>)results.values;
                notifyDataSetChanged();
            }
        };
        return filter;
    }
}
