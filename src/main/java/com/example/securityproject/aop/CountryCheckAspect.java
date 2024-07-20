package com.example.securityproject.aop;


import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CountryResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;
import java.net.InetAddress;

@Aspect
@Component
public class CountryCheckAspect {

    @Autowired
    private DatabaseReader geoIp2DatabaseReader;

    @Around("@annotation(com.example.securityproject.aop.LocationChecking)")
    public Object checkCountry(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String ipAddress = getClientIP(request);
        String country = getCountryFromIP(ipAddress);

        if ("Iran".equalsIgnoreCase(country)) {
            throw new Exception("Access denied from Iran.");
        }

        return joinPoint.proceed();
    }

    private String getClientIP(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-Forwarded-For");
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        }
        return ipAddress;
    }

    private String getCountryFromIP(String ipAddress) {

        if (ipAddress.equals("127.0.0.1") || ipAddress.equalsIgnoreCase("localhost")) {
            return "localhost";
        }
        try {
            InetAddress ip = InetAddress.getByName(ipAddress);
            CountryResponse response = geoIp2DatabaseReader.country(ip);
            return response.getCountry().getName();
        } catch (IOException | GeoIp2Exception e) {
            e.printStackTrace();
            return "Unknown"; // Default country if there's an error
        }
    }
}
