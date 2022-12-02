package Exeptions;

public class AlreadyExistExeption extends Exception{
    public AlreadyExistExeption(){
        super();
    }
    public AlreadyExistExeption(String message){
        super(message);
    }
}
