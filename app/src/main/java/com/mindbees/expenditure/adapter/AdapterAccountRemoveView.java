package com.mindbees.expenditure.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mindbees.expenditure.R;
import com.mindbees.expenditure.Interfaces.DeleteItem;
import com.mindbees.expenditure.adapter.AdapterAcntStats.ViewHolder;
import com.mindbees.expenditure.database.MyDataBase;
import com.mindbees.expenditure.fragment.FragmentAccounts;
import com.mindbees.expenditure.model.StatusAccount;
import com.mindbees.expenditure.ui.CircularContactView;
import com.mindbees.expenditure.ui.swiperemove.BaseSwipeAdapter;
import com.mindbees.expenditure.ui.swiperemove.SimpleSwipeListener;
import com.mindbees.expenditure.ui.swiperemove.SwipeLayout;
import com.mindbees.expenditure.util.BaseActivity;
import com.mindbees.expenditure.util.Const;
import com.mindbees.expenditure.util.Tools;

public class AdapterAccountRemoveView extends BaseSwipeAdapter{
	
	Context context;
	public ArrayList<StatusAccount> allList;
	private int[] PHOTO_TEXT_BACKGROUND_COLORS;
	boolean fromAccount;
//	Tools tools;
	String symbol;
	DeleteItem mCallBack;
	

	public AdapterAccountRemoveView(Context context, ArrayList<StatusAccount> allList,
			boolean fromAccount) {
		super();
		this.context = context;
		this.allList = allList;
		this.fromAccount = fromAccount;
		PHOTO_TEXT_BACKGROUND_COLORS = context.getResources().getIntArray(R.array.contacts_text_background_colors);
//		tools = new Tools(context);
//		symbol = tools.getCurrency();
		symbol = BaseActivity.getpreference(Const.TAG_SYMBOL, context);
	}
	
	public void setCallBack(FragmentAccounts context){
		mCallBack = (DeleteItem) context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return allList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public int getSwipeLayoutResourceId(int position) {
		// TODO Auto-generated method stub
		return R.id.swipe;
	}

	@Override
	public View generateView(final int position, ViewGroup parent) {
		// TODO Auto-generated method stub
		final View v = LayoutInflater.from(context).inflate(R.layout.inflate_remove_view, null);
        SwipeLayout swipeLayout = (SwipeLayout)v.findViewById(getSwipeLayoutResourceId(position));
        swipeLayout.addDrag(SwipeLayout.DragEdge.Right, v.findViewById(R.id.childView));
        
        swipeLayout.addSwipeListener(new SimpleSwipeListener() {
            @Override
            public void onOpen(SwipeLayout layout) {
//                YoYo.with(Techniques.Tada).duration(500).delay(100).playOn(layout.findViewById(R.id.trash));
				v.findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
//                Toast.makeText(context, "click delete", Toast.LENGTH_SHORT).show();

						mCallBack.onDeleteItem(position);

					}
				});
            }
        });
        /*swipeLayout.setOnDoubleClickListener(new SwipeLayout.DoubleClickListener() {
            @Override
            public void onDoubleClick(SwipeLayout layout, boolean surface) {
                Toast.makeText(context, "DoubleClick", Toast.LENGTH_SHORT).show();
            }
        });*/
        
        

        return v;
	}

	@Override
	public void fillValues(int position, View v) {
		// TODO Auto-generated method stub
		ViewHolder holder;
			holder = new ViewHolder();
			
			holder.tvAcntName = (TextView) v.findViewById(R.id.acnt_name);
//			holder.tvamount = (TextView) v.findViewById(R.id.amount);
//			holder.tvSymbol = (TextView) v.findViewById(R.id.symbol);
			holder.acntIcon = (CircularContactView) v.findViewById(R.id.statusIcon);
			
			v.setTag(holder);
		
		 final int backgroundColorToUse=PHOTO_TEXT_BACKGROUND_COLORS[position % PHOTO_TEXT_BACKGROUND_COLORS.length];
		
		holder.tvAcntName.setText(allList.get(position).getAccount_tittle());
		/*if (fromAccount) {
			holder.tvamount.setText(allList.get(position).getInitial_amount());
		}else {
			holder.tvamount.setText(allList.get(position).getFinal_balance());
		}*/
		holder.acntIcon.getTextView().setTextColor(context.getResources().getColor(R.color.white));
//		holder.tvSymbol.setText(symbol);
		holder.acntIcon.setTextAndBackgroundColor(allList.get(position).getNumber()+"", backgroundColorToUse, false);
		
		
	}
	
	class ViewHolder {
		TextView tvAcntName, tvamount, tvSymbol;
		CircularContactView acntIcon;
	}

}
