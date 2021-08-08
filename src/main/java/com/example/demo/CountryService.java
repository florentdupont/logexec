package com.example.demo;


import com.example.demo.log.DebugExec;
import com.example.demo.log.LogExec;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class CountryService {

    @DebugExec
    public List<Country> doSomething() {
        return Arrays.asList(new Country("FR", "France"), new Country("DE", "Allemagne"));
    }


    @LogExec
    public List<Country> doSomethingElse() {
        doSomethingPrivate();
        return Arrays.asList(new Country("FR", "France"), new Country("DE", "Allemagne"));
    }

    @LogExec
    private void doSomethingPrivate() {
        System.out.println("blabla");
    }
}
