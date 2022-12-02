package Exeptions;

public class LineNotValidExeption extends Exception{
    public LineNotValidExeption(){
        super();
    }
    public LineNotValidExeption(String message){
        super(message);
    }
}
