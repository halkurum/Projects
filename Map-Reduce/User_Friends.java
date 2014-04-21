package dB_A3_1;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class One 
{
    
    public static class TokenCounterMapper extends
            Mapper<Object, Text, Text,Text > 
    {
        public void map(Object key, Text value, Context context)
                throws IOException, InterruptedException 
        {              
        	try
        	{
            
        	String mapKey = value.toString().split(",")[0];
        	String mapValue = value.toString().split(",")[1];
        	context.write(new Text(mapKey),new Text(mapValue));
        	context.write(new Text(mapValue),new Text(mapKey));
        	}
        	catch(Exception ex)
        	{
        		System.out.println("Exception in Mapper-"+ex.getMessage());
        	}
        }
    }

    public static class TokenCounterReducer extends
            Reducer<Text, Text, Text, Text> 
    {
        public void reduce(Text key,Iterable<Text> values,
                Context context) throws IOException, InterruptedException
        {
        	try
        	{
            
        	Integer count=0;
        	        	
        	for(@SuppressWarnings("unused") Text text:values)
        	{
        		count++;
        	}
        	context.write(new Text(key.toString()),new Text(count.toString())); 
        	}
        	catch(Exception ex)
        	{
        		System.out.println("Exception in Reducer-"+ex.getMessage());
        	}
        }
    }
    
    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
    
        Job job = new Job(conf, "MyUserCount");
        
        job.setJarByClass(One.class);
        job.setMapperClass(TokenCounterMapper.class);
        job.setReducerClass(TokenCounterReducer.class);
        
        job.setNumReduceTasks(2);
        
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
        
        FileInputFormat.addInputPath(job, new Path(args[0]));
	    FileOutputFormat.setOutputPath(job, new Path(args[1]));
        
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
