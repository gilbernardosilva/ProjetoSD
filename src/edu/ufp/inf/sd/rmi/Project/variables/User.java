package edu.ufp.inf.sd.rmi.Project.variables;

public class User {

    private String username;
    private String password;
    private Token token;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String username, String password, Token token) {
        this.username = username;
        this.password = password;
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + this.username + '\'' +
                ", password='" + this.password + '\'' +
                ", token=" + this.token +
                '}';
    }
}
