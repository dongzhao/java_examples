package com.dzhao.example.jpa;

import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.IOException;

@Mojo(defaultPhase = LifecyclePhase.INSTALL, name = "todo")
public class MyMojoHelper extends AbstractMojo {

    private DefaultHttpClient client = new DefaultHttpClient();

    @Parameter(required = true, defaultValue = "${host}")
    private String host;
    @Parameter(required = true, defaultValue = "${port}")
    private String port;
    @Parameter(required = true, defaultValue = "${user_name}")
    private String username;
    @Parameter(required = true, defaultValue = "${password}")
    private String password;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        DefaultHttpClient client = new DefaultHttpClient();
        client.getCredentialsProvider().setCredentials(
                new AuthScope(host, Integer.decode(port)),
                new UsernamePasswordCredentials(username, password)
        );

        HttpPost post = new HttpPost(port);

        try {
            HttpResponse httpResponse = client.execute(post);
            if (200 == httpResponse.getStatusLine().getStatusCode()) {
                System.out.println("Hello World!");
            } else {
                throw new RuntimeException("Failed");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
