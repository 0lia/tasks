package task4;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SortableShapes {

    public static void main(String[] args) {
        int side = 3;
        int radius = 1;
        int base = 5;
        int height = 2;
        List<Shape> shapes = new ArrayList<>();

        shapes.add(new Circle(radius)); //3.14
        shapes.add(new Rectangle(base, height));   //10
        shapes.add(new Square(side));  //9
        shapes.add(new Triangle(base, height)); //5

        System.out.println("Unsorted shapes - " + shapes);

        shapes = shapes.stream().sorted().collect(Collectors.toList());

        System.out.println("Sorted shapes - " + shapes);
    }
}
