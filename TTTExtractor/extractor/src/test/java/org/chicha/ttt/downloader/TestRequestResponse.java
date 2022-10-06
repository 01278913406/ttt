package org.chicha.ttt.downloader;

import org.chicha.ttt.extractor.downloader.Request;
import org.chicha.ttt.extractor.downloader.Response;

final class TestRequestResponse {
    private final Request request;
    private final Response response;

    public TestRequestResponse(Request request, Response response) {
        this.request = request;
        this.response = response;
    }

    public Request getRequest() {
        return request;
    }

    public Response getResponse() {
        return response;
    }
}
