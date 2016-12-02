package appmobile.employeemanagerapp.utils;

/**
 * The validator object.
 */

public class Validator {

    public ErrorCodes ValidateNewEmployee(String name, String phone, String address){
        if (name.length() ==0 || phone.length() == 0 || address.length() == 0){
            return ErrorCodes.EMPTY_FIELD;
        }
        else if (phone.length()<10 || !isNumeric(phone) || phone.charAt(0) != '0'){
            return ErrorCodes.INVALID_PHONE;
        }
        return ErrorCodes.EMPTY_FIELD.OK;
    }

    private static boolean isNumeric(String strNum) {
        boolean ret = true;
        try {

            Double.parseDouble(strNum);

        }catch (NumberFormatException e) {
            ret = false;
        }
        return ret;
    }

    public ErrorCodes ValidateUsernameAndPassword(String username, String password) {
        if (username.length() == 0 || password.length() == 0){
            return ErrorCodes.EMPTY_FIELD;
        }
        return ErrorCodes.OK;
    }
}
