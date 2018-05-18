package com.cbers.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class FireBaseMessage {

	public static final boolean SEND_MESSAGE = false;

	public static boolean send(String to, String message) {

		String payload = "{\r\n   \"content_available\": true,\r\n   \"priority\": \"high\",\r\n   \"to\": \""+to+"\",\r\n   "
				+ "\"notification\": {\r\n       \"title\": \"CBERS UPDATE\",\r\n       \"body\": \""+message+"\"\r\n   }\r\n}";

		System.out.println("Payload: "+ payload);
		return sendPOST("https://fcm.googleapis.com/fcm/send", payload);
	}

	private static boolean sendPOST(String url, String postPayload) {
		try {
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/json");
			con.setRequestProperty("Authorization", "key=AAAAbvingVM:APA91bFU19GmmhvZU2ksFvYccqRvxH-_q_qP_M8xH2tGLMtBoLiccxq6M0iZkaGkW0mJ0eq8x56ILyDOfXTy0ZiZ2aSXxN26BFbnBkp-ymGurp7GxWc2xRLlBiUjgAoDIe6fg_CQgc_O");

			// For POST only - START
			con.setDoOutput(true);
			OutputStream os = con.getOutputStream();
			os.write(postPayload.getBytes());
			os.flush();
			os.close();
			// For POST only - END

			int responseCode = con.getResponseCode();
			System.out.println("POST Response Code :: " + responseCode);

			if (responseCode == HttpURLConnection.HTTP_OK) { //success
				BufferedReader in = new BufferedReader(new InputStreamReader(
						con.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();

				// print result
				System.out.println("Response: "+response.toString());
				return true;
			} else {
				System.out.println("POST request not worked");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	//	public static void main(String args[]) {
	//		send("eK27URIVGI4:APA91bEKKnvDdD3VsQr4B-7GXZTRObswbtBd6I2l3nDVXspHblzy1J_RFf67b8mlSbRMCMrLo3WjwK19hqYZUsfN26MbbKQJdsVQTyPtLYWhmU_UbU5Fz_EzfkPsQovB_Ijoeyzd8y33", 
	//				"CBERS Doctor has sent you an update.");
	//	}

}
