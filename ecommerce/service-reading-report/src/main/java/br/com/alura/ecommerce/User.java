package br.com.alura.ecommerce;

public class User {

    private final String id;

    public User(String id) {
        this.id = id;
    }

    public String getReportPath() {
        return "target/" + id + "-report.txt";
    }

    public String getId() {
        return id;
    }
}
