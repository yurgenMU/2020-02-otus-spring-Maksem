package ru.otus.spring.io;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class IOServiceImpl implements IOService {

    private final PrintStream output;
    private final Scanner scanner;

    public IOServiceImpl(InputStream input, PrintStream output) {
        this.output = output;
        this.scanner = new Scanner(input);
    }

    @Override
    public String read() {
        return scanner.nextLine();
    }

    @Override
    public void write(Object arg) {
        output.println(arg);
    }

    @Override
    public void writeFormatted(String format, Object... args) {
        System.out.printf(format, args);
    }
}
