package com.epam.autocode;

public class Main {
    public static void main(String[] args) {
        var jPlagService = new JPlagService();
        var result = jPlagService.run();
        System.out.println(result.getAllComparisons());
    }
}