package com.tomtom.devday.speedbumper;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.gson.Gson;
import com.tomtom.devday.speedbumper.model.Bumpers;
import com.tomtom.devday.speedbumper.model.Coordinate;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpeedbumperApplicationTests {

    @Autowired
    Gson gson;

    @Test
    public void contextLoads() {

        final Bumpers bumpers = new Bumpers();
        final Coordinate coordinate = new Coordinate();
        coordinate.setLat(60.771944);
        coordinate.setLon(-135.118910);

        final Coordinate coordinate1 = new Coordinate();
        coordinate1.setLat(60.772589);
        coordinate1.setLon(-135.120434);
        final List<Coordinate> location = new ArrayList();

        location.add(coordinate);
        location.add(coordinate1);
        bumpers.setLocation(location);

        final String json = gson.toJson(bumpers);
        System.out.println(json);
    }

}
