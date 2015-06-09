package com.dzhao.example.webservices;

import com.dzhao.example.webservices.api.MyRestService;

import javax.ws.rs.core.Response;

public class MyRestServiceImpl implements MyRestService{

    @Override
    public Response getInfo() {
        return Response.ok(MyRestService.class.getName()).build();
    }
}