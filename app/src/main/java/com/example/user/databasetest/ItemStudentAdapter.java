package com.example.user.databasetest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.user.databasetest.R;
import com.example.user.databasetest.StudentInfo;

import java.util.ArrayList;

public class ItemStudentAdapter extends BaseAdapter {
    private Context activity;
    private ArrayList<StudentInfo> allStudent = new ArrayList<>();
    private LayoutInflater layoutInflater = null;

    public ItemStudentAdapter(Context activity, ArrayList<StudentInfo> allStudent) {
        this.activity = activity;
        this.allStudent = allStudent;
        this.layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }



    private static class ViewHolder{
        private TextView name, id, level, credit;
    }
    private ViewHolder viewHolder = null;



    @Override
    public int getCount() {
        return allStudent.size();
    }

    @Override
    public Object getItem(int position) {
        return allStudent.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=convertView;
        final int pos = position;
        if(view == null){
            viewHolder = new ViewHolder();
            view = layoutInflater.inflate(R.layout.row_cell_student,null);
            viewHolder.name = view.findViewById(R.id.name);
            viewHolder.level = view.findViewById(R.id.level);
            viewHolder.credit = view.findViewById(R.id.credit);
            view.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) view.getTag();
        }
        viewHolder.name.setText(allStudent.get(pos).getName());
        viewHolder.level.setText(allStudent.get(pos).getLevel());
        viewHolder.credit.setText(allStudent.get(pos).getCredit());
        return view;
    }



}
