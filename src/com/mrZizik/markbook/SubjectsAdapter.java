package com.mrZizik.markbook;

import android.R.color;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

public class SubjectsAdapter extends ArrayAdapter<Subject> { 

        private final Activity activity;
        //
        private final ArrayList<Subject> entries;

        public SubjectsAdapter(final Activity a, final int textViewResourceId, final ArrayList<Subject> entries) {

                super(a, textViewResourceId, entries);
                this.entries = entries;
                activity = a;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

                View v = convertView;
                ViewHolder holder;
                if (v == null) {
                        LayoutInflater inflater = (LayoutInflater) activity
                                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        v = inflater.inflate(R.layout.list_view, parent, false);
                        holder = new ViewHolder();
                        // инициализируем нашу разметку
                        holder.name = (TextView) v.findViewById(R.id.subjectName);
                        holder.modul1 = (TextView) v.findViewById(R.id.modul1);
                        holder.modul2 = (TextView) v.findViewById(R.id.modul2);
                        holder.modul3 = (TextView) v.findViewById(R.id.modul3);
                        holder.modul4 = (TextView) v.findViewById(R.id.modul4);
                        holder.CMark = (TextView) v.findViewById(R.id.kursach);
                        holder.ZMark = (TextView) v.findViewById(R.id.zachet);
                        holder.EMark = (TextView) v.findViewById(R.id.ekzamen);

                        v.setTag(holder);
                } else {
                        holder = (ViewHolder) v.getTag();
                }
                Subject subject = entries.get(position);
                if (subject != null) {
                        // получаем текст из массива
                        holder.name.setText(subject.name);
                        holder.modul1.setText(subject.modul1);
                        holder.modul2.setText(subject.modul2);
                        holder.modul3.setText(subject.modul3);
                        holder.modul4.setText(subject.modul4);
                        holder.CMark.setText(subject.kursach);
                        holder.ZMark.setText(subject.zachet);
                        holder.EMark.setText(subject.exam);

                        // скачиваем картинки
                        
                }
                /*if (holder.name.getText()=="Название предмета:") {
                	holder.name.setBackgroundColor(0xFF6B696B);
                	holder.name.setTextColor(Color.WHITE);
                	holder.modul1.setBackgroundColor(0xFF6B696B);
                	holder.modul1.setTextColor(Color.WHITE);
                	holder.modul2.setBackgroundColor(0xFF6B696B);
                	holder.modul2.setTextColor(Color.WHITE);
                	holder.modul3.setBackgroundColor(0xFF6B696B);
                	holder.modul3.setTextColor(Color.WHITE);
                	holder.modul4.setBackgroundColor(0xFF6B696B);
                	holder.modul4.setTextColor(Color.WHITE);
                	holder.CMark.setBackgroundColor(0xFF6B696B);
                	holder.CMark.setTextColor(Color.WHITE);
                	holder.ZMark.setBackgroundColor(0xFF6B696B);
                	holder.ZMark.setTextColor(Color.WHITE);
                	holder.EMark.setBackgroundColor(0xFF6B696B);
                	holder.EMark.setTextColor(Color.WHITE);
                }*/
                return v;
        }

        // для быстроты вынесли в отдельный класс
        private static class ViewHolder {
                public TextView name;
                public TextView modul1;
                public TextView modul2;
                public TextView modul3;
                public TextView modul4;
                public TextView CMark;
                public TextView ZMark;
                public TextView EMark;
        }
}