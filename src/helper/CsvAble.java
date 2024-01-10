package helper;

import exceptions.ColsNumException;
import exceptions.CsvFileNotFoundException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CsvAble {
    private static String spliter = ";";


    public String getQuery(String tableName,String[] cols){
        String res ="INSERT INTO "+tableName +"(";
        for (int i = 0; i < cols.length; i++) {
            res +=cols[i];
            if(i!=cols.length-1){
                res+=",";
            }
        }
        res+=") VALUES ( ";
        for (int i = 0; i < cols.length; i++) {
            res +="?";
            if(i!=cols.length-1){
                res+=",";
            }
        }
        res+=")";
        return res;
    }
    public String toDatabase(String tableName, String[] cols, int[] colsNum, String csvPath, Connection con) throws ColsNumException, SQLException {
        if (cols.length != colsNum.length) {
            throw new ColsNumException();
        }

        String sql = getQuery(tableName, cols);
        try (PreparedStatement ps = con.prepareStatement(sql);
             BufferedReader bReader = new BufferedReader(new FileReader(csvPath))) {

            String line;
            int batchSize = 500;
            int count = 0;

            con.setAutoCommit(false);

            while ((line = bReader.readLine()) != null) {
                if (line != null) {
                    String[] array = line.split(";");
                    for (int i = 0; i < cols.length; i++) {
                        ps.setString(i + 1, array[colsNum[i]]);
                    }
                    ps.addBatch();

                    if (++count % batchSize == 0) {
                        ps.executeBatch();
                        ps.clearBatch();
                    }
                }
            }
            ps.executeBatch();
            // Commit the transaction
            con.commit();

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            // Handle SQLException appropriately, e.g., log it
            e.printStackTrace();
        } finally {
            // Ensure the connection is closed in case of an exception
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return "Insert successful";
    }

}
