import java.io.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.net.URL;
import java.net.URLConnection;
import org.json.XML;
import org.json.JSONObject;
import org.json.JSONException;
import java.net.URLEncoder;
import java.net.URLDecoder;

public class WeatherSearch extends HttpServlet 
{

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
        throws IOException, ServletException
    {
		
        PrintWriter out = response.getWriter();
		String location = URLDecoder.decode(request.getParameter("location"));
		String type = URLDecoder.decode(request.getParameter("type"));
		String unit = URLDecoder.decode(request.getParameter("unit"));
		String awsURL = "http://default-environment-ii9naedwp8.elasticbeanstalk.com/?location=" + URLEncoder.encode(location) + "&type=" + type + "&unit=" + unit;
		//out.write(awsURL);
		String myurl = URLEncoder.encode(awsURL);
		//out.write("\n"+myurl);
		URL url = new URL(awsURL);
		URLConnection urlConnection = url.openConnection();

		InputStream awsResponse = urlConnection.getInputStream();
		InputStreamReader iSReader = new InputStreamReader(awsResponse);
		StringBuffer sb = new StringBuffer();
		String xmlLine; 
		BufferedReader buf = new BufferedReader(iSReader);
		try 
		{	
			while ((xmlLine = buf.readLine()) != null) 
			{
				sb.append(xmlLine);
			}
		}
		catch (IOException ex) 
		{
			ex.printStackTrace();
		} 
		xmlLine = sb.toString();
		
		response.setContentType("application/json; charset=UTF-8");
		int indent = 6;
		try
		{
			JSONObject jsonObj = XML.toJSONObject(xmlLine);
			String json1 = jsonObj.toString(indent);
			out.write(json1);
		}
		catch(JSONException ex)
		{
			ex.printStackTrace();
		}
    }
}
