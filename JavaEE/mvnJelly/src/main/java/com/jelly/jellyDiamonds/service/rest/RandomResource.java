package com.jelly.jellyDiamonds.service.rest;

import java.util.Random;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path( "/random" )
public class RandomResource {

    Random rand = new Random();

    @GET
    @Produces( MediaType.TEXT_PLAIN )
    public int getRandomInt() {
        int randomInt = rand.nextInt( 10 ) + 1;
        return randomInt;
    }
}
