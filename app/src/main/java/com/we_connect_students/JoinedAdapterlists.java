package com.we_connect_students;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

class JoinedAdapterlists extends BaseAdapter {

   private LayoutInflater mInflater;
   ViewHolder holder;
   private List<Citys> Modellists = null;
   private ArrayList<Citys> Join_arraylist;

   public JoinedAdapterlists(Context context, List<Citys> Joinmodellist) {

       mInflater = LayoutInflater.from(context);
       this.Modellists = Joinmodellist;
       this.Join_arraylist = new ArrayList<Citys>();
       this.Join_arraylist.addAll(Joinmodellist);

   }

   @Override
   public int getCount() {

       return Modellists.size();
   }

   @Override
   public Citys getItem(int position) {

       return Modellists.get(position);

   }

   @Override
   public long getItemId(int position) {
       return position;
   }

   @Override
   public View getView(final int position, View convertView, ViewGroup parent) {

       if (convertView == null) {

           holder = new ViewHolder();

           convertView = mInflater.inflate(R.layout.question, null);

           holder.Title=convertView.findViewById(R.id.Title);

           convertView.setTag(holder);

       } else {
           holder = (ViewHolder) convertView.getTag();
       }
       Citys item = Modellists.get(position);
       holder.Title.setText(Modellists.get(position).GETCITY());













       return convertView;
   }

    public void clearData() {

        Modellists.clear();
    }

    private class ViewHolder {

       TextView Title;

   }
}
