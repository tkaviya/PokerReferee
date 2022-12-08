package com.t3ratech;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/***************************************************************************
 * Created:     08 / 12 / 2022                                             *
 * Platform:    Ubuntu Linux x86_64                                        *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 ***************************************************************************/

@SpringBootApplication
public class PokerReferee implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(PokerReferee.class, args);
    }

    @Override
    public void run(String... args) {}
}
