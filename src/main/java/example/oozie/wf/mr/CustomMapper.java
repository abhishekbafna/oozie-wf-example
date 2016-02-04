package example.oozie.wf.mr;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class CustomMapper extends Mapper<LongWritable, Text, Text, DoubleWritable> {

    private Text maxScoreRecord = null;
    private double maxScore = 0.0;


    @Override
    public void map(LongWritable key, Text value, Context context) {
        String values[] = value.toString().split(",");
        int len = values.length;
        int sum = Integer.valueOf(values[len-1]) + Integer.valueOf(values[len-2]) + Integer.valueOf(values[len-3]);
        double avg = sum/3;
        if (avg > maxScore) {
            maxScore = avg;
            maxScoreRecord = new Text(value);
        }
    }

    @Override
    public void cleanup(Context context) throws IOException, InterruptedException {
        context.write(maxScoreRecord, new DoubleWritable(maxScore));
    }
}
