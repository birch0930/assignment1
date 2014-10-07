package ca.bcit.infosys.employee;

/**
 * A class representing a single Employee.
 *
 * @author Bruce Link
 *
 */
public class Employee {
    /** The employee's name. */
    private String name;
    /** The employee's employee number. */
    private int empNumber;
    /** The employee's login ID. */
    private String userName;


    /**
     * The no-argument constructor. Used to create new employees from within the
     * application.
     */
    public Employee() {
    }

    /**
     * The argument-containing constructor. Used to create the initial employees
     * who have access as well as the administrator.
     *
     * @param empName the name of the employee.
     * @param number the empNumber of the user.
     * @param id the loginID of the user.
     */
    public Employee(final String empName, final int number, final String id) {
        name = empName;
        empNumber = number;
        userName = id;
    }

    /**
     * @return the name
     */
    public final String getName() {
        return name;
    }

    /**
     * @param empName the name to set
     */
    public final void setName(final String empName) {
        name = empName;
    }

    /**
     * @return the empNumber
     */
    public final int getEmpNumber() {
        return empNumber;
    }

    /**
     * @param number the empNumber to set
     */
    public final void setEmpNumber(final int number) {
        empNumber = number;
    }

    /**
     * @return the userName
     */
    public final String getUserName() {
        return userName;
    }

    /**
     * @param id the userName to set
     */
    public final void setUserName(final String id) {
        userName = id;
    }


}
