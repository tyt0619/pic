package com.imageloader.server;

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class test
 */
@WebServlet("/test")
public class test extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public test() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append(request.getSession().getServletContext().getRealPath(request.getRequestURI())+"\n");
		//response.getWriter().append("Served at: ").append(request.getContextPath()+"\n");
		String path=this.getServletConfig().getServletContext().getRealPath("/");
		//response.getWriter().append(request.getLocalAddr()+"\n");
		String ip=request.getLocalAddr();
		//response.getWriter().append(path+"\n");
		File root=new File(path+"pic");
		if(root.exists() && root.isDirectory())
		{
			response.setStatus(200);
			File[] files=root.listFiles();
			for(int i=0;i<files.length;i++){
				response.getWriter().append("http://"+ip+":8080/test2/pic/"+files[i].getName()+"\n");
			}
		}
		else{
			response.setStatus(500);
			//response.getWriter().append("error");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
