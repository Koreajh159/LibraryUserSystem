package lus.news;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import lus.MainFrame;
import lus.db.UserInformation;

public class LibraryNewsPanel extends JPanel {
	JPanel p_lib, p_user, p_newBooks, p_readKing, p_userInfo, p_rentalRecord, p_center;
	JLabel l_newBooks, l_rentalRecord, l_readKing;
	JTable table_newBook, table_rentRec, table_readKing;
	JScrollPane scroll_book, scroll_king, scroll_rent;
	MainFrame main;
	NewsTableModel model;
	NewsTableModel2 model2;
	NewsTableModel3 model3;

	public LibraryNewsPanel(MainFrame main) {
		this.main = main;
		setLayout(new BorderLayout());
		p_lib = new JPanel(new BorderLayout());
		
		p_newBooks = new JPanel(new BorderLayout());
		l_newBooks = new JLabel("이달의 신간 도서");
		table_newBook = new JTable();
		scroll_book = new JScrollPane(table_newBook);
		scroll_book.setPreferredSize(new Dimension(900, 250));
		p_newBooks.add(l_newBooks, BorderLayout.NORTH);
		p_newBooks.add(scroll_book);

		p_readKing = new JPanel(new BorderLayout());
		l_readKing = new JLabel("이달의 독서왕");
		table_readKing = new JTable();
		scroll_king = new JScrollPane(table_readKing);
		scroll_king.setPreferredSize(new Dimension(900, 250));
		p_readKing.add(l_readKing, BorderLayout.NORTH);
		p_readKing.add(scroll_king);

		p_lib.add(p_newBooks, BorderLayout.NORTH);
		p_lib.add(p_readKing);

		p_user = new JPanel(new BorderLayout());
		p_userInfo = new JPanel();

		p_rentalRecord = new JPanel(new BorderLayout());
		l_rentalRecord = new JLabel("나의 도서 대출 기록");
		table_rentRec = new JTable();
		scroll_rent = new JScrollPane(table_rentRec);
		scroll_rent.setPreferredSize(new Dimension(250, 300));
		p_rentalRecord.add(scroll_rent);
		p_rentalRecord.add(l_rentalRecord, BorderLayout.NORTH);

		p_user.add(p_userInfo, BorderLayout.NORTH);
		p_user.add(p_rentalRecord, BorderLayout.SOUTH);

		p_center = new JPanel();
		p_center.setPreferredSize(new Dimension(30, 400));
		add(p_center);
		add(p_lib, BorderLayout.WEST);
		add(p_user, BorderLayout.EAST);

		model = new NewsTableModel();
		model2 = new NewsTableModel2();
		model3 = new NewsTableModel3();
		showNewBook();
		showBestTop10();
		showMyRental();
	}

	public void showNewBook() {

		Connection con = main.getConn();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		// String str= "201902";
		StringBuffer sb = new StringBuffer();
		String[] columnName = { "도서명", "장르", "출판사", "저자" };
		sb.append("select b.book_id,g.genre,b.book_name,p.publisher");
		sb.append(",b.writer,bd.book_regist_date");
		sb.append(",bs.book_state,r.member_id,rs.rental_state_id");
		sb.append(" from lib_book b,lib_genre g,lib_member m,lib_publisher p");
		sb.append(",lib_book_state bs,lib_rental_table r,lib_book_detail bd,lib_rental_state rs");
		sb.append(" where b.book_id = bd.book_id");
		sb.append(" and rs.rental_state_id = bd.rental_state");
		sb.append(" and bd.book_state = bs.book_state_id");
		sb.append(" and r.member_id = m.mem_id");
		sb.append(" and g.genre_id =b.genre");
		sb.append(" and p.publisher_id = b.publisher");
		sb.append(" and bd.book_regist_date like '%" + CurrentDay.getCurrentMonth() + "%'");
		// sb.append(" and count(count)");
		sb.append(" order by book_id asc");
		try {
			pstmt = con.prepareStatement(sb.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = pstmt.executeQuery();
			rs.last();
			int total = rs.getRow();
			Object[][] data = new Object[total][columnName.length];
			rs.beforeFirst();

			for (int i = 0; i < total; i++) {
				rs.next();
				data[i][0] = rs.getString("book_name");
				data[i][1] = rs.getString("genre");
				data[i][2] = rs.getString("publisher");
				data[i][3] = rs.getString("writer");
				
			}
			model.columnName = columnName;
			model.data = data;
			table_newBook.setModel(model);
			table_newBook.updateUI();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// sb.append(b);
		// sb.append(b);
		// System.out.println(sb.toString());
	}

	public void showBestTop10() {
		Connection con = main.getConn();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		// String str= "201902";
		StringBuffer sb = new StringBuffer();
		String[] columnName = {"등수","이름", "대여 책의 수" };
		sb.append("select member_id,count(member_id) from lib_rental_table");
		sb.append(" where rental_date like '%" + CurrentDay.getCurrentMonth() + "%'");
		sb.append(" group by member_id");

		try {
			pstmt = con.prepareStatement(sb.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = pstmt.executeQuery();
			rs.last();
			int total = rs.getRow();
			Object[][] data = new Object[total][columnName.length];
			rs.beforeFirst();

			for (int i = 0; i < total; i++) {
				rs.next();

				data[i][0] = i + 1;
				data[i][1] = rs.getString("member_id");
				data[i][2] = rs.getString("count(member_id)");

		
			}
			model2.columnName = columnName;
			model2.data = data;
			table_readKing.setModel(model2);
			table_readKing.updateUI();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// sb.append(b);
		// sb.append(b);
		// System.out.println(sb.toString());
	}
	public void showMyRental() {
		Connection con = main.getConn();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		// String str= "201902";
		StringBuffer sb = new StringBuffer();
		String[] columnName = { "도서명", "대출일"};
		sb.append("select b.book_name,r.rental_date");
		sb.append(" from lib_book b, lib_rental_table r");
		sb.append(" where r.member_id="+UserInformation.getCurrentUserID());

		try {
			pstmt = con.prepareStatement(sb.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = pstmt.executeQuery();
			rs.last();
			int total = rs.getRow();
			Object[][] data = new Object[total][columnName.length];
			rs.beforeFirst();

			for (int i = 0; i < total; i++) {
				rs.next();

	
				data[i][0] = rs.getString("book_name");
				data[i][1] = rs.getString("rental_date");

				
			}
			model3.columnName = columnName;
			model3.data = data;
			table_rentRec.setModel(model2);
			table_rentRec.updateUI();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
