package jdbctemplate.bean;

/**
 @author Alex
 @create 2023-02-25-13:42
 */

// 对应表t_book中的类
public class Book {
    private String userId;
    private String username;
    private String ustatus;

    public Book() {
    }

    public Book(String userId, String username, String ustatus) {
        this.userId = userId;
        this.username = username;
        this.ustatus = ustatus;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUstatus() {
        return ustatus;
    }

    public void setUstatus(String ustatus) {
        this.ustatus = ustatus;
    }

    @Override
    public String toString() {
        return "Book{" +
                "userId='" + userId + '\'' +
                ", username='" + username + '\'' +
                ", ustatus='" + ustatus + '\'' +
                '}';
    }
}
