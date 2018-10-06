import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.logging.Logger;

public class AnagramFinder {

    public static class Mapper extends org.apache.hadoop.mapreduce.Mapper<LongWritable, Text, Text, Text> {

        private Text sortedText = new Text();
        private Text outputValue = new Text();
        private Logger logger = Logger.getLogger("Mapper");

        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            StringTokenizer tokenizer = new StringTokenizer(value.toString(),
                    " \t\n\r\f,.:()!?", false);

            logger.info("Value " + value.toString());

            while (tokenizer.hasMoreTokens()) {
                //todo
            }
        }



    }


    public static class Reducer extends org.apache.hadoop.mapreduce.Reducer<Text, Text, IntWritable, Text> {

        private IntWritable count = new IntWritable();
        private Text outputValue = new Text();

        protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            //TODO
        }

    }

    public static void main(String[] args) throws InterruptedException, IOException, ClassNotFoundException {

        Configuration conf = new Configuration();
        Job job = new Job(conf, "Anagram Finder");

        job.setJarByClass(AnagramFinder.class);


        org.apache.hadoop.mapreduce.lib.input.FileInputFormat.addInputPath(job, new Path(args[0]));
        org.apache.hadoop.mapreduce.lib.output.FileOutputFormat.setOutputPath(job, new Path(args[1]));


        job.setMapperClass(Mapper.class);
        // job.setCombinerClass(Reducer.class);
        job.setReducerClass(Reducer.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);

        System.exit(job.waitForCompletion(true) ? 0 : 1);

    }

}