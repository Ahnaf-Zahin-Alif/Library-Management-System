package dao;

import database.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Book;

public class BookDao {
    

public boolean addBook(Book book){
    String sql = "INSERT INTO books(id , title , author , category , price , description) values(? , ? , ? , ? , ? , ?)";

    try(Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setInt(1, book.getId());
            stmt.setString(2, book.getTitle());
            stmt.setString(3, book.getAuthor());
            stmt.setString(4, book.getCategory());
            stmt.setDouble(5, book.getPrice());
            stmt.setString(6, book.getDescription());

        return stmt.executeUpdate() > 0;

        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }


public boolean updateBook(Book book){
    String sql = "Update books set title = ? ,  author = ? , category = ?,price = ? , description = ? where id = ?";

    try(Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)){

            
            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getAuthor());
            stmt.setString(3, book.getCategory());
            stmt.setDouble(4, book.getPrice());
            stmt.setString(5, book.getDescription());
            stmt.setInt(6, book.getId());

        return stmt.executeUpdate() > 0;

        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }

public boolean deleteBook(Book book){
    String sql = "Delete from books where id = ?";

    try(Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)){

            
            
            stmt.setInt(1, book.getId());

        return stmt.executeUpdate() > 0;

        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }

public List<Book> getAllBooks(){
    List<Book> list = new ArrayList<>();
    String sql = "Select * from books";

try(Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();) {

            while(rs.next()){
                Book book = new Book();
                book.setId(rs.getInt("id"));
                book.setTitle(rs.getString("title"));
                book.setAuthor(rs.getString("author"));
                book.setCategory(rs.getString("category"));
                book.setPrice(rs.getDouble("price"));
                book.setDescription(rs.getString("description"));
                list.add(book);
            }


    
} catch (SQLException e) {
    e.printStackTrace();
}


return list;

}

public List<Book> searchBooks(String keyword){
    List<Book> list = new ArrayList<>();
    String sql = "Select * from books where id like ? or title like ? or author like ?";

try(Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
){
       String searchPattern = "%" + keyword + "%";
       stmt.setString(1, searchPattern);     
        stmt.setString(2, searchPattern);
        stmt.setString(3, searchPattern);

       try(ResultSet rs = stmt.executeQuery();){
        while(rs.next()){
                Book book = new Book();
                book.setId(rs.getInt("id"));
                book.setTitle(rs.getString("title"));
                book.setAuthor(rs.getString("author"));
                book.setCategory(rs.getString("category"));
                book.setPrice(rs.getDouble("price"));
                book.setDescription(rs.getString("description"));
                list.add(book);
            }
       } 

    
} catch (SQLException e) {
    e.printStackTrace();
}


return list;

}
















}
