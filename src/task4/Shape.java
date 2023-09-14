package task4;

public interface Shape extends Comparable {
    double calculateArea();

    @Override
    default int compareTo(Object o) {
        return (int)(this.calculateArea() - ((Shape)o).calculateArea());
    }
}
