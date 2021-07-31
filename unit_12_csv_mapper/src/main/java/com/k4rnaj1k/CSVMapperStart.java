package com.k4rnaj1k;

import java.util.Scanner;

public class CSVMapperStart {
    public static void main(String[] args) {
        CSVParser parser;
        if (args.length == 1) {
            parser = new CSVParser(args[0]);
        } else {
            Scanner s = new Scanner(System.in);
            parser = new CSVParser(s.nextLine());
        }
        parser.parse();
        CSVMapper<Person> mapper = new CSVMapper<>();
        mapper.getMapped(Person.class, parser).forEach(System.out::println);
        System.out.println(parser.get(1, Person.Fields.ID.getFieldsName()));
        System.out.println(parser.get(1, Person.Fields.NAME.getFieldsName()));
    }
}
