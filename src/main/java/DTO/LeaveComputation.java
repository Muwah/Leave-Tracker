package DTO;

public class LeaveComputation {
	private int leaveDue;
    private int leaveTaken;
    private int balance;

    // Constructors
    public LeaveComputation(int due, int taken) {
        this.leaveDue = due;
        this.leaveTaken = taken;
        this.balance = due - taken;
    }

    // Getters
    public int getLeaveDue() { return leaveDue; }
    public int getLeaveTaken() { return leaveTaken; }
    public int getBalance() { return balance; }

}
