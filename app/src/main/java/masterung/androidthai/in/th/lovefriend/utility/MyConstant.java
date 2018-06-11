package masterung.androidthai.in.th.lovefriend.utility;

public class MyConstant {

    private String hostFTPString = "ftp.androidthai.in.th";
    private String userFTPString = "love@androidthai.in.th";
    private String passwordString = "Abc12345";
    private int portAnInt = 21;

    private String urlAddData = "http://androidthai.in.th/love/addData.php";
    private String urlReadAllData = "http://androidthai.in.th/love/getAllData.php";

    private String[] columnUserStrings = new String[]{
            "id", "Name", "User", "Password", "Avata", "Post"};

    public String[] getColumnUserStrings() {
        return columnUserStrings;
    }

    public String getUrlReadAllData() {
        return urlReadAllData;
    }

    public String getUrlAddData() {
        return urlAddData;
    }

    public String getHostFTPString() {
        return hostFTPString;
    }

    public String getUserFTPString() {
        return userFTPString;
    }

    public String getPasswordString() {
        return passwordString;
    }

    public int getPortAnInt() {
        return portAnInt;
    }
}
