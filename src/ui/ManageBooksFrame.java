package ui;

import java.awt.Font;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class ManageBooksFrame extends JFrame {
    
    private JTextField txtBookId;

    private  JTextField txtTitle;

    private JTextField txtAuthor;

    private JRadioButton rbtnFiction;

    private JRadioButton rbtnNonFiction;

    private ButtonGroup categoryGroup;

    private JTextField txtPrice;

    private JTextArea txtDescription;

    private JButton btnRemove;

    private JButton btnEdit;

    private JButton btnAdd;

    private JTextField txtSearch;

    private JTable bookTable;

    private DefaultTableModel tableModel;

public ManageBooksFrame(){

    setTitle("Library Management System");
    setSize(960 , 620);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    setLayout(null);

    JLabel labelHeader = new JLabel("Manage Books");
    labelHeader.setFont(new Font("Times New Roman" , Font.BOLD , 24));
    labelHeader.setBounds(380 , 15 , 200 , 35);
    add(labelHeader);


JLabel labelBookId = new JLabel("Book Id:");
labelBookId.setBounds(40 , 70 , 90 , 25);
add(labelBookId);

txtBookId = new JTextField();
txtBookId.setBounds(140 , 70 , 200 , 25);
add(txtBookId);


JLabel labelTitle = new JLabel("Title:");
labelTitle.setBounds(40 , 110 , 90 , 25);
add(labelTitle);

txtTitle = new JTextField();
txtTitle.setBounds(140 , 110 , 200 , 25);
add(txtTitle);

JLabel labelAuthor = new JLabel("Author:");
labelAuthor.setBounds(40 , 150 , 90 , 25);
add(labelAuthor);

txtAuthor = new JTextField();
txtAuthor.setBounds(140 , 150 , 200 , 25);
add(txtAuthor);

JLabel labelCategory = new JLabel("Category");
labelCategory.setBounds(40 , 190 , 90 , 25);
add(labelCategory);

rbtnFiction = new JRadioButton("Fiction");
rbtnFiction.setBounds(140 , 190 , 80 , 25);
add(rbtnFiction);

rbtnNonFiction = new JRadioButton("Non-Fiction");
rbtnNonFiction.setBounds(225 , 190 , 110 , 25);
add(rbtnNonFiction);

categoryGroup = new ButtonGroup();
categoryGroup.add(rbtnFiction);
categoryGroup.add(rbtnNonFiction);

JLabel labelPrice = new JLabel("Price:");
labelPrice.setBounds(40 , 230 , 90 ,25);
add(labelPrice);

txtPrice = new JTextField();
txtPrice.setBounds(140 , 230 , 200 , 25);
add(txtPrice);

JLabel labelDesc = new JLabel("Description:");
labelDesc.setBounds(40 , 270 , 90 , 25);
add(labelDesc);

txtDescription = new JTextArea();

JScrollPane scrollDesc = new JScrollPane(txtDescription);
scrollDesc.setBounds(40 , 300 , 300 , 120);
add(scrollDesc);


btnAdd = new JButton("Add");
btnAdd.setBounds(40 , 450 , 100 , 35);
add(btnAdd);

btnRemove = new JButton("Remove");
btnRemove.setBounds(150 , 450 , 100 , 35);
add(btnRemove);

btnEdit = new JButton("Edit");
btnEdit.setBounds(260 , 450 , 100 , 35);
add(btnEdit);

JLabel labelSearch = new JLabel("Enter Value to Search:");
labelSearch.setBounds(400 , 65 , 150 , 25);
add(labelSearch);

txtSearch = new JTextField();
txtSearch.setBounds(560 , 65 , 160 , 25);
add(txtSearch);

String[] column = {
    "Id" , "Title" , "Author" , "Category" , "Price" , "Desc"
};

tableModel = new DefaultTableModel(column , 0);

bookTable = new JTable(tableModel);

JScrollPane scrollTable = new JScrollPane(bookTable);
scrollTable.setBounds(390 , 100 , 520 , 385);
add(scrollTable);

}
public static void main(String[] args) {
    new ManageBooksFrame().setVisible(true);
}

}
