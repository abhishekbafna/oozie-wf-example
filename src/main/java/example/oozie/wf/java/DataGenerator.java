package example.oozie.wf.java;

import java.io.*;
import java.net.URI;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.util.Progressable;

import java.net.URISyntaxException;
import java.util.Random;

public class DataGenerator {

    private Random random = new Random(System.currentTimeMillis());

    public static void main(String[] args) throws IOException, URISyntaxException {

        if (args.length != 3) {
            System.out.println("arguments are missing.");
            System.exit(1);
        }

        int recordCount = Integer.parseInt(args[0]);
        String nameNode = args[1];
        String outputFile = args[2];
        int id = 1;
        char separator = ',';

        DataGenerator dataGenerator = new DataGenerator();
        StringBuilder builder = new StringBuilder();

        FileSystem hdfs = FileSystem.get( new URI( nameNode ), new Configuration() );
        Path file = new Path(outputFile);
        if ( hdfs.exists( file )) { hdfs.delete( file, true ); }
        OutputStream os = hdfs.create( file,
                new Progressable() {
                    public void progress() {
                    } });
        BufferedWriter writer = new BufferedWriter( new OutputStreamWriter( os, "UTF-8" ) );

        while (id<=recordCount) {
            builder.append(id); // id
            builder.append(separator);
            builder.append(dataGenerator.getName()); // name
            builder.append(separator);
            builder.append(dataGenerator.getGender()); // gender
            builder.append(separator);
            builder.append(dataGenerator.getAge()); // age
            builder.append(separator);
            builder.append(dataGenerator.getMarks()); // subject-1
            builder.append(separator);
            builder.append(dataGenerator.getMarks()); // subject-2
            builder.append(separator);
            builder.append(dataGenerator.getMarks()); // subject-3
            writer.write(builder.toString());
            writer.newLine();
            id++;
            builder.setLength(0);
        }
        writer.flush();
        writer.close();
        os.close();
        hdfs.close();
    }

    private enum Gender {
        MALE, FEMALE
    }

    private String getName() {
        StringBuilder name = new StringBuilder();
        while (name.length() < 8) {
            int num = random.nextInt(26);
            name.append((char)('a'+num));
        }
        return name.toString();
    }

    private String getGender() {
        return random.nextBoolean() ? Gender.MALE.toString() : Gender.FEMALE.toString();
    }

    private int getAge() {
        int age = 18;
        int offset = random.nextInt(7);
        return age + offset;
    }

    private int getMarks() {
        return random.nextInt(100);
    }
}
