//package com.mindbees.expenditure.cardview;
//
//import android.graphics.Color;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.ImageView;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import com.fima.cardsui.objects.RecyclableCard;
//import com.mindbees.expenditure.R;
//
//public class MyCardView extends RecyclableCard{
//	String reportTitle;
//	int cardBgColor;
//	int cardImg;
//
//	public MyCardView(String reportTitle, int cardBgColor, int cardImg) {
//		this.reportTitle = reportTitle;
//		this.cardBgColor = cardBgColor;
//		this.cardImg = cardImg;
//
//	}
//
//	@Override
//	protected void applyTo(View convertView) {
//		// TODO Auto-generated method stub
//
//		((TextView) convertView.findViewById(R.id.reportHeading)).setText(reportTitle);
//		((RelativeLayout) convertView.findViewById(R.id.cardBg)).setBackgroundColor(cardBgColor);
//		((ImageView) convertView.findViewById(R.id.reportImg)).setBackgroundResource(cardImg);
//		((ImageView) convertView.findViewById(R.id.reportArow)).setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//
//			}
//		});;
//
//	}
//
//	@Override
//	protected int getCardLayoutId() {
//		// TODO Auto-generated method stub
//		return R.layout.my_card_layout;
//	}
//
//}
