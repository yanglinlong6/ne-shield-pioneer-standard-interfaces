package cn.com.glsx.stdinterface.modules.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import javax.xml.ws.http.HTTPException;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;


/**
 * http??????
 */
@Slf4j
public class HttpUtils {

    public final static ContentType XML_UTF8 = ContentType.create("text/xml", "utf-8");
    public final static ContentType XML_GBK = ContentType.create("text/xml", "gbk");
    public final static ContentType JSON_UTF8 = ContentType.create("application/json", "utf-8");
    final static int CONNECTION_TIMEOUT = 10000; // ?????????????????? 10 ?????? ??????????????????
    final static int SOCKET_TIMEOUT = 180000; // ?????????????????? 3 ??????
    final static int SEARCH_CONNECTION_TIMEOUT = 500; // ??????????????????????????????????????????,????????????????????? CONNECTION_TIMEOUT
    static PoolingHttpClientConnectionManager httpClientConnectionManager = null;

    static {
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", new PlainConnectionSocketFactory()).build();

        httpClientConnectionManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        httpClientConnectionManager.setMaxTotal(200); // ????????????????????????
        httpClientConnectionManager.setDefaultMaxPerRoute(20); // ????????????????????????????????????,??????????????????20 ?????????,?????? 200 ?????????,?????? 10 ?????????
    }

    public static CloseableHttpClient getHttpClient() {
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(CONNECTION_TIMEOUT).setSocketTimeout(SOCKET_TIMEOUT)
                .setConnectionRequestTimeout(SEARCH_CONNECTION_TIMEOUT).build();

        SocketConfig socketConfig = SocketConfig.custom()
                .setSoKeepAlive(true)
                .setTcpNoDelay(true).build();

        // ??????????????????
        HttpRequestRetryHandler httpRequestRetryHandler = (exception, executionCount, context) -> {
            if (executionCount >= 3) {// ????????????????????? 3 ???????????????
                return false;
            }
            if (exception instanceof NoHttpResponseException) {// ????????????????????????????????????????????????
                return true;
            }
            if (exception instanceof InterruptedIOException) {// ??????
                return false;
            }
            if (exception instanceof UnknownHostException) {// ????????????????????????
                return false;
            }

            HttpClientContext clientContext = HttpClientContext.adapt(context);
            HttpRequest request = clientContext.getRequest();
            // ??????????????????????????????????????????
            if (!(request instanceof HttpEntityEnclosingRequest)) {
                return true;
            }
            return false;
        };

        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(httpClientConnectionManager)
                .setDefaultRequestConfig(requestConfig)
                .setRetryHandler(httpRequestRetryHandler)
                .setDefaultSocketConfig(socketConfig).build();

        return httpClient;
    }

    /**
     * ??????:sanri <br/>
     * ??????:2017-7-26??????3:47:02<br/>
     * ??????:??? map ??? ??????????????????NameValuePair ??????  <br/>
     *
     * @param params ???:????????????????????????  yyyy-MM-dd
     * @return
     */
    public static List<NameValuePair> transferParam(Map<String, ? extends Object> params) {
        return transferParam(params, "yyyy-MM-dd");
    }

    public static List<NameValuePair> transferParam(Map<String, ? extends Object> params, String dateFormat) {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        if (params != null && !params.isEmpty()) {
            Set<?> entrySet = params.entrySet();
            Iterator<?> paramIterator = entrySet.iterator();
            while (paramIterator.hasNext()) {
                Map.Entry<String, ? extends Object> param = (Map.Entry<String, ? extends Object>) paramIterator.next();
                Object value = param.getValue();
                if (value == null) {
                    nameValuePairs.add(new BasicNameValuePair(param.getKey(), null));
                } else {
                    if (value instanceof Date) {
                        Date date = (Date) value;
                        String dateString = "";
                        if (StringUtils.isBlank(dateFormat)) {
                            dateString = date.toString();
                        } else {
                            dateString = DateFormatUtils.format(date, dateFormat);
                        }
                        nameValuePairs.add(new BasicNameValuePair(param.getKey(), dateString));
                        continue;
                    }
                    nameValuePairs.add(new BasicNameValuePair(param.getKey(), ObjectUtils.toString(value)));
                }
            }
        }
        return nameValuePairs;
    }

    /**
     * ????????????????????????
     *
     * @param url
     * @param params
     * @return
     * @throws IOException
     */
    @Deprecated
    public static String post(String url, Map<String, String> params) throws IOException {
        return postFormData(url, params, Consts.UTF_8);
    }

    /**
     * ??????:sanri <br/>
     * ??????:2018-4-12??????3:27:43<br/>
     * ??????:???????????????????????? <br/>
     *
     * @param url
     * @param params
     * @return
     * @throws IOException
     */
    @Deprecated
    public static String post(String url, Map<String, String> params, int timeoutSecond) throws IOException {
        return post(url, params, timeoutSecond, "utf-8");
    }

    /**
     * ???????????????????????? ,???????????????????????????
     *
     * @param url
     * @param params
     * @param timeoutSecond
     * @param charset
     * @return
     * @throws IOException
     */
    @Deprecated
    public static String post(String url, Map<String, String> params, int timeoutSecond, String charset) throws IOException {
        return postFormData(url, params, Charset.forName(charset));
    }


    @Deprecated
    public static String postForTSP(String url, Map<String, Object> params, int timeoutSecond, String charset) throws IOException {
        return postFormDataTSP(url, params, Charset.forName(charset));
    }

    /**
     * ????????????????????????
     *
     * @param url
     * @param charset
     * @return
     */
    @Deprecated
    public static String get(String url, String charset) throws IOException {
        return getData(url, null, Charset.forName(charset));
    }

    /**
     * ??????:sanri <br/>
     * ??????:2018-4-12??????3:30:52<br/>
     * ??????:???????????????????????? <br/>
     *
     * @param url
     * @param params
     * @param charset
     * @return
     * @throws IOException
     */
    @Deprecated
    public static String get(String url, Map<String, String> params, String charset) throws IOException {
        return get(url, params, 10, charset);
    }

    @Deprecated
    public static String get(String url, Map<String, String> params) throws IOException {
        return get(url, params, 10, "utf-8");
    }

    /**
     * ???????????????????????? timeout ????????????
     *
     * @param url
     * @param params
     * @param timeoutSecond
     * @param charset
     * @return
     */
    @Deprecated
    public static String get(String url, Map<String, String> params, int timeoutSecond, String charset) throws IOException {
        return getData(url, params, Charset.forName(charset));
    }

    @Deprecated
    public static String postSms(String url, Map<String, String> params, String charset) throws Exception {
        return postsms(url, params, 10, charset);
    }

    @Deprecated
    public static String postsms(String url, Map<String, String> params, int timeout, String charset) throws IOException {
        long startTime = System.currentTimeMillis();
        HttpClient httpclient = new DefaultHttpClient();
        httpclient.getParams().setIntParameter("http.socket.timeout", timeout * 1000);
        httpclient.getParams().setBooleanParameter("http.protocol.expect-continue", false);
        StringBuilder retVal = new StringBuilder();
        try {
            List<NameValuePair> formparams = new ArrayList<NameValuePair>();
            if (params != null) {
                for (Map.Entry<String, String> param : params.entrySet()) {
                    formparams.add(new BasicNameValuePair(param.getKey(), param.getValue()));
                }
            }
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, charset);
            HttpPost httppost = new HttpPost(url);
            httppost.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(10, false));
            httppost.setEntity(entity);
            HttpResponse resp = httpclient.execute(httppost);
            if (resp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity httpentity = resp.getEntity();
                String httpcharset = EntityUtils.getContentCharSet(entity);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpentity.getContent(), httpcharset));
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    retVal.append(line);
                }
                bufferedReader.close();
            }
        } catch (IOException e) {
            log.error(" post error : " + " url:" + url + " params" + params, e);
            throw e;
        } finally {
            httpclient.getConnectionManager().shutdown();
            long endTime = System.currentTimeMillis();
            if (log.isInfoEnabled()) {
                log.info("connection  end,sartTime = " + startTime + "  , endTime =" + endTime + " , times:" + (endTime - startTime));
            }
        }
        log.info(" url:" + url + " params" + params);
        log.info("post back" + retVal);
        return retVal.toString();
    }

    /**
     * ??????:sanri <br/>
     * ??????:2017-10-19??????2:25:40<br/>
     * ??????:????????? url ???????????? <br/>
     *
     * @param url
     * @param data
     * @param contentType
     * @return
     * @throws IOException
     */
    public static String postData(String url, String data, ContentType contentType) throws IOException, HTTPException {
        HttpClient httpClient = getHttpClient();

        //?????????,?????????????????????
        HttpPost postMethod = new HttpPost(url);
        if (contentType != null) {
            postMethod.addHeader("Content-Type", contentType.toString());
        }
        postMethod.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:56.0) Gecko/20100101 Firefox/56.0");
        StringEntity dataEntity = new StringEntity(data, contentType);
        postMethod.setEntity(dataEntity);
        HttpResponse response = null;

        try {
            //????????????,?????????????????????????????????
            response = httpClient.execute(postMethod);

            //????????????
            HttpEntity entity = response.getEntity();
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode != 200) {
                //???????????????????????????
                if (statusCode == 302) {
                    Header firstHeader = response.getFirstHeader("location");
                    String location = firstHeader.getValue();
                    return postData(location, data, contentType);
                }

                HTTPException exception = new HTTPException(statusCode);
                log.error("????????????" + url + "??????,http ????????????:" + statusCode, exception);
                throw exception;
            }

            return EntityUtils.toString(entity, contentType.getCharset());
        } catch (ClientProtocolException e) {
            throw new IllegalArgumentException("????????????????????? ????????? url ?????? url: " + url, e);
        } finally {
            HttpClientUtils.closeQuietly(response);
        }
    }

    /**
     * ??????:sanri <br/>
     * ??????:2018-3-28??????8:57:50<br/>
     * ??????: ????????????????????????<br/>
     *
     * @param url
     * @param urlEncoded
     * @return
     * @throws IOException
     * @throws ParseException
     */
    public static String getData(final String url, final String urlEncoded) throws IOException {
        HttpClient httpClient = getHttpClient();

        String queryUrl = url;
        if (StringUtils.isNotBlank(urlEncoded)) {
            queryUrl = url + "?" + urlEncoded;
        }

        HttpGet httpGet = new HttpGet(queryUrl);
        httpGet.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:56.0) Gecko/20100101 Firefox/56.0");
        HttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode != 200) {
                //???????????????????????????
                if (statusCode == 302) {
                    Header firstHeader = response.getFirstHeader("location");
                    String location = firstHeader.getValue();
                    return getData(location, urlEncoded);
                }

                HTTPException exception = new HTTPException(statusCode);
                log.error("????????????" + url + "??????,http ????????????:" + statusCode, exception);
                throw exception;
            }

            HttpEntity msgEntity = response.getEntity();
            String message = EntityUtils.toString(msgEntity);
            return message;
        } catch (ClientProtocolException e) {
            throw new IllegalArgumentException("????????????????????? ????????? url ?????? url: " + url, e);
        } finally {
            HttpClientUtils.closeQuietly(response);
        }
    }

    /**
     * ??????:sanri <br/>
     * ??????:2017-7-26??????3:36:40<br/>
     * ??????:?????????????????????????????????????????????????????????????????????????????? <br/>
     *
     * @param url
     * @param params
     * @return
     * @throws IOException
     */
    @Deprecated
    public static String getData(String url, Map<String, String> params, Charset charset) throws IOException {
        List<NameValuePair> nameValuePairs = transferParam(params);
        HttpEntity urlEncodedFormEntity = new UrlEncodedFormEntity(nameValuePairs, charset);
        String urlEncoded = EntityUtils.toString(urlEncodedFormEntity, charset);
        String data = getData(url, urlEncoded);
        return data;
    }

    /**
     * ??????:sanri <br/>
     * ??????:2017-11-7??????6:24:30<br/>
     * ??????:?????????????????? json ?????? <br/>
     *
     * @param url
     * @param params
     * @return
     * @throws IllegalArgumentException
     * @throws IOException
     */
    public static JSONObject getJSON(String url, Map<String, String> params) throws IOException {
        return getJSON(url, params, Consts.UTF_8);
    }

    public static JSONObject getJSON(String url, Map<String, String> params, Charset charset) throws IOException {
        String retData = getData(url, params, charset);
        if (StringUtils.isNotBlank(retData)) {
            return JSONObject.parseObject(retData);
        }
        return new JSONObject();
    }

    /**
     * ?????????????????????????????? json ??????,?????? String
     * ??????:sanri <br/>
     * ??????:2018-4-12??????2:42:58<br/>
     * ??????: <br/>
     *
     * @param url
     * @param json
     * @return
     * @throws IOException
     */
    @Deprecated
    public static String postJson(String url, JSONObject json) throws IOException {
        JSONObject postJson = postJSON(url, json, JSON_UTF8);
        if (postJson == null) {
            return "";
        }
        return postJson.toString();
    }

    /**
     * ??????:sanri <br/>
     * ??????:2017-7-26??????3:36:40<br/>
     * ??????:??????????????????,?????????????????????????????? <br/>
     *
     * @param url
     * @param params
     * @return
     * @throws IOException
     */
    public static String postFormData(String url, Map<String, String> params) throws IOException {
        return postFormData(url, params, Consts.UTF_8);
    }

    public static String postFormData(String url, Map<String, String> params, Charset charset) throws IOException {
        CloseableHttpClient httpClient = getHttpClient();
        List<NameValuePair> nameValuePairs = transferParam(params);
        HttpPost postMethod = new HttpPost(url);
        if (CollectionUtils.isNotEmpty(nameValuePairs)) {
            HttpEntity entity = new UrlEncodedFormEntity(nameValuePairs, charset);
            postMethod.setEntity(entity);
        }
        postMethod.addHeader("Cookie", "JSESSIONID=node0oze509ig0of0vksk2dshvoi12.node0; fileDownload=true");
        postMethod.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36");
        HttpResponse response = null;
        try {
            response = httpClient.execute(postMethod);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode != 200) {
                //???????????????????????????
                if (statusCode == 302) {
                    Header firstHeader = response.getFirstHeader("location");
                    String location = firstHeader.getValue();
                    return postFormData(location, params, charset);
                }

                HTTPException exception = new HTTPException(statusCode);
                log.error("????????????" + url + "??????,http ????????????:" + statusCode, exception);
                throw exception;
            }
            HttpEntity msgEntity = response.getEntity();
            String message = EntityUtils.toString(msgEntity, charset);
            return message;
        } finally {
            HttpClientUtils.closeQuietly(response);
        }
    }

    public static String postFormDataTSP(String url, Map<String, Object> params, Charset charset) throws IOException {
        CloseableHttpClient httpClient = getHttpClient();
        List<NameValuePair> nameValuePairs = transferParam(params);
        HttpPost postMethod = new HttpPost(url);
        if (CollectionUtils.isNotEmpty(nameValuePairs)) {
            HttpEntity entity = new UrlEncodedFormEntity(nameValuePairs, charset);
            postMethod.setEntity(entity);
        }
        postMethod.addHeader("Cookie", "JSESSIONID=node0oze509ig0of0vksk2dshvoi12.node0; fileDownload=true");
        postMethod.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36");
        HttpResponse response = null;
        try {
            response = httpClient.execute(postMethod);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode != 200) {
                //???????????????????????????
                if (statusCode == 302) {
                    Header firstHeader = response.getFirstHeader("location");
                    String location = firstHeader.getValue();
                    return postFormDataTSP(location, params, charset);
                }

                HTTPException exception = new HTTPException(statusCode);
                log.error("????????????" + url + "??????,http ????????????:" + statusCode, exception);
                throw exception;
            }
            HttpEntity msgEntity = response.getEntity();
            return EntityUtils.toString(msgEntity, charset);
        } finally {
            HttpClientUtils.closeQuietly(response);
        }
    }

    /**
     * ??????:sanri <br/>
     * ??????:2017-7-26??????5:59:57<br/>
     * ??????:??????????????? xml ?????? <br/>
     *
     * @param url
     * @param xml
     * @return
     * @throws IOException
     * @throws IllegalArgumentException
     */
    public static String postXml(String url, String xml) throws IllegalArgumentException, IOException {
        return postXml(url, xml, XML_UTF8);
    }

    public static String postXml(String url, String xml, ContentType contentType) throws IOException, IllegalArgumentException {
        return postData(url, xml, contentType);
    }

    /**
     * ???????????????????????? timeout ????????????
     *
     * @param url
     * @param xml
     * @param encoding
     * @param timeout
     * @return
     */
    @Deprecated
    public static String postXml(String url, String xml, String encoding, int timeout) throws IOException {
        return postData(url, xml, ContentType.create("text/xml", Charset.forName(encoding)));
    }

    /**
     * ???????????????????????? timeout ????????????
     * ??????:sanri <br/>
     * ??????:2018-4-12??????2:35:52<br/>
     * ??????: <br/>
     *
     * @param url
     * @param buffer
     * @param timeout
     * @param charset
     * @return
     * @throws IOException
     */
    @Deprecated
    public static String postXml(String url, byte[] buffer, int timeout, String charset) throws IOException {
        try {
            String xmlData = new String(buffer, charset);
            return postXml(url, xmlData, charset, timeout);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Deprecated
    public static String postJsonArray(String url, JSONArray json) {
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);
        post.setHeader("Content-Type", "application/json");
        post.addHeader("Authorization", "Basic YWRtaW46");
        String result = "";
        try {

            StringEntity s = new StringEntity(json.toString(), "utf-8");
            s.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            post.setEntity(s);

            // ????????????
            HttpResponse httpResponse = client.execute(post);

            // ?????????????????????
            InputStream inStream = httpResponse.getEntity().getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    inStream, StandardCharsets.UTF_8));
            StringBuilder strber = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                strber.append(line).append("\n");
            }

            inStream.close();

            result = strber.toString();
            System.out.println(result);

            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                System.out.println("???????????????????????????????????????");
            } else {
                System.out.println("?????????????????????");
            }
        } catch (Exception e) {
            System.out.println("????????????");
            throw new RuntimeException(e);
        }

        return result;
    }

    /**
     * ???????????????????????????
     *
     * @param url
     * @param timout
     * @return
     */
    public static HttpResponse customTimeoutGetReponse(String url, int timout) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet(url);

        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(timout)
                .setConnectionRequestTimeout(timout)
                .setSocketTimeout(timout)
                .setExpectContinueEnabled(false)
                .build();
        request.setConfig(requestConfig);

        return httpClient.execute(request);
    }

    /**
     * ???????????????????????????
     *
     * @param resourceUrl
     * @return
     */
    public static InputStream getURLResourceInputStream(String resourceUrl) {
        URL url = null;
        HttpURLConnection conn = null;
        BufferedInputStream bin = null;
        try {
            /*???????????????????????????,????????????url*/
            url = new URL(resourceUrl);
            /*?????????????????????????????????????????????????????????????????????in????????????url??????????????????????????????*/
            conn = (HttpURLConnection) url.openConnection();
            //?????????????????????
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            //???????????????
            bin = new BufferedInputStream(conn.getInputStream());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bin;
    }


    /**
     * ??????:sanri <br/>
     * ??????:2017-10-19??????2:23:53<br/>
     * ??????:json ?????? <br/>
     *
     * @param url
     * @param jsonObject
     * @return
     * @throws IOException
     */
    public static JSONObject postJSON(String url, JSONObject jsonObject, ContentType contentType) throws IOException {
        String postData = jsonObject.toJSONString();
        String retJsonData = postData(url, postData, contentType);
        if (StringUtils.isNotBlank(retJsonData)) {
            return JSONObject.parseObject(retJsonData);
        }
        return null;
    }

    public static JSONObject postJSON(String url, JSONObject jsonObject, ContentType contentType, Map<String, String> header) throws IOException {
        String postData = jsonObject.toJSONString();
        String retJsonData = postData(url, postData, contentType, header);
        if (StringUtils.isNotBlank(retJsonData)) {
            return JSONObject.parseObject(retJsonData);
        }
        return null;
    }


    public static String postData(String url, String data, ContentType contentType, Map<String, String> headers) throws IOException, HTTPException {
        HttpClient httpClient = getHttpClient();

        //?????????,?????????????????????
        HttpPost postMethod = new HttpPost(url);
        if (contentType != null) {
            postMethod.addHeader("Content-Type", contentType.toString());
        }
        postMethod.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:56.0) Gecko/20100101 Firefox/56.0");
        for (String header : headers.keySet()) {
            postMethod.addHeader(header, headers.get(header));
        }
        StringEntity dataEntity = new StringEntity(data, contentType);
        postMethod.setEntity(dataEntity);
        HttpResponse response = null;

        try {
            //????????????,?????????????????????????????????
            response = httpClient.execute(postMethod);

            //????????????
            HttpEntity entity = response.getEntity();
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode != 200) {
                //???????????????????????????
                if (statusCode == 302) {
                    Header firstHeader = response.getFirstHeader("location");
                    String location = firstHeader.getValue();
                    return postData(location, data, contentType);
                }

                HTTPException exception = new HTTPException(statusCode);
                log.error("????????????" + url + "??????,http ????????????:" + statusCode, exception);
                throw exception;
            }

            return EntityUtils.toString(entity, contentType.getCharset());
        } catch (ClientProtocolException e) {
            throw new IllegalArgumentException("????????????????????? ????????? url ?????? url: " + url, e);
        } finally {
            HttpClientUtils.closeQuietly(response);
        }
    }


    /**
     * ??????:sanri <br/>
     * ??????:2018-3-28??????8:58:38<br/>
     * ??????:????????? <br/>
     *
     * @param url
     * @param urlEncoded ???: ???????????????????????????
     * @return
     * @throws IOException
     */
    public static InputStream getStream(String url, String urlEncoded) throws IOException {
        CloseableHttpClient httpClient = getHttpClient();

        String queryUrl = url;
        if (StringUtils.isNotBlank(urlEncoded)) {
            queryUrl = url + "?" + urlEncoded;
        }

        HttpGet httpGet = new HttpGet(queryUrl);
        httpGet.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:56.0) Gecko/20100101 Firefox/56.0");
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode != 200) {
                //???????????????????????????
                if (statusCode == 302) {
                    Header firstHeader = response.getFirstHeader("location");
                    String location = firstHeader.getValue();
                    return getStream(location, urlEncoded);
                }

                HTTPException exception = new HTTPException(statusCode);
                log.error("????????????" + url + "??????,http ????????????:" + statusCode, exception);
                throw exception;
            }

            HttpEntity entity = response.getEntity();

            return entity.getContent();
        } catch (ClientProtocolException e) {
            throw new IllegalArgumentException("????????????????????? ????????? url ?????? url: " + url, e);
        } finally {
            if (response != null) {
                response.close();
            }
        }
    }
}