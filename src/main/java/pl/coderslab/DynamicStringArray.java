package pl.coderslab;

import java.util.Arrays;

public class DynamicStringArray {
    String[] array = new String[0];

    public void add(String elements) {
        array = Arrays.copyOf(array, array.length + 1);
        array[array.length - 1] = elements;


    }

}
