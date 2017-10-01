
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

public class Gambling {
//command line
//  testrpc --account="0xa8b7f177000b8193528eab77a0501b7e8b308297b2912bff8a43d9d8daf180a3,999999999999999999999" --account="0xae78473c4efa3795469085422d42a4de9eea61037c20eddc0930682bfedc8a26,10000000000000000000"

    String server;
    String player;
    String transaction;
    public Gambling(){
        
//    server   public    0xa91962710fff3c7c52929546e80150c356bb3479
//             private   0xa8b7f177000b8193528eab77a0501b7e8b308297b2912bff8a43d9d8daf180a3
//    player   public    0x2d37ac6f99539f66c0c11a3a39b4f1c5da8b82bb
//             private   0xae78473c4efa3795469085422d42a4de9eea61037c20eddc0930682bfedc8a26 
        server = "0xa91962710fff3c7c52929546e80150c356bb3479";
        player = "0x2d37ac6f99539f66c0c11a3a39b4f1c5da8b82bb";
        transaction = "";
    }
    public void addFunds(String amount){
        transaction = "{\"jsonrpc\":\"2.0\",\"method\":\"eth_sendTransaction\",\"params\": [{\"from\":\""+server+"\", \"to\":\""+player+"\", \"value\": "+amount+"e18}], \"id\":1}";
        send();
    }
    public void removeFunds(String amount){
        transaction = "{\"jsonrpc\":\"2.0\",\"method\":\"eth_sendTransaction\",\"params\": [{\"from\":\""+player+"\", \"to\":\""+server+"\", \"value\": "+amount+"e18}], \"id\":1}";
        send();
    }
    public double getFunds(){
        transaction = "{\"jsonrpc\":\"2.0\",\"method\":\"eth_getBalance\",\"params\":[\""+player+"\",\"latest\"],\"id\":5148}";
        return get();
    }
    public void send(){
        HttpClient httpClient = HttpClientBuilder.create().build();
        try {
            HttpPost request = new HttpPost("http://localhost:8545/");
            StringEntity params = new StringEntity(transaction);
            request.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
            request.setEntity(params);
            httpClient.execute(request);
            //handle response here...
        }catch (Exception ex) {
            System.out.println(ex);
        } finally {
            //Deprecated
            httpClient.getConnectionManager().shutdown(); 
       }       
    }
    public double get(){
        String value= "";
        double balance = 0;
        HttpClient httpClient = HttpClientBuilder.create().build();
        try {
            HttpPost request = new HttpPost("http://localhost:8545/");
            StringEntity params = new StringEntity(transaction);
            request.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
            request.setEntity(params);
            HttpResponse response = httpClient.execute(request);
            //httpClient.execute(request);
            //handle response here...
            HttpEntity entity = response.getEntity();
            value = EntityUtils.toString(entity);
            System.out.println(value);
            String temp[] = value.split(" ");
          //  System.out.println(temp[temp.length-1]);
            String temp2[] = temp[temp.length-1].split("\"");
           // System.out.println(temp2[temp2.length-2]);
            String finalResult = temp2[temp2.length-2].substring(2);
          //  System.out.println(finalResult);
             
            double test = new BigInteger(finalResult, 16).doubleValue();
           // long test = Long.parseLong(finalResult,16);
            balance = test;
            balance = balance / 100000;
            balance = balance/10000000;
            balance = balance/1000000;

        }catch (Exception ex) {
            System.out.println(ex);
        } finally {
            //Deprecated
            httpClient.getConnectionManager().shutdown(); 
       } 
        
        return balance;
    }
    /*public static void main(String[] args) throws IOException {

        Gambling test = new Gambling();
        Scanner kb = new Scanner(System.in);
        System.out.print("Enter amount to add to account: ");
        String input = kb.nextLine();
        test.addFunds(input);
        System.out.println("addFunds("+input+") was called");
        System.out.print("Enter amount to remove from account: ");
        input = kb.nextLine();
        test.removeFunds(input);
        System.out.println("removeFunds("+input+") was called");
        
            
        
       // Gambling test = new Gambling();
        System.out.println("getFunds() ---- getting funds");
        System.out.println(test.getFunds());

    }*/
    
}
