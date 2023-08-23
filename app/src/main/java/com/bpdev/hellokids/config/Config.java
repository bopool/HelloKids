package com.bpdev.hellokids.config;

import android.util.Log;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.List;

public class Config {

    static InetAddress ipAddress;

    // 까먹을까봐 정해준다
    public static final String PREFERENCE_NAME = "HelloKidsTest";
    public static final String ACCESS_TOKEN = "accessToken";


    public static void main(String args[]) throws UnknownHostException {
        ipAddress = InetAddress.getLocalHost();
        Log.i("Config Call IPAdressTest", "현재 아이피 : " + ipAddress.getHostAddress());
        Log.i("Config Call IPAdressTest", "현재 호스트명 : " + ipAddress.getHostName());
    }

    public static final String HOST = ipAddress.getHostAddress().toString(); // 호스트는 끝에 / 없다! /가 있으면 path가 된다


}


