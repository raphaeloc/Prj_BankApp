package com.example.prj_bankapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.prj_bankapp.R;
import com.example.prj_bankapp.model.listmodel.StatementList;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StatementAdapter extends RecyclerView.Adapter<StatementAdapter.StatementHolder> {

    private List<StatementList> dados = new ArrayList<>();
    private int resource;
    private Context context;

    public StatementAdapter(int resource, Context context) {
        this.resource = resource;
        this.context = context;
    }

    public void setDados(List<StatementList> dados) {
        this.dados = dados;
        notifyDataSetChanged();
    }

    public class StatementHolder extends RecyclerView.ViewHolder {

        TextView tv_title, tv_value, tv_desc, tv_date;

        public StatementHolder(@NonNull View itemView) {
            super(itemView);

            tv_title = itemView.findViewById(R.id.celula_tv_title);
            tv_date = itemView.findViewById(R.id.celula_tv_date);
            tv_desc = itemView.findViewById(R.id.celula_tv_desc);
            tv_value = itemView.findViewById(R.id.celula_tv_value);
        }
    }

    @NonNull
    @Override
    public StatementHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(resource, parent, false);

        return new StatementHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StatementHolder holder, int position) {

        StatementList item = dados.get(position);

        SimpleDateFormat formatUS = new SimpleDateFormat("yyyy-mm-dd");
        SimpleDateFormat formatBR = new SimpleDateFormat("dd-mm-yyyy");
        String dateFormated = "";
        try {
            Date date = formatUS.parse(item.getDate());
            dateFormated = formatBR.format(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }


        holder.tv_title.setText(item.getTitle());
        holder.tv_value.setText(String.valueOf(item.getValue()));
        holder.tv_date.setText(dateFormated);
        holder.tv_desc.setText(item.getDesc());
    }

    @Override
    public int getItemCount() {
        return dados.size();
    }
}
