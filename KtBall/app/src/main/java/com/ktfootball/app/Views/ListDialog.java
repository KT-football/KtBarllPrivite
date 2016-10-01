package com.ktfootball.app.Views;

import android.app.Dialog;
import android.content.Context;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.kt.ktball.views.wheelview.WheelView;
import com.kt.ktball.views.wheelview.adapter.WheelViewAdapter;
import com.ktfootball.app.Entity.GetCitys;
import com.ktfootball.app.R;

import java.util.List;

/**
 * Created by jy on 16/6/27.
 */
//生日
public class ListDialog
        extends Dialog {

    private WheelView year;
    private List<GetCitys.City> list;
    private TextView confirm;
    private View.OnClickListener listener;
    private TextView cancel;

    public ListDialog(Context context, List<GetCitys.City> list, View.OnClickListener listener) {
        super(context);
        this.list = list;
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        View view = View.inflate(getContext(), R.layout.dialog_listwheel, null);
        year = (WheelView) view.findViewById(R.id.list);
        confirm = (TextView) view.findViewById(R.id.selectDialog_txv_confirm);
        cancel = (TextView) view.findViewById(R.id.selectDialog_txv_cacel);
        year.setViewAdapter(new WheelViewAdapter() {
            @Override
            public int getItemsCount() {
                return list.size();
            }

            @Override
            public View getItem(int index, View convertView, ViewGroup parent) {
                TextView textView = new TextView(getContext());
                textView.setText(list.get(index).name);
                return textView;
            }

            @Override
            public View getEmptyItem(View convertView, ViewGroup parent) {
                TextView textView = new TextView(getContext());
                textView.setText("");
                return textView;
            }

            @Override
            public void registerDataSetObserver(DataSetObserver observer) {

            }

            @Override
            public void unregisterDataSetObserver(DataSetObserver observer) {

            }
        });
        confirm.setOnClickListener(listener);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        year.setCyclic(false);
        year.setVisibleItems(7);

        setContentView(view);

        setCanceledOnTouchOutside(false);

    }

    public WheelView getWheelView() {
        return year;
    }
}
