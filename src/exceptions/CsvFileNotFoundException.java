package exceptions;

public class CsvFileNotFoundException extends  Exception{

    public CsvFileNotFoundException(String csvPath){
        super(csvPath+" is a invalid path!");
    }
}
