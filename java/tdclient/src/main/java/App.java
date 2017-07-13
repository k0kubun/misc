import com.treasuredata.client.TDClient;

public class App {
    public static void main(String[] args) {
        TDClient client = TDClient.newClient();
        client.createBulkImportSession("test-session", "aaa", "bbb");
        client.close();
    }
}
