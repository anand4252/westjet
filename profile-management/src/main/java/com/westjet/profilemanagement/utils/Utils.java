package com.westjet.profilemanagement.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

@Slf4j
public class Utils {

    public static ByteArrayInputStream getInputStream(ByteArrayOutputStream byteArrayOutputStream){
        ByteArrayInOutStream byteArrayInOutStream = new ByteArrayInOutStream(byteArrayOutputStream.size());
//        return byteArrayInOutStream.getInputStream();
        return byteArrayInOutStream.getInputStream();
    }
}
