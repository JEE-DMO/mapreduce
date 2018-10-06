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

public class AddressGeocoder {

    public static class Mapper extends org.apache.hadoop.mapreduce.Mapper<LongWritable, Text, Text, Text> {

        private Text geoCodeText = new Text();
        private Text addressText = new Text();

        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

            geoCodeText.set(GeoUtil.geoCode(value.toString()));
            addressText.set(value);
            context.write(geoCodeText, addressText);

        }


    }

    public static class Combiner extends org.apache.hadoop.mapreduce.Reducer<Text, Text, Text, Text> {

        protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            Set<Text> uniques = new HashSet<Text>();
            for (Text value : values) {
                if (uniques.add(value)) {
                    context.write(key, value);
                }
            }
        }
    }

    public static class Reducer extends org.apache.hadoop.mapreduce.Reducer<Text, Text, IntWritable, Text> {

        private IntWritable count = new IntWritable();
        private Text outputValue = new Text();

        protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            Set<Text> uniques = new HashSet<Text>();
            int size = 0;
            StringBuilder builder = new StringBuilder();
            builder.append(key + " -> ");
            for (Text value : values) {
                if (uniques.add(value)) {
                    size++;
                    builder.append(value.toString());
                    builder.append(',');
                }
            }
            builder.setLength(builder.length() - 1);

            if (size > 1) {
                count.set(size);
                outputValue.set(builder.toString());
                context.write(count, outputValue);
            }
        }

    }

    public static void main(String[] args) throws InterruptedException, IOException, ClassNotFoundException {

        Configuration conf = new Configuration();
        Job job = new Job(conf, "Address Geocoder");

        job.setJarByClass(AddressGeocoder.class);


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