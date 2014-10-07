package ca.bcit.infosys.employee;

/**
 * Login Credentials.
 * @author blink
 */
public class Credentials {
    /** The login ID. */
    private String userName;
    /** The password. */
    private String password;
    /**
     * @return the loginID
     */
    public final String getUserName() {
        return userName;
    }
    /**
     * @param id the loginID to set
     */
    public final void setUserName(final String id) {
        userName = id;
    }
    /**
     * @return the password
     */
    public final String getPassword() {
        return password;
    }
    /**
     * @param pw the password to set
     */
    public final void setPassword(final String pw) {
        password = pw;
    }

}
