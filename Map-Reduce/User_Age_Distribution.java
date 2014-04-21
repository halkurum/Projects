package DB_A3_MR;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class Age_MR 
{	
    public static class MyAgeMapper extends
            Mapper<Object, Text, Text,Text > 
    {

        public void map(Object key, Text value, Context context)
                throws IOException, InterruptedException 
        {              
        	try
        	{
                String age=value.toString().split(",")[1].trim();
                String userID=value.toString().split(",")[0].trim();
                
                Text toWrite = null;
                
                if(age==null)
                {
                	System.out.println("Age from vaoue "+ value.toString() + "not found");
                	return;
                }
                
                Integer age_i = Integer.parseInt(age);
                
                if(age_i >= 5 && age_i <= 14)
                {
                	toWrite = new Text("one");
                }
                else if(age_i >=15 && age_i <=24)
                {
                	toWrite = new Text("two");
                }
                else if(age_i >=25&& age_i <=34)
                {
                	toWrite = new Text("three");
                }
                else if(age_i >=35 && age_i <= 44)
                {
                	toWrite = new Text("four");
                }
                else if(age_i >= 45 && age_i <= 54)
                {
                	toWrite = new Text("five");
                }
                else
                {
                	toWrite = new Text("six");
                }
                context.write(toWrite, new Text(userID));
        	}
        	catch(Exception ex)
        	{
        		System.out.println("Exception in Mapper-"+ex.getMessage());
        	}
        }
    }

    public static class MyAgeReducer extends
            Reducer<Text, Text, Text, Text> 
    {
        public void reduce(Text key,Iterable<Text> values,
                Context context) throws IOException, InterruptedException
        {
        	try
        	{
        		Integer count=0;
        		String ageRange=new String();
        		if(key.toString().equals("one"))
        			ageRange="(a):5-14";
        		else if(key.toString().equals("two"))
        			ageRange="(b):15-24";
        		else if(key.toString().equals("three"))
        			ageRange="(c):25-34";
        		else if(key.toString().equals("four"))
        			ageRange="(d):35-44";
        		else if(key.toString().equals("five"))
        			ageRange="(e):45-54";
        		else if(key.toString().equals("six"))
        			ageRange="(f):55+";
        		
        		for(@SuppressWarnings("unused") Text text:values)
        		{
        			count++;
        		}
        		
        		context.write(new Text(ageRange),new Text(count.toString()));
        	}
        	catch(Exception ex)
        	{
        		System.out.println("Exception in Reducer-"+ex.getMessage());
        	}
        }
    }

    public static void main(String[] args) 
    {
    	try
    	{
		    Configuration conf = new Configuration();       
		    Job myAgeJob = new Job(conf, "MyAgeCount");
		    
		    myAgeJob.setJarByClass(Age_MR.class);
		    myAgeJob.setMapperClass(MyAgeMapper.class);
		    myAgeJob.setReducerClass(MyAgeReducer.class);
		    
		    myAgeJob.setOutputKeyClass(Text.class);
		    myAgeJob.setOutputValueClass(Text.class);
		    
		    FileInputFormat.addInputPath(myAgeJob, new Path(args[0]));
		    FileOutputFormat.setOutputPath(myAgeJob, new Path(args[1]));
		   
		    System.exit(myAgeJob.waitForCompletion(true) ? 0 : 1);
    	}
    	catch(Exception ex)
    	{
    		ex.printStackTrace();
    	}
    }
}