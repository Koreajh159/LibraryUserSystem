package lus.news;

import javax.swing.table.AbstractTableModel;

public class NewsTableModel3 extends AbstractTableModel{
	String[] columnName;
	Object data[][];
	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return data.length;
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return columnName.length;
	}

	@Override
	public Object getValueAt(int row, int col) {
		// TODO Auto-generated method stub
		return data[row][col];
	}
	
	@Override
	public String getColumnName(int col) {
		// TODO Auto-generated method stub
		return columnName[col];
	}
}
