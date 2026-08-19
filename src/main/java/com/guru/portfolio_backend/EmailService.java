
package com.guru.portfolio_backend;

import com.guru.portfolio_backend.entity.ContactMessage;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class EmailService {

    public void sendContactEmail(ContactMessage message) {

        try {

            String apiKey = System.getenv("RESEND_API_KEY");
            String toEmail = System.getenv("MAIL_TO");

            String html = "<h2>New Portfolio Contact</h2>"
                    + "<p><strong>Name:</strong> " + message.getName() + "</p>"
                    + "<p><strong>Email:</strong> " + message.getEmail() + "</p>"
                    + "<p><strong>Business:</strong> " + message.getBusiness() + "</p>"
                    + "<p><strong>Message:</strong> " + message.getMessage() + "</p>";

            String json = "{"
                    + "\"from\":\"onboarding@resend.dev\","
                    + "\"to\":[\"" + toEmail + "\"],"
                    + "\"subject\":\"New Portfolio Contact - "
                    + escapeJson(message.getName()) + "\","
                    + "\"html\":\"" + escapeJson(html) + "\""
                    + "}";

            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.resend.com/emails"))
                    .header("Authorization", "Bearer " + apiKey)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse<String> response = client.send(
                    request,
                    HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() >= 200 &&
                    response.statusCode() < 300) {

                System.out.println("EMAIL SENT SUCCESSFULLY");
                System.out.println("Resend Response: " + response.body());

            } else {

                System.out.println("EMAIL SENDING FAILED");
                System.out.println("Resend Status: " + response.statusCode());
                System.out.println("Resend Response: " + response.body());
            }

        } catch (Exception e) {

            System.out.println("EMAIL SENDING FAILED");
            e.printStackTrace();
        }
    }

    private String escapeJson(String text) {

        return text
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r");
    }
}
