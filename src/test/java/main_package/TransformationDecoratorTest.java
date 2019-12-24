package main_package;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.Assert.*;

public class TransformationDecoratorTest {
    private Transformation t;

    @BeforeEach
    public void setup() {
        t = new SimpleTransformation();
    }
    @Test
    public void upperTest() {
        t = new TextTransformation(t);
        String[] predicted = new String[3];
        String[] result =  {"ŹREBIĘ", "MŁODE ŹREBIĘ", "ŹREBIĘ-012"};

        predicted[0] = t.transform("ŹREBIĘ", 0);
        predicted[1] = t.transform("młode źrebię", 0);
        predicted[2] = t.transform("ŹrEbIĘ-012", 0);

        Assert.assertArrayEquals(result, predicted);
    }
    @Test
    public void lowerTest() {
        t = new TextTransformation(t);
        String[] predicted = new String[3];
        String[] result =  {"źrebię", "młode źrebię", "źrebię-012"};

        predicted[0] = t.transform("ŹREBIĘ", 1);
        predicted[1] = t.transform("młode źrebię", 1);
        predicted[2] = t.transform("ŹrEbIĘ-012", 1);

        Assert.assertArrayEquals(result, predicted);
    }
    @Test
    public void inverseTest() {
        t = new TextTransformation(t);
        String[] predicted = new String[3];
        String[] result =  {"Ęiberź", "Edołm Ęiberź", "210-ęiberź"};

        predicted[0] = t.transform("Źrebię", 2);
        predicted[1] = t.transform("Młode Źrebię", 2);
        predicted[2] = t.transform("źrebię-012", 2);

        Assert.assertArrayEquals(result, predicted);
    }
    @Test // change to delete space after
    public void capitalTest() {
        t = new TextTransformation(t);
        String[] predicted = new String[3];
        String[] result =  {"Źrebię ", "Młode Źrebię ", "Źrebię-012 "};

        predicted[0] = t.transform("źrebię", 3);
        predicted[1] = t.transform("Młode źrebię", 3);
        predicted[2] = t.transform("źrebię-012", 3);

        Assert.assertArrayEquals(result, predicted);
    }
    @Test // change to delete space after
    public void repetitionTest() {
        t = new TextTransformation(t);
        String[] predicted = new String[3];
        String[] result =  {"Spacer do sklepu ", "Spacer do sklepu po bułki ", "Myślę że masz rację "};

        predicted[0] = t.transform("Spacer do do do do sklepu", 4);
        predicted[1] = t.transform("Spacer do do sklepu po po po bułki", 4);
        predicted[2] = t.transform("Myślę że że masz masz rację", 4);

        Assert.assertArrayEquals(result, predicted);
    }
}