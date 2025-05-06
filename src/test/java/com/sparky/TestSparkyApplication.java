package com.sparky;

import org.springframework.boot.SpringApplication;

public class TestSparkyApplication {

    public static void main(String[] args) {
        SpringApplication.from(SparkyApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
