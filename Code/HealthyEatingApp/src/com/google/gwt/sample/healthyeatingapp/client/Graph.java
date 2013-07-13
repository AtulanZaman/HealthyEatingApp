package com.google.gwt.sample.healthyeatingapp.client;
 

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.visualization.client.AbstractDataTable;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.visualizations.corechart.LineChart;
import com.google.gwt.visualization.client.visualizations.corechart.Options;


public class Graph extends Composite{
	private LineChart chart;
	
	public Graph(){
	}
	
	public LineChart returnGraph (){
		this.chart = new LineChart(createTable(), createOptions());
		return chart;
		//get stuff from database
	}
	
	private AbstractDataTable createTable(){
		DataTable data = DataTable.create();
		data.addColumn(ColumnType.STRING, "Type");
		data.addColumn(ColumnType.NUMBER, "num1");
		data.addColumn(ColumnType.NUMBER, "num2");
		data.addRows(2);
		data.setValue(0, 0, "foo");
		data.setValue(0, 1, 14);
		data.setValue(0, 2, 18);
		data.setValue(1,0, "foo2");
		data.setValue(1,1, 50);
		data.setValue(1,2, 90);
		
		return data;
	}
	
	private Options createOptions() {
	    Options options = Options.create();
	    options.setWidth(400);
	    options.setHeight(240);
	    options.setTitle("My Daily Activities");
	    return options;
	}
}
