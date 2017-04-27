package happiness;

import java.awt.image.RenderedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

/**
 * Servlet implementation class LineChart1
 */
@WebServlet("/LineChart1")
public class LineChart1 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	int vhappy,qhappy,nhappy,nahappy,vhappy1;
	String prere=null;
	String predate=null;
	String prere2=null;
	String predate2=null;
	String prere3=null;
	String predate3=null;
	String prere4=null;
	String predate4=null;

	/**
	 * @see HttpServlet#HttpServlet()
	 */

	public LineChart1() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		ArrayList<String> dateres1=new ArrayList<String>();
		ArrayList<String> dateres2=new ArrayList<String>();
		ArrayList<String> dateres3=new ArrayList<String>();
		ArrayList<String> dateres4=new ArrayList<String>();
		ArrayList<String> res1 = new ArrayList<String>();
		ArrayList<String> res2 = new ArrayList<String>();
		ArrayList<String> res3 = new ArrayList<String>();
		ArrayList<String> res4 = new ArrayList<String>();
		String sst1 = new String();
		sst1=null;
		String sst2 = new String();
		sst2=null;
		String sst3 = new String();
		sst2=null;
		String sst4 = new String();
		sst2=null;
		Report1 rs=new Report1();
		ArrayList<String> res=new ArrayList<String>();
		res.clear();
		//getting arraylist of response and date from Report1
		res=rs.getdates();
		HttpSession session = request.getSession();
		//getting total row count
		int totalrowCount = (Integer)session.getAttribute("totalrowcount");

		for (int i = 0; i < res.size(); i++) {
			StringTokenizer st = new StringTokenizer(res.get(i).toString(),"[/,");
			String date = null; String status = null;
			int veryhappycount=0, quitehappycount=0,  nothappycount=0,  verysadcount=0;
			String st1=new String();
			String st2=new String();
			String st3=new String();
			String st4=new String();
			while(st.hasMoreTokens()){
				date=st.nextToken();
				status=st.nextToken();

				//calculating percentage where status is very happy and adding to arraylist
				if(status.equals("veryhappy")){
					veryhappycount++;
					vhappy=(veryhappycount*100/totalrowCount);
					st1=vhappy+"/"+date;
					dateres1.add(st1); 
				}
				//calculating percentage where status is quite happy and adding to arraylist
				else if(status.equals("quitehappy")){
					quitehappycount++;
					qhappy=(quitehappycount*100/totalrowCount);
					st2=qhappy+"/"+date;
					dateres2.add(st2);

				}
				//calculating percentage where status is not happy and adding to arraylist
				else if(status.equals("nothappy")){
					nothappycount++;
					nhappy=(nothappycount*100/totalrowCount);
					st3=nhappy+"/"+date;
					dateres3.add(st3);
				}
				//calculating percentage where status is not at all happy and adding to arraylist
				else{
					verysadcount++;
					nahappy=(verysadcount*100/totalrowCount); 
					st4=nahappy+"/"+date;
					dateres4.add(st4);
				}
			}
		}

		System.out.println(dateres1);
		System.out.println(dateres2);
		System.out.println(dateres3);
		System.out.println(dateres4);
		response.setContentType("image/png");
		ServletOutputStream os = response.getOutputStream();
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		//DefaultPieDataset dataset = new DefaultPieDataset( );
		String series1 = "Very Happy";
		String series2 = "Quite Happy";
		String series3 = "Not Happy";
		String series4 = "Not at all Happy";

		//combining per day percentage for very happy status
		for(int v=0;v<=dateres1.size()-1;v++)
		{
			StringTokenizer string1 = new StringTokenizer(dateres1.get(v).toString(), "[/,");
			StringTokenizer string2 =new StringTokenizer(dateres1.get(v).toString(),"[/,");
			while (string2.hasMoreTokens() ) {
				String result1 = null;
				String vhappydate = null;
				result1=string2.nextToken();
				vhappydate=string2.nextToken();
				if(prere==null)
				{
					prere=string1.nextToken();
					predate=string1.nextToken();
					sst1= prere+"/"+predate;
					res1.add(sst1);

				}
				else if(predate.equals(vhappydate))
				{
					int vhapy = Integer.parseInt(result1) + Integer.parseInt(prere);
					sst1 = vhapy + "/" + vhappydate;
					System.out.println(sst1);
					res1.remove(res1.size()-1);
					res1.add(sst1);
					Integer i=new Integer(vhapy);
					prere=i.toString();
					predate=vhappydate;
				}
				else
				{
					sst1=result1 +"/"+vhappydate;
					res1.add(sst1);
					prere=result1;
					predate=vhappydate;
				}
			}
		}
		System.out.println("res1--"+res1);
		//giving input to line chart for very happy status
		if(res1 != null && !res1.isEmpty())
		{
			for(int i = 0; i < res1.size(); i++)
			{
				int result1 = Integer.parseInt(new StringTokenizer(res1.get(i).toString(),"[/,").nextToken());
				String date1=res1.get(i).substring(res1.get(i).lastIndexOf("/") + 1);
				dataset.setValue(result1,series1,date1 );    
			}
		}

		//combining per day percentage for quite happy status
		for(int v=0;v<=dateres2.size()-1;v++)
		{
			StringTokenizer string1 = new StringTokenizer(dateres2.get(v).toString(), "[/,");
			StringTokenizer string2 =new StringTokenizer(dateres2.get(v).toString(),"[/,");

			while (string2.hasMoreTokens() ) {
				String result2 = null;
				String qhappydate = null;
				result2=string2.nextToken();
				qhappydate=string2.nextToken();
				if(prere2==null)
				{
					prere2=string1.nextToken();
					predate2=string1.nextToken();
					sst2= prere2+"/"+predate2;
					res2.add(sst2);

				}
				else if(predate2.equals(qhappydate))
				{
					int qhapy = Integer.parseInt(result2) + Integer.parseInt(prere2);

					sst2 = qhapy + "/" + qhappydate;
					System.out.println(sst2);
					res2.remove(res2.size()-1);
					res2.add(sst2);
					Integer i=new Integer(qhapy);
					prere2=i.toString();
					predate2=qhappydate;
				}
				else
				{
					sst2=result2 +"/"+qhappydate;
					res2.add(sst2);
					prere2=result2;
					predate2=qhappydate;
				}
			}
		}
		System.out.println("res2--"+res2);
		//giving input to line chart for quite happy status
		if(res2 != null && !res2.isEmpty())
		{
			for(int n = 0; n < res2.size(); n++)
			{
				int result2 = Integer.parseInt(new StringTokenizer(res2.get(n).toString(),"[/,").nextToken());
				String date2=res2.get(n).substring(res2.get(n).lastIndexOf("/") + 1);
				dataset.setValue(result2,series2,date2 );    
			}
		}

		//combining per day percentage for not happy status
		for(int v=0;v<=dateres3.size()-1;v++)
		{
			StringTokenizer string1 = new StringTokenizer(dateres3.get(v).toString(), "[/,");
			StringTokenizer string2 =new StringTokenizer(dateres3.get(v).toString(),"[/,");

			while (string2.hasMoreTokens() ) {
				String result3 = null;
				String nhappydate = null;
				result3=string2.nextToken();
				nhappydate=string2.nextToken();
				if(prere3==null)
				{
					prere3=string1.nextToken();
					predate3=string1.nextToken();
					sst3= prere3+"/"+predate3;
					res3.add(sst3);

				}
				else if(predate3.equals(nhappydate))
				{
					int nhapy = Integer.parseInt(result3) + Integer.parseInt(prere3);

					sst3 = nhapy + "/" + nhappydate;
					System.out.println(sst3);
					res3.remove(res3.size()-1);
					res3.add(sst3);
					Integer i=new Integer(nhapy);
					prere3=i.toString();
					predate3=nhappydate;
				}
				else
				{
					sst3=result3 +"/"+nhappydate;
					res3.add(sst3);
					prere3=result3;
					predate3=nhappydate;
				}
			}
		}
		System.out.println("res3--"+res3);

		//giving input to line chart for not happy status
		if(res3 != null && !res3.isEmpty())
		{
			for(int n = 0; n < res3.size(); n++)
			{
				int result3 = Integer.parseInt(new StringTokenizer(res3.get(n).toString(),"[/,").nextToken());
				String date3=res3.get(n).substring(res3.get(n).lastIndexOf("/") + 1);
				dataset.setValue(result3,series3,date3 );    
			}
		}

		//combining per day percentage for not at all happy status
		for(int v=0;v<=dateres4.size()-1;v++)
		{
			StringTokenizer string1 = new StringTokenizer(dateres4.get(v).toString(), "[/,");
			StringTokenizer string2 =new StringTokenizer(dateres4.get(v).toString(),"[/,");



			while (string2.hasMoreTokens() ) {
				String result4 = null;
				String nahappydate = null;
				result4=string2.nextToken();
				nahappydate=string2.nextToken();
				if(prere4==null)
				{
					prere4=string1.nextToken();
					predate4=string1.nextToken();
					sst4= prere4+"/"+predate4;
					res4.add(sst4);

				}
				else if(predate4.equals(nahappydate))
				{
					int nahapy = Integer.parseInt(result4) + Integer.parseInt(prere4);

					sst4 = nahapy + "/" + nahappydate;
					System.out.println(sst4);
					res4.remove(res4.size()-1);
					res4.add(sst4);
					Integer i=new Integer(nahapy);
					prere4=i.toString();
					predate4=nahappydate;
				}
				else
				{
					sst4=result4 +"/"+nahappydate;
					res4.add(sst4);
					prere4=result4;
					predate4=nahappydate;

				}
			}
		}
		System.out.println("res4--"+res4);
		//giving input to line chart for not at all happy status
		if(res4 != null && !res4.isEmpty())
		{
			for(int n = 0; n < res4.size(); n++)
			{
				int result4 = Integer.parseInt(new StringTokenizer(res4.get(n).toString(),"[/,").nextToken());
				String date4=res4.get(n).substring(res4.get(n).lastIndexOf("/") + 1);
				dataset.setValue(result4,series4,date4 );    
			}
		}

		JFreeChart chart = ChartFactory.createLineChart3D("Happiness Index",
				"", "Percentage", dataset, PlotOrientation.VERTICAL, true, true, true);



		RenderedImage chartImage = chart.createBufferedImage(700, 600);
		ImageIO.write(chartImage, "png", os);
		os.flush();
		os.close();

		dateres1.clear();dateres2.clear();dateres3.clear();;dateres4.clear();
	}



	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
