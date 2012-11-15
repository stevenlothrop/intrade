package com.ssp.clubhouse.logging;

import com.ssp.clubhouse.util.DateProvider;
import org.jetlang.core.Callback;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;

/**
 * User: stevenlothrop
 * Date: 11/14/12
 * Time: 10:17 PM
 */
public class Logger<T> {
    private final PrintWriter printWriter;
    private DateProvider dateProvider;

    public Logger(DateProvider dateProvider, File file, boolean append) throws IOException {
        this.dateProvider = dateProvider;
        printWriter = new PrintWriter(new FileWriter(file), append);
        writeHeader();
    }

    public void writeHeader() {
        printWriter.write("TIMESTAMP,DATA");
        printWriter.write(System.lineSeparator());
    }


    public Callback<T> log() {
        return new Callback<T>() {
            @Override
            public void onMessage(T message) {
                printWriter.print(new SimpleDateFormat("yyyy_MM_dd_HH:mm:ss.SSS,").format(dateProvider.getDate()));
                if (message instanceof Jsonable) {
                    printWriter.println(((Jsonable) message).toJson());
                } else {
                    printWriter.println(message.toString());
                }
            }
        };
    }

}
