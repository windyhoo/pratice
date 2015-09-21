package ForSource;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LocalUserFilter implements Filter {
	public final String REQ_UUID = "UUID";
	/**
	 * ��ʼ��
	 */
	public void init(FilterConfig arg0) throws ServletException {
	}

	/**
	 * ���ñ����û�
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
				
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpSession session = httpRequest.getSession(true);
//		String cityId = (String) session.getAttribute(CrmConstants.CRM_WEBLOGIN_POSTCITYID);
//		String rCityId = cityId;
		/*if (rCityId == null || rCityId.equalsIgnoreCase("")) {
			// check url parameters to obtain city id
			rCityId = httpRequest.getParameter("rCityId");
			if (rCityId != null && !rCityId.equalsIgnoreCase("")) {
				session.setAttribute("loginPostCityID", rCityId);
				session.setAttribute("rCityId", rCityId);
			}
		}*/ 
		//***************�ĳ����¼*****************
		session.setAttribute("staffId", "dlq");
		session.setAttribute("loginPostCityID", "200");
		session.setAttribute("rCityId", "200");
//		System.out.println("LocalUserFilter ~~~ thread: " + Thread.currentThread().getId());
//		WebAuthorizationUtil.copySessionToLocalThread(httpRequest, null);
//		String cid = WebLocalUser.getRCityId();

		String userState=(String) session.getAttribute("user_state");

//		System.out.println("here:"+ServletActionContext.getRequest().getRequestURI());
		HttpServletRequest httpSR = (HttpServletRequest) request;

		if(!httpSR.getRequestURI().equals("/imonitorweb/configMgr/loginCheck.action")) {
			if(userState==null || (userState!=null && !userState.equals("true"))) {
				RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
				dispatcher.forward(request, response);
			}
		}
		
		//TODO ����
		chain.doFilter(request, response);

	}

	public void destroy() {
		
	}

}
