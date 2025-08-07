package context;

public class PersonContext {
    //ThreadLocal variable to store a random first name for each thread, initialized to null
    private final ThreadLocal<String> randomFirstName = ThreadLocal.withInitial(() -> null);
    private final ThreadLocal<String> randomLastName = ThreadLocal.withInitial(() -> null);
    private final ThreadLocal<String> randomemailAddress = ThreadLocal.withInitial(() -> null);

    // ThreadLocal variable to store an integer for each thread, initialized to 0
    //private final ThreadLocal<Integer> someNumber = ThreadLocal.withInitial(() -> 0);

    public void setRandomFirstName(String randomFirstName) {
        this.randomFirstName.set(randomFirstName);
    }

    public void setRandomLastName(String randomLastName) {
        this.randomLastName.set(randomLastName);
    }

    public void setRandomEmailAddress(String emailAddress) {
        this.randomemailAddress.set(emailAddress);
    }

    public String getRandomFirstName() {
        return randomFirstName.get();
    }

    public String getRandomLastName() {
        return randomLastName.get();
    }

    public String getRandomEmailAddress() {
        return randomemailAddress.get();
    }
}
