package com.google.gwt.sample.healthyeatingapp.client.SocialMedia;

import java.util.Iterator;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootPanel;

public class SocialMedia extends Composite implements HasWidgets{
	
	FlowPanel fp;
	Button Btn;
	FlexTable ft;
	public SocialMedia(){
		//initWidget(this.fp);
		
		//fp.add(new LeaderboardWidget(this));
		//SocialMediaWebPageLoad(HTML);
	}
	
	public FlowPanel SocialMediaWebPageLoad(){
		fp = new FlowPanel();
		ft = new FlexTable();
		ft.setTitle("Leaderboard");
		ft.setText(0, 0, "Leaderboard");
		ft.getFlexCellFormatter().setColSpan(0, 0, 2);		
		ft.getFlexCellFormatter().setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_CENTER);
		ft.setText(1, 0, "Friend");
		ft.setText(1, 1, "Points");
		Btn = new Button();		
		Btn.setSize("100px", "30px");
		Btn.setText("Update");
		fp.add(ft);
		fp.add(Btn);
		return fp;
	}

	@Override
	public void add(Widget w) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Iterator<Widget> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean remove(Widget w) {
		// TODO Auto-generated method stub
		return false;
	}

}
