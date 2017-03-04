package com.softengine.monarch.sozluk;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by mehmet on 10.1.2017.
 */

public class CustomAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<Model> model;

    public CustomAdapter(Activity activity, List<Model> model){
        mInflater= (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.model=model;
    }


    @Override
    public int getCount() {
        return model.size();
    }

    @Override
    public Model getItem(int i) {
        return model.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View satirView;

        satirView = mInflater.inflate(R.layout.row, null);
        TextView mSira = (TextView) satirView.findViewById(R.id.row_sira);
        TextView mkategori = (TextView) satirView.findViewById(R.id.row_kategori);
        TextView mTur = (TextView) satirView.findViewById(R.id.row_tur);
        TextView mEng = (TextView) satirView.findViewById(R.id.row_ing);



        final Model finalModel = model.get(position);

        mSira.setText(finalModel.getSira());
        mkategori.setText(finalModel.getKategori());
        mTur.setText(finalModel.getTur());
        mEng.setText(finalModel.getEng());


        return satirView;
    }
}
