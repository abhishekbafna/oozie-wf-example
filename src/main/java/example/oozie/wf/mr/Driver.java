package example.oozie.wf.mr;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class Driver implements Tool {

    Configuration configuration = new Configuration();

    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            System.out.println("incorrect arguments");
            return;
        }
        ToolRunner.run(new Driver(), args);
    }

    public int run(String[] strings) throws Exception {
        Configuration configuration = this.getConf();
        Job job = new Job(configuration, "MR Job");
        job.setJarByClass(Driver.class);
        job.setMapperClass(CustomMapper.class);

        job.setNumReduceTasks(0);

        FileInputFormat.addInputPath(job, new Path(strings[0]));
        job.setInputFormatClass(TextInputFormat.class);

        FileOutputFormat.setOutputPath(job, new Path(strings[1]));
        job.setOutputFormatClass(TextOutputFormat.class);
        return job.waitForCompletion(true) ? 0 : 1;
    }

    public void setConf(Configuration configuration) {
        this.configuration = configuration;
    }

    public Configuration getConf() {
        return configuration;
    }
}
