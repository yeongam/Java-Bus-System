package com.bus.timetable;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BusTimetableApplication {
    public static void main(String[] args) {
        new java.io.File("data").mkdirs(); // SQLite DB 파일 저장 디렉터리
        SpringApplication.run(BusTimetableApplication.class, args);
    }
}