package seng202.team5.data;

public interface Data {

    /**
     * Checks that the values of the variables of Data are in appropriate form,
     * Checking for null variables, size of variables and ensuring vairables are in a valid domain.
     *
     * @return int 1 if the check passes, otherwise a negative value that corresponds to a certain variable.
     */
    int checkValues();

    /**
     * Checks every variable of Data against a list of possible null representations potentially used
     * in files, or returned by a gui empty field. If a null representation is found, replaces that value with null,
     */
    void convertBlanksToNull();
}
