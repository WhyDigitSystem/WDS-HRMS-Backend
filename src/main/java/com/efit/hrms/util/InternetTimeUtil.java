package com.efit.hrms.util;

import java.net.InetAddress;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.TimeInfo;

public class InternetTimeUtil {
    private static final String TIME_SERVER = "time.google.com";

    public static void main(String[] args) {
		LocalDate getInternetDate123=getInternetDate();
		System.out.println(getInternetDate123);
	}
    public static LocalDate getInternetDate() {
        try {
            NTPUDPClient client = new NTPUDPClient();
            client.setDefaultTimeout(5000);
            client.open();

            InetAddress address = InetAddress.getByName(TIME_SERVER);
            TimeInfo timeInfo = client.getTime(address);

            long returnTime = timeInfo.getMessage().getTransmitTimeStamp().getTime();
            Instant instant = Instant.ofEpochMilli(returnTime);

            return instant.atZone(ZoneId.systemDefault()).toLocalDate();
        } catch (Exception e) {
            e.printStackTrace();
            // fallback: system time (only if absolutely necessary)
            return LocalDate.now();
        }
    }
}
