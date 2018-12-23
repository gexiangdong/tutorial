package cn.devmgr.tutorial.test;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;

/**
 * 用HttpClient调用RESTful API (TvSeriesController.addPhoto)
 * 实现文件上传的例子
 */
public class UploadFile {

    public static void main(String[] argvs) throws Exception{
        File f1 = new File("src/test/resources/1.jpg");
        File f2 = new File("src/test/resources/2.jpg");

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {

            HttpEntity data = MultipartEntityBuilder.create()
                    .setMode(HttpMultipartMode.RFC6532)
                    .addBinaryBody("photo", f1, ContentType.DEFAULT_BINARY, f1.getName())
                    .addBinaryBody("photo", f2, ContentType.DEFAULT_BINARY, f2.getName())
                    .addTextBody("fileId", "110", ContentType.DEFAULT_BINARY)
                    .build();

            HttpUriRequest request = RequestBuilder
                    .post("http://127.0.0.1:8080/tvseries/10/photos")
                    .setEntity(data)
                    .build();

            ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
                @Override
                public String handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
                    int status = response.getStatusLine().getStatusCode();
                    System.out.println(status);
                    if (status == 200) {
                        HttpEntity entity = response.getEntity();
                        return entity != null ? EntityUtils.toString(entity) : null;
                    } else {
                        throw new ClientProtocolException("Unexpected response status: " + status);
                    }
                }
            };
            String responseBody = httpClient.execute(request, responseHandler);
            System.out.println(responseBody);
        }
    }
}
