package Hw;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<Department> departments = new ArrayList<>(List.of(
                new Department("1"),
                new Department("2"),
                new Department("3"),
                new Department("4"),
                new Department("5")
        ));
        List<Person> people = new ArrayList<>(List.of(
                new Person("Bob", 25, 70000.0, departments.get(ThreadLocalRandom.current().nextInt(departments.size()))),
                new Person("Lily", 21, 60000.0, departments.get(ThreadLocalRandom.current().nextInt(departments.size()))),
                new Person("Ann", 24, 100000.0, departments.get(ThreadLocalRandom.current().nextInt(departments.size()))),
                new Person("Carl", 31, 20000.0, departments.get(ThreadLocalRandom.current().nextInt(departments.size()))),
                new Person("Daniel", 24, 65000.0, departments.get(ThreadLocalRandom.current().nextInt(departments.size()))),
                new Person("Elza", 27, 150000.0, departments.get(ThreadLocalRandom.current().nextInt(departments.size()))),
                new Person("Josh", 21, 51000.0, departments.get(ThreadLocalRandom.current().nextInt(departments.size()))),
                new Person("Mary", 25, 92000.0, departments.get(ThreadLocalRandom.current().nextInt(departments.size()))),
                new Person("Dylon", 28, 77000.0, departments.get(ThreadLocalRandom.current().nextInt(departments.size()))),
                new Person("Troy", 19, 51000.0, departments.get(ThreadLocalRandom.current().nextInt(departments.size()))),
                new Person("Del", 21, 250000.0, departments.get(ThreadLocalRandom.current().nextInt(departments.size()))),
                new Person("Julia", 17, 160000.0, departments.get(ThreadLocalRandom.current().nextInt(departments.size()))),
                new Person("Tracy", 18, 71000.0, departments.get(ThreadLocalRandom.current().nextInt(departments.size())))
        ));

        people.forEach(System.out::println);
        System.out.println("-".repeat(30));
        sortCrewByName(people);
        System.out.println("-".repeat(30));
        getOldestEmployeePerDepartment(people);
        System.out.println("-".repeat(30));
        sortByAgeAndSalary(people);
        System.out.println("-".repeat(30));
        sortBySumOfSalary(people);
    }


    //Вывести на консоль отсортированные (по алфавиту) имена персонов
    static void sortCrewByName(List<Person> people){
        people.stream()
                .sorted(Comparator.comparing(Person::getName))
                .forEach(System.out::println);
    }


//   В каждом департаменте найти самого взрослого сотрудника.
//   * Вывести на консоль мапипнг department -> personName
//   * Map<Department, Person>
    static void getOldestEmployeePerDepartment(List<Person> people){
        Comparator<Person> ageComparator = Comparator.comparing(Person::getAge);
        Map<Department, Person> maxSalaryPerDepartment = people.stream()
                .collect(Collectors.toMap(
                        Person::getDepartment,
                        Function.identity(),
                        (first, second) -> {
                            if(ageComparator.compare(first, second) > 0) {
                            return first;
                        }
                        return second;
                }));

        maxSalaryPerDepartment.forEach((k, v) -> System.out.println(k + ": " + v));



    }


    //Найти 10 первых сотрудников, младше 30 лет, у которых зарплата выше 50_000
    static void sortByAgeAndSalary(List<Person> people){
        people.stream()
                .filter(person -> person.getAge() < 30)
                .filter(person -> person.getSalary() > 50000.0)
                .limit(10)
                .forEach(System.out::println);
    }


    //Найти депаратмент, чья суммарная зарплата всех сотрудников максимальна
    static void sortBySumOfSalary(List<Person> people){

        Map<Department, List<Person>> groupByDep = people.stream()
                .collect(Collectors.groupingBy(Person::getDepartment));


        Map<Department, Double> sumSalary = groupByDep.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> e.getValue().stream()
                                .map(Person::getSalary)
                                .reduce(0.0, Double::sum)
                ));

        sumSalary.forEach((k, v) -> System.out.println(k + ": " + v));

        Optional<Map.Entry<Department, Double>> maxSalary = sumSalary.entrySet().stream()
                .max(Map.Entry.comparingByValue());

        System.out.println("Департамент с максимальной зп: " + maxSalary);

    }


}




