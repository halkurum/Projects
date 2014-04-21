package db_A3_2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


public class Interest 
{    
	static java.util.Hashtable<String,String> locationTable=new java.util.Hashtable<String,String>();
    public static class MyInterestMapper extends
            Mapper<Object, Text, Text,Text > 
    {

        public void map(Object key, Text value, Context context)
                throws IOException, InterruptedException 
        {              
        	try
        	{
             	 String interest=value.toString().split(",")[2].trim();
             	 String x=value.toString().split(",")[3].trim();
             	 String y=value.toString().split(",")[4].trim();
             	 String userID=value.toString().split(",")[0];
             	 
             	 String iLoc=locationTable.get(interest);
             	 
             	 if(iLoc!=null)
             	 {
             		 String iX=iLoc.split(",")[0];
             		 String iY=iLoc.split(",")[1];
             		 
             		 Double xCor=Math.pow((Double.parseDouble(iX)-Double.parseDouble(x)),2);
             		 Double yCor=Math.pow(Double.parseDouble(iY)-Double.parseDouble(y),2);
             		 
             		 double d=Math.sqrt(xCor+yCor);
             		 
             		 if(d<=5)
             		 {
             			context.write(new Text(interest),new Text(userID)); 
             		 }
             	 }
             	 else
             	 {
             		 System.out.println("Couldn't find the interest-"+interest+"in the hashtable.");
             		 return;
             	 }
             	 
        	}
        	catch(Exception ex)
        	{
        		System.out.println("Exception in Mapper-"+ex.getMessage());
        	}
        }
    }

    public static class MyInterestReducer extends
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
        		
        		context.write(key,new Text(count.toString()));
        	}
        	catch(Exception ex)
        	{
        		System.out.println("Exception in Reducer-"+ex.getMessage());
        	}
        }
    }

    public static void getInterestFileData() throws IOException
    {
    	FileInputStream file = null;
    	BufferedReader reader = null;
    	try
    	{
	    	file = new FileInputStream(new File("E:\\Interest.txt"));
	    	reader = new BufferedReader(new InputStreamReader(file));
	    	String line=null;
	    	String[] splitString;
	    	String x=new String();
	    	String y=new String();
	    	String[] xSplit;
	    	String[] ySplit;
	    	while((line=reader.readLine())!=null)
	    	{
	    		splitString=line.split("\\(");
	    		xSplit=splitString[1].split(",");
	    		x=xSplit[0];
	    		ySplit=xSplit[1].split("\\)");
	    		y=ySplit[0].trim();
	    		locationTable.put(splitString[0].trim(), x.trim() + "," + y.trim());
	    	}
    	}
    	catch(Exception ex)
    	{
    		ex.printStackTrace();
    	}
    	finally
    	{
    		file.close();
    		reader.close();
    	}
    }
    public static void main(String[] args)
    {
    	try
    	{
	    	getInterestFileData();

	        Configuration conf = new Configuration();       
	        Job job = new Job(conf, "MyInterestCount");
	        job.setJarByClass(Interest.class);
	        job.setMapperClass(MyInterestMapper.class);
	        job.setReducerClass(MyInterestReducer.class);
	        
	        job.setOutputKeyClass(Text.class);
	        job.setOutputValueClass(Text.class);
	        
	        FileInputFormat.addInputPath(job, new Path(args[0]));
	        FileOutputFormat.setOutputPath(job, new Path(args[1]));
	        System.exit(job.waitForCompletion(true) ? 0 : 1);
    	}
    	catch(Exception ex)
    	{
    		ex.printStackTrace();
    	}
    }
}