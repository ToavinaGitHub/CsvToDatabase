package run;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

import Database.*;
import exceptions.ColsNumException;
import helper.CsvAble;

public class Main {
    public static void main(String[] args) throws SQLException, ColsNumException, ClassNotFoundException {

        Connection con = Connect.getConnection();
        String pathCsv = "D:/Mr-Tahiana/CsvToDatabase/testData.csv";

        CsvAble c = new CsvAble();

        String[] cols = new String[2];
        cols[0] = "nomEtudiant";
        cols[1] = "prenomEtudiant";


        int[] nums = new int[2];
        nums[0] = 0;
        nums[1] = 1;


        c.toDatabase("etudiant",cols,nums,pathCsv,con);
    }
}
