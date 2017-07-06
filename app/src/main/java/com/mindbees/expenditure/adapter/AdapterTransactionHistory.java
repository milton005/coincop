package com.mindbees.expenditure.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mindbees.expenditure.R;
import com.mindbees.expenditure.model.MonthlyAccountStat.Account;
import com.mindbees.expenditure.util.BaseActivity;
import com.mindbees.expenditure.util.Const;

import java.security.PublicKey;
import java.util.ArrayList;

/**
 * Created by User on 18-02-2017.
 */

public class AdapterTransactionHistory extends RecyclerView.Adapter<AdapterTransactionHistory.ViewHolder> {
    private ArrayList<Account> arrayList;
    private Context context;
    String symbol;
    public AdapterTransactionHistory(Context context,ArrayList<Account>arrayList)
    {
        this.arrayList=arrayList;
        this.context=context;
        symbol = BaseActivity.getpreference(Const.TAG_SYMBOL, context);
    }
    public Account getObject(int position){return arrayList.get(position);}
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_transactionhistory,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String title=arrayList.get(position).getCategoryTitle();
        String firstamont=arrayList.get(0).getAmount();
        String description=arrayList.get(position).getDescription();
        String amount=arrayList.get(position).getAmount();
        String balance=arrayList.get(position).getEndingBalance();
        String date=arrayList.get(position).getActionTime();
        holder.date.setText(date);
        holder.balance.setText(balance);
        holder.amount.setText(amount);
        holder.description.setText(description);
        holder.title.setText(title);
        if (amount.contentEquals(firstamont))
        {

        }
        else {
            holder.amount.setTextColor(context.getResources().getColor(R.color.red));
            holder.amountsymbol.setTextColor(context.getResources().getColor(R.color.red));
        }
        if(!amount.isEmpty())
        {
            holder.amountsymbol.setText(symbol);
        }
        if (!balance.isEmpty())
        {
            holder.balancesymbol.setText(symbol);
        }

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView description,title,amount,amountsymbol,balance,balancesymbol,date;

        public ViewHolder(View itemView) {
            super(itemView);
            description= (TextView) itemView.findViewById(R.id.description);
            title= (TextView) itemView.findViewById(R.id.Title_salary);
            amount= (TextView) itemView.findViewById(R.id.amount);
            amountsymbol= (TextView) itemView.findViewById(R.id.amount_symbol);
            balance= (TextView) itemView.findViewById(R.id.balance_transactions);
            balancesymbol= (TextView) itemView.findViewById(R.id.balance_symbol);
            date= (TextView) itemView.findViewById(R.id.date_transaction);
        }
    }
}
