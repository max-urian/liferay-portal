/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portlet;

import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.service.spring.RoleLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.util.GetterUtil;
import com.liferay.util.StringPool;
import com.liferay.util.servlet.ProtectedPrincipal;

import java.io.BufferedReader;
import java.io.UnsupportedEncodingException;

import java.security.Principal;

import java.util.Enumeration;
import java.util.Locale;

import javax.portlet.PortletRequest;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="PortletServletRequest.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 * @author  Brian Myunghun Kim
 *
 */
public class PortletServletRequest extends HttpServletRequestWrapper {

	public PortletServletRequest(HttpServletRequest req,
								 PortletRequest portletRequest, String pathInfo,
								 String queryString, String requestURI,
								 String servletPath) {

		super(req);

		_req = req;
		_portletRequest = portletRequest;
		_pathInfo = GetterUtil.getString(pathInfo);
		_queryString = GetterUtil.getString(queryString);
		_requestURI = GetterUtil.getString(requestURI);
		_servletPath = GetterUtil.getString(servletPath);

		String userId = PortalUtil.getUserId(req);
		String remoteUser = req.getRemoteUser();

		if ((userId != null) && (remoteUser == null)) {
			_remoteUser = userId;
			_userPrincipal = new ProtectedPrincipal(userId);
		}
		else {
			_remoteUser = remoteUser;
			_userPrincipal = req.getUserPrincipal();
		}
	}

	public Object getAttribute(String name) {
		Object retVal = super.getAttribute(name);

		if (name == null) {
			return retVal;
		}

		RenderRequestImpl reqImpl = (RenderRequestImpl)_portletRequest;

		if (ServerDetector.isWebSphere()) {
			if (reqImpl.getPortlet().isWARFile()) {
				if (name.equals(WebKeys.JAVAX_SERVLET_INCLUDE_CONTEXT_PATH)) {
					retVal = _portletRequest.getContextPath();
				}
				else if (name.equals(WebKeys.JAVAX_SERVLET_INCLUDE_PATH_INFO)) {
					retVal = _pathInfo;
				}
				else if (name.equals(
							WebKeys.JAVAX_SERVLET_INCLUDE_QUERY_STRING)) {

					retVal = _queryString;
				}
				else if (name.equals(
							WebKeys.JAVAX_SERVLET_INCLUDE_REQUEST_URI)) {

					retVal = _requestURI;
				}
				else if (name.equals(
							WebKeys.JAVAX_SERVLET_INCLUDE_SERVLET_PATH)) {

					retVal = _servletPath;
				}
			}

			if (name.startsWith("javax.servlet.include.") && (retVal == null)) {
				retVal = StringPool.BLANK;
			}
		}

		return retVal;
	}

	public String getCharacterEncoding() {
		return null;
	}

	public void setCharacterEncoding(String encoding)
		throws UnsupportedEncodingException {
	}

	public int getContentLength() {
		return 0;
	}

	public String getContentType() {
		return null;
	}

	public String getContextPath() {
		return _portletRequest.getContextPath();
	}

	public ServletInputStream getInputStream() {
		return null;
	}

	public Locale getLocale() {
		return _portletRequest.getLocale();
	}

	public Enumeration getLocales() {
		return _portletRequest.getLocales();
	}

	public String getPathInfo() {
		return _pathInfo;
	}

	public String getProtocol() {
		return null;
	}

	public String getQueryString() {
		return _queryString;
	}

	public BufferedReader getReader() {
		return null;
	}

	public String getRealPath(String path) {
		return null;
	}

	public String getRemoteAddr() {
		return null;
	}

	public String getRemoteHost() {
		return null;
	}

	public String getRequestURI() {
		return _requestURI;
	}

	public StringBuffer getRequestURL() {
		return null;
	}

	public String getServletPath() {
		return _servletPath;
	}

	public String getRemoteUser() {
		return _remoteUser;
	}

	public Principal getUserPrincipal() {
		return _userPrincipal;
	}

	public boolean isUserInRole(String role) {
		String remoteUser = getRemoteUser();

		if (remoteUser == null) {
			return false;
		}
		else {
			String companyId = PortalUtil.getCompanyId(_req);

			try {
				return RoleLocalServiceUtil.hasUserRole(
					remoteUser, companyId, role);
			}
			catch (Exception e) {
				_log.warn(e);
			}

			return super.isUserInRole(role);
		}
	}

	private static Log _log = LogFactory.getLog(PortletServletRequest.class);

	private HttpServletRequest _req;
	private PortletRequest _portletRequest;
	private String _pathInfo;
	private String _queryString;
	private String _requestURI;
	private String _servletPath;
	private String _remoteUser;
	private Principal _userPrincipal;

}