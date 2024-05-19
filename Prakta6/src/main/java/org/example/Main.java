package org.example;

import java.io.IOException;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Main {
    public static Scanner scanner = new Scanner(System.in);
    public static class Employee {
        private static final Logger LOGGER = Logger.getLogger(Employee.class.getName());
        private String name;
        private int age;
        private int experience;

        public Employee(String name, int age, int experience) {
            this.name = name;
            this.age = age;
            this.experience = experience;
            try {
                FileHandler fileHandler = new FileHandler("employee.log", true);
                fileHandler.setFormatter(new SimpleFormatter());
                LOGGER.addHandler(fileHandler);
            } catch (IOException e) {
                LOGGER.severe("Ошибка при создании файла лога employee.log");
            }
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }

        public int getExperience() {
            return experience;
        }

        public boolean isAdult() {
            LOGGER.info("Проверка совершеннолетия для сотрудника " + name);
            return age >= 18;
        }

        public boolean isQuiteExperienced(int requiredExperience) {
            LOGGER.info("Проверка опыта для сотрудника " + name + " требуется " + requiredExperience);
            return experience >= requiredExperience;
        }

        public int calculateSalary(int selectedIndex) {
            int salary = 0;
            switch (selectedIndex) {
                case 1:
                    salary = experience > 7 ? 20000 : 15000;
                    break;
                case 2:
                    salary = experience > 10 ? 45000 : 35000;
                    break;
                case 3:
                    salary = experience > 12 ? 60000 : 55000;
                    break;
                default:
                    LOGGER.warning("Неверный индекс выбранной работы " + selectedIndex);
            }
            LOGGER.info("Расчет зарплаты для сотрудника " + name + " выбранная работа " + selectedIndex + " зарплата " + salary);
            return salary;
        }

        public String getPosition(int selectIndex) {
            String position = "";
            switch (selectIndex) {
                case 1:
                    position = "Токарь";
                    break;
                case 2:
                    position = "Фрезерщик";
                    break;
                case 3:
                    position = "Сверльщик";
                    break;
                default:
                    LOGGER.warning("Неверный индекс выбранной работы " + selectIndex);
            }
            LOGGER.info("Получение должности для сотрудника " + name + " выбранная работа " + selectIndex + " должность " + position);
            return position;
        }

        public void printUpdatedInfo(int salary, String post) {
            System.out.println("\nОбновление в анкете:");
            System.out.println("Зарплата: " + salary);
            System.out.println("Должность: " + post);
            LOGGER.info("Вывод обновленной информации для сотрудника " + name);
        }
    }

    public static class Factory {
        private static final Logger LOGGER = Logger.getLogger(Factory.class.getName());

        public static void selectEmployment(Employee employee) {
            LOGGER.info("Выбор работы для сотрудника " + employee.getName());
            String[] employments = {"Токарный станок", "Фрезерный станок", "Сверлильный станок"};
            System.out.println("Выберите индекс желаемой работы:");
            for (int i = 0; i < employments.length; i++) {
                System.out.println((i + 1) + ": " + employments[i]);
            }
            int selectIndex = scanner.nextInt();
            if (selectIndex >= 1 && selectIndex <= 3) {
                if (employee.isQuiteExperienced(selectIndex == 1 ? 5 : (selectIndex == 2 ? 7 : 10)))

                {
                    System.out.println("Вы подходите на данную должность!");
                    int salary = employee.calculateSalary(selectIndex);
                    String post = employee.getPosition(selectIndex);
                    employee.printUpdatedInfo(salary, post);
                    LOGGER.info("Успешный выбор работы для сотрудника " + employee.getName() + " выбранная работа " + selectIndex);
                } else {
                    System.out.println("Сожалеем, но вы не проходите из-за маленького опыта работы");
                    LOGGER.info("Неудачный выбор работы для сотрудника " + employee.getName() + " выбранная работа " + selectIndex + " недостаточный опыт");
                }
            } else {
                System.out.println("Некорректный выбор!");
                LOGGER.warning("Неверный индекс выбранной работы " + selectIndex);
            }
        }
    }

    public static void main(String[] args) {
        try {
            FileHandler fileHandler = new FileHandler("factory.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            Logger.getLogger(Factory.class.getName()).addHandler(fileHandler);
        } catch (IOException e) {
            Logger.getLogger(Factory.class.getName()).severe("Ошибка при создании файла лога factory.log");
        }

        System.out.println("Введите имя: ");
        String name = scanner.next();
        System.out.println("Введите возраст: ");
        int age = scanner.nextInt();
        System.out.println("Введите опыт работы: ");
        int experience = scanner.nextInt();

        Employee employee = new Employee(name, age, experience);

        System.out.println("\nАнкета персонала:");
        System.out.println("Ваше имя: " + employee.getName());
        System.out.println("Ваш возраст: " + employee.getAge());
        System.out.println("Ваш опыт работы: " + employee.getExperience() + " лет");

        if (employee.isAdult()) {
            Factory.selectEmployment(employee);
        } else {
            System.out.println("Сожалеем, но несовершеннолетние не могут работать на данном заводе");
            Logger.getLogger(Factory.class.getName()).info("Неудачный выбор работы для сотрудника " + employee.getName() + " несовершеннолетний");
        }
    }
}
