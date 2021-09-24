package com.mypackage.testrenue;

import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TestRenue {
    private static int columnNumbers;

    public static void main(String[] args) throws IOException {
        System.out.println("Запускается...");

        if (getColumnNumber(args)) return; //получение номера столбца из аргумента запуска или из файла,
        // а так же проверка, получен ли номер столбца

        HashMap<String,String> data = readFile(); //преобраззование BufferedReader в HashMap c ключем по полученному столбцу

        while (true) {
            //получение строки для поиска из консоли
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Введите строку: ");
            String searchString = bufferedReader.readLine();

            if (searchString.equals("exit"))
                break;

            long start = System.currentTimeMillis();
            ArrayList<String> arrayList = searchStrings(searchString, data); //поиск строк, начинающихся с searchString
            long finish = System.currentTimeMillis();

            //вывод найденных строк
            for (String s : arrayList) {
                System.out.println(s);
            }

            System.out.println("Количество найденных строк: " + arrayList.size() + ". Время, затраченное на поиск: " +
                    (finish - start) + " мс.");

            MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
            System.out.println("Использовано " + memoryMXBean.getHeapMemoryUsage().getUsed() / 1048576 +
                    " МБ хипа для работы приложения.");
        }

    }

    private static ArrayList<String> searchStrings(String searchString, HashMap<String, String> data) {
        ArrayList<String> arrayList = new ArrayList<>();
        data
                .entrySet()
                .stream()
                .filter(e -> e.getKey().startsWith(searchString))
                .sorted(Map.Entry.comparingByKey())
                .forEach(e -> arrayList.add(e.getKey() + ", " + e.getValue()));
        return arrayList;
    }

    private static boolean getColumnNumber(String[] args) {
        if (args.length == 1 && args[0].matches(".*\\d+.*")) {
            columnNumbers = Integer.parseInt(args[0]);
            System.out.printf("Колонка для поиска: %s\n%n", columnNumbers);
        } else {
            Yaml yaml = new Yaml();
            try (InputStream in = Files.newInputStream(Paths.get("classes/application.yml"))) {
                Configuration configuration = yaml.loadAs(in, Configuration.class);
                columnNumbers = configuration.getColumnNumbers();
                System.out.println(configuration);
            } catch (IOException e) {
                System.out.println("Для выбора столбца используйте настройки в файле <classes/application.yml> или " +
                        "добавьте аргумент при запуске приложения");
                return true;
            }
        }
        return false;
    }

    private static HashMap<String, String> readFile () throws IOException {
        HashMap<String, String> result = new HashMap<>();
        BufferedReader bufferedReader = getDataFile(); //получение BufferedReader из файла с данными
        String line;
        StringBuilder stringBuilder;
        String[] values;
        while ((line = bufferedReader.readLine()) != null) {
            values = line.replace("\"","").split(",");
                stringBuilder = new StringBuilder();
                for (int i = 0; i < values.length; i++) {
                    if (i != columnNumbers - 1) {
                        if (i != values.length - 1)
                            stringBuilder.append(values[i]).append(", ");
                        else
                            stringBuilder.append(values[i]);
                    }
                }
                result.put(values[columnNumbers - 1], stringBuilder.toString());
        }
        bufferedReader.close();
        return result;
    }

    private static BufferedReader getDataFile() throws FileNotFoundException {
        BufferedReader bufferedReader;
        try {
            bufferedReader = new BufferedReader(new FileReader("src/main/resources/airports.dat"));
        } catch (IOException e) {
            bufferedReader = new BufferedReader(new FileReader("classes/airports.dat"));
        }
        return bufferedReader;
    }
}