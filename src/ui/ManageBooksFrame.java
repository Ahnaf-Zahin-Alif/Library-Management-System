package ui;

import dao.BookDao;
import java.awt.Font;
import java.util.List;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import model.Book;

public class ManageBooksFrame extends JFrame {
    private BookDao bookDao = new BookDao();

    private JTextField txtBookId;
    private JTextField txtTitle;
    private JTextField txtAuthor;
    private JRadioButton rbtnFiction;
    private JRadioButton rbtnNonFiction;
    private ButtonGroup categoryGroup;
    private JTextField txtPrice;
    private JTextArea txtDescription;

    private JButton btnAdd;
    private JButton btnRemove;
    private JButton btnEdit;

    private JTextField txtSearch;
    private JTable bookTable;
    private DefaultTableModel tableModel;

    public ManageBooksFrame() {
        setTitle("Library Management System");
        setSize(960, 620);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        // Header Title
        JLabel labelHeader = new JLabel("Manage Books");
        labelHeader.setFont(new Font("Times New Roman", Font.BOLD, 24));
        labelHeader.setBounds(380, 15, 200, 35);
        add(labelHeader);

        // Book ID
        JLabel labelBookId = new JLabel("Book Id:");
        labelBookId.setBounds(40, 70, 90, 25);
        add(labelBookId);

        txtBookId = new JTextField();
        txtBookId.setBounds(140, 70, 200, 25);
        add(txtBookId);

        // Title
        JLabel labelTitle = new JLabel("Title:");
        labelTitle.setBounds(40, 110, 90, 25);
        add(labelTitle);

        txtTitle = new JTextField();
        txtTitle.setBounds(140, 110, 200, 25);
        add(txtTitle);

        // Author
        JLabel labelAuthor = new JLabel("Author:");
        labelAuthor.setBounds(40, 150, 90, 25);
        add(labelAuthor);

        txtAuthor = new JTextField();
        txtAuthor.setBounds(140, 150, 200, 25);
        add(txtAuthor);

        // Category
        JLabel labelCategory = new JLabel("Category:");
        labelCategory.setBounds(40, 190, 90, 25);
        add(labelCategory);

        rbtnFiction = new JRadioButton("Fiction");
        rbtnFiction.setBounds(140, 190, 80, 25);
        rbtnFiction.setSelected(true);
        add(rbtnFiction);

        rbtnNonFiction = new JRadioButton("Non-Fiction");
        rbtnNonFiction.setBounds(225, 190, 110, 25);
        add(rbtnNonFiction);

        categoryGroup = new ButtonGroup();
        categoryGroup.add(rbtnFiction);
        categoryGroup.add(rbtnNonFiction);

        // Price
        JLabel labelPrice = new JLabel("Price:");
        labelPrice.setBounds(40, 230, 90, 25);
        add(labelPrice);

        txtPrice = new JTextField();
        txtPrice.setBounds(140, 230, 200, 25);
        add(txtPrice);

        // Description
        JLabel labelDesc = new JLabel("Description:");
        labelDesc.setBounds(40, 270, 90, 25);
        add(labelDesc);

        txtDescription = new JTextArea();
        txtDescription.setLineWrap(true);
        txtDescription.setWrapStyleWord(true);

        JScrollPane scrollDesc = new JScrollPane(txtDescription);
        scrollDesc.setBounds(40, 300, 300, 120);
        add(scrollDesc);

        // Action Buttons
        btnAdd = new JButton("Add");
        btnAdd.setBounds(40, 450, 100, 35);
        add(btnAdd);

        btnRemove = new JButton("Remove");
        btnRemove.setBounds(150, 450, 100, 35);
        add(btnRemove);

        btnEdit = new JButton("Edit");
        btnEdit.setBounds(260, 450, 100, 35);
        add(btnEdit);

        // Search Bar
        JLabel labelSearch = new JLabel("Enter Value to Search:");
        labelSearch.setBounds(400, 65, 150, 25);
        add(labelSearch);

        txtSearch = new JTextField();
        txtSearch.setBounds(560, 65, 160, 25);
        add(txtSearch);

        // Table
        String[] column = {"Id", "Title", "Author", "Category", "Price", "Desc"};
        tableModel = new DefaultTableModel(column, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table view-only
            }
        };

        bookTable = new JTable(tableModel);
        JScrollPane scrollTable = new JScrollPane(bookTable);
        scrollTable.setBounds(390, 100, 520, 385);
        add(scrollTable);

        // Event Handlers
        initEvents();

        // Initial Data Load from Database
        loadBookToTable();
    }

    private void initEvents() {
        // --- ADD BUTTON ---
        btnAdd.addActionListener(e -> {
            try {
                String idText = txtBookId.getText().trim();
                String title = txtTitle.getText().trim();
                String author = txtAuthor.getText().trim();
                String category = rbtnFiction.isSelected() ? "Fiction" : "Non-Fiction";
                String priceText = txtPrice.getText().trim();
                String desc = txtDescription.getText().trim();

                if (idText.isEmpty() || title.isEmpty() || author.isEmpty() || priceText.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please fill in all required fields (ID, Title, Author, Price)!", "Validation", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                int id = Integer.parseInt(idText);
                double price = Double.parseDouble(priceText);

                Book book = new Book(id, title, author, category, price, desc);

                if (bookDao.addBook(book)) {
                    JOptionPane.showMessageDialog(this, "Book added successfully!");
                    loadBookToTable();
                    clearForm();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to add book. ID might already exist in database.", "Error", JOptionPane.ERROR_MESSAGE);
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid numbers for Book ID and Price!", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // --- EDIT BUTTON ---
        btnEdit.addActionListener(e -> {
            try {
                String idText = txtBookId.getText().trim();
                if (idText.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please select a book from the table to edit.", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                int id = Integer.parseInt(idText);
                String title = txtTitle.getText().trim();
                String author = txtAuthor.getText().trim();
                String category = rbtnFiction.isSelected() ? "Fiction" : "Non-Fiction";
                double price = Double.parseDouble(txtPrice.getText().trim());
                String desc = txtDescription.getText().trim();

                Book book = new Book(id, title, author, category, price, desc);

                if (bookDao.updateBook(book)) {
                    JOptionPane.showMessageDialog(this, "Book updated successfully!");
                    loadBookToTable();
                    clearForm();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to update book.", "Error", JOptionPane.ERROR_MESSAGE);
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid numbers for Book ID and Price!", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // --- REMOVE BUTTON ---
        btnRemove.addActionListener(e -> {
            String idText = txtBookId.getText().trim();
            if (idText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please select a book from the table to remove.", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete this book?",
                "Confirm Deletion",
                JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    int id = Integer.parseInt(idText);
                    Book book = new Book();
                    book.setId(id);

                    if (bookDao.deleteBook(book)) {
                        JOptionPane.showMessageDialog(this, "Book deleted successfully!");
                        loadBookToTable();
                        clearForm();
                    } else {
                        JOptionPane.showMessageDialog(this, "Failed to delete book.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid Book ID!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // --- TABLE ROW CLICK (Populate left form) ---
        bookTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = bookTable.getSelectedRow();
                if (selectedRow != -1) {
                    txtBookId.setText(tableModel.getValueAt(selectedRow, 0).toString());
                    txtTitle.setText(tableModel.getValueAt(selectedRow, 1).toString());
                    txtAuthor.setText(tableModel.getValueAt(selectedRow, 2).toString());

                    String cat = tableModel.getValueAt(selectedRow, 3).toString();
                    if ("Fiction".equalsIgnoreCase(cat)) {
                        rbtnFiction.setSelected(true);
                    } else {
                        rbtnNonFiction.setSelected(true);
                    }

                    txtPrice.setText(tableModel.getValueAt(selectedRow, 4).toString());
                    Object descVal = tableModel.getValueAt(selectedRow, 5);
                    txtDescription.setText(descVal != null ? descVal.toString() : "");
                }
            }
        });

        // --- SEARCH BAR (Live Search Filter) ---
        txtSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { doSearch(); }
            @Override
            public void removeUpdate(DocumentEvent e) { doSearch(); }
            @Override
            public void changedUpdate(DocumentEvent e) { doSearch(); }

            private void doSearch() {
                String keyword = txtSearch.getText().trim();
                if (keyword.isEmpty()) {
                    loadBookToTable();
                } else {
                    List<Book> searchResults = bookDao.searchBooks(keyword);
                    tableModel.setRowCount(0);
                    for (Book b : searchResults) {
                        tableModel.addRow(new Object[]{
                            b.getId(),
                            b.getTitle(),
                            b.getAuthor(),
                            b.getCategory(),
                            b.getPrice(),
                            b.getDescription()
                        });
                    }
                }
            }
        });
    }

    private void loadBookToTable() {
        tableModel.setRowCount(0);
        List<Book> books = bookDao.getAllBooks();
        for (Book b : books) {
            tableModel.addRow(new Object[]{
                b.getId(),
                b.getTitle(),
                b.getAuthor(),
                b.getCategory(),
                b.getPrice(),
                b.getDescription()
            });
        }
    }

    private void clearForm() {
        txtBookId.setText("");
        txtTitle.setText("");
        txtAuthor.setText("");
        rbtnFiction.setSelected(true);
        txtPrice.setText("");
        txtDescription.setText("");
        bookTable.clearSelection();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ManageBooksFrame().setVisible(true);
        });
    }
}
