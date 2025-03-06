package exception;

@SuppressWarnings("serial")
public class LeaveApplicationNotFoundException extends RuntimeException{
	public LeaveApplicationNotFoundException(String message) {
        super(message);
    }

}
