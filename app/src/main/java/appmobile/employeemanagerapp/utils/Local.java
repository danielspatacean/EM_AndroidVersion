package appmobile.employeemanagerapp.utils;


import android.app.Activity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import appmobile.employeemanagerapp.activities.AddPersonActivity;
import appmobile.employeemanagerapp.activities.ListActivity;
import appmobile.employeemanagerapp.tasks.AddEmployeeAsync;
import appmobile.employeemanagerapp.tasks.DeleteAsync;
import appmobile.employeemanagerapp.tasks.EditAsync;

public class Local {
    public static String list;
    public static File path;
    public static File file;

    public static void Initialize(Activity activity) {
        path = activity.getFilesDir();
        file = new File(path, "locals.txt");
    }

    public static void SetList(String myJSON){
        list = myJSON;
    }

    public static String getList(){
        return list;
    }

    public static void WriteToFile(String action, String name, String phone, String address) {
        FileOutputStream stream = null;
        try {
            stream = new FileOutputStream(file);
            stream.write((action + ";" + name + ";" + phone + ";" + address+"\n").getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void UpdateFileForDelete(Activity activity){
            InputStream instream = null;
            try {
                instream = new FileInputStream(file);

                if (instream != null) {
                    InputStreamReader inputreader = new InputStreamReader(instream);
                    BufferedReader buffreader = new BufferedReader(inputreader);

                    String line;
                    do {
                        line = buffreader.readLine();
                        System.out.println(line);
                        String[] attributes = line.split(";");
                        if (attributes[0].equals("DELETE")) {
                            String name = attributes[1];
                            String phone = attributes[2];
                            String address = attributes[3];
                            DeleteAsync sendPostReqAsyncTask = new DeleteAsync((ListActivity) activity);
                            sendPostReqAsyncTask.execute(name, phone, address);
                        }
                    } while (line != null);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                try {
                    if (instream != null) instream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
    }

    public static void UpdateFromFileForAdd(Activity activity) {
        InputStream instream = null;
        try {
            instream = new FileInputStream(file);

            if (instream != null) {
                InputStreamReader inputreader = new InputStreamReader(instream);
                BufferedReader buffreader = new BufferedReader(inputreader);

                String line;
                do {
                    line = buffreader.readLine();
                    System.out.println(line);
                    String[] attributes = line.split(";");
                    if (attributes[0].equals("ADD")) {
                        String name = attributes[1];
                        String phone = attributes[2];
                        String address = attributes[3];
                        AddEmployeeAsync sendPostReqAsyncTask = new AddEmployeeAsync((AddPersonActivity) activity);
                        sendPostReqAsyncTask.execute(name, phone, address);
                    }
                } while (line != null);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (instream != null) instream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void UpdateFromFileForEdit(Activity activity) {
        InputStream instream = null;
        try {
            instream = new FileInputStream(file);

            if (instream != null) {
                InputStreamReader inputreader = new InputStreamReader(instream);
                BufferedReader buffreader = new BufferedReader(inputreader);

                String line;
                do {
                    line = buffreader.readLine();
                    System.out.println(line);
                    String[] attributes = line.split(";");
                    if (attributes[0].equals("EDIT")) {
                        String oldName = attributes[1];
                        String oldPhone = attributes[2];
                        String newName = attributes[3];
                        String newPhone = attributes[4];
                        String newAddress = attributes[4];
                        EditAsync sendPostReqAsyncTask = new EditAsync((ListActivity) activity);
                        sendPostReqAsyncTask.execute(oldName, oldPhone, newName, newPhone, newAddress);
                    }
                } while (line != null);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (instream != null) instream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void ClearFile(){
        try {
            FileOutputStream writer = new FileOutputStream(file);
            writer.write(("").getBytes());
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void WriteToFileForEdit(String action,
                                          String oldName,
                                          String oldPhone,
                                          String newName,
                                          String newPhone,
                                          String newAddress)
    {
        FileOutputStream stream = null;
        try {
            stream = new FileOutputStream(file);
            stream.write((action + ";" + oldName + ";" + oldPhone + ";" + newName + ";" + newPhone +";" + newAddress).getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
