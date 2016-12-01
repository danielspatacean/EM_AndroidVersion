package appmobile.employeemanagerapp.utils;


public class Utils {
    public static boolean LOGGED = false;
    public static String AppDetails = ("You can view all the employees from your company \n\n"+
                                        "Add new employees \n\n"+
                                        "Edit existing employees \n\n"+
                                        "Delete employees\n\n"+
                                        "Search employees\n\n"
    );

    public static final String EmptyLoginFields = "Please complete username and password.";
    public static final String IncorrectPhoneNr = "Incorrect phone number.";
    public static final String EmptyFields = "Please complete all fields.";
    public static final String name = "name";
    public static final String phone = "phone";
    public static final String address = "address";
    public static final String InsertedMessage = "User inserted!";
    public static final String success = "success";
    public static final String WrongErrorMessage = "Something went wrong";
    public static final String DeletedMessage = "Employee deleted";
    public static final String oldName = "oldName";
    public static final String oldPhone = "oldPhone";
    public static final String newName = "newName";
    public static final String newPhone = "newPhone";
    public static final String newAddress = "newAddress";
    public static final String EditedMessage = "Employee edited";
    public static final CharSequence WaitMessage = "Please wait";
    public static final CharSequence LoadingMessage = "Loading...";
    public static final String username = "username";
    public static final String password = "password";
    public static final String InvalidCredentialsMessage = "Invalid username or password";


    public static void Login(){
        LOGGED = true;
    }
    public static void Logout(){
        LOGGED = false;
    }
}
