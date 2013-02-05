/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portalweb.portlet.wikidisplay.wikipage.linkwdfrontpagechildpagetowdfrontpagechildpage;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class LinkWDFrontPageChildPageToWDFrontPageChildPageTest
	extends BaseTestCase {
	public void testLinkWDFrontPageChildPageToWDFrontPageChildPage()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.waitForElementPresent("link=Wiki Display Test Page");
		selenium.clickAt("link=Wiki Display Test Page",
			RuntimeVariables.replace("Wiki Display Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForText("//div[@class='child-pages']/ul/li/a[contains(.,'Wiki FrontPage ChildPage2 Title')]",
			"Wiki FrontPage ChildPage2 Title");
		assertEquals(RuntimeVariables.replace("Wiki FrontPage ChildPage2 Title"),
			selenium.getText(
				"//div[@class='child-pages']/ul/li/a[contains(.,'Wiki FrontPage ChildPage2 Title')]"));
		selenium.clickAt("//div[@class='child-pages']/ul/li/a[contains(.,'Wiki FrontPage ChildPage2 Title')]",
			RuntimeVariables.replace("Wiki FrontPage ChildPage2 Title"));
		selenium.waitForPageToLoad("30000");
		assertFalse(selenium.isTextPresent("Wiki FrontPage Child Page1 Title"));
		assertEquals(RuntimeVariables.replace("Edit"),
			selenium.getText(
				"//div[@class='page-actions top-actions']/span/a[contains(.,'Edit')]"));
		selenium.clickAt("//div[@class='page-actions top-actions']/span/a[contains(.,'Edit')]",
			RuntimeVariables.replace("Edit"));
		selenium.waitForPageToLoad("30000");
		Thread.sleep(1000);
		selenium.waitForText("//span[@class='cke_toolbar']/span[contains(.,'Format')]/a",
			"Format");
		selenium.waitForText("//span[.='Source']", "Source");
		assertEquals(RuntimeVariables.replace("Source"),
			selenium.getText("//span[.='Source']"));
		selenium.clickAt("//span[.='Source']",
			RuntimeVariables.replace("Source"));
		selenium.waitForVisible(
			"//a[@class='cke_button cke_button__source cke_button_on']");
		selenium.waitForVisible("//div[@id='cke_1_contents']/textarea");
		selenium.type("//div[@id='cke_1_contents']/textarea",
			RuntimeVariables.replace(
				"Wiki FrontPage ChildPage2 Content\n\n[[Wiki FrontPage ChildPage1 Title]]"));
		assertEquals(RuntimeVariables.replace("Source"),
			selenium.getText("//span[.='Source']"));
		selenium.clickAt("//span[.='Source']",
			RuntimeVariables.replace("Source"));
		selenium.waitForElementPresent("//div[@id='cke_1_contents']/iframe");
		assertTrue(selenium.isVisible("//div[@id='cke_1_contents']/iframe"));
		selenium.selectFrame("//div[@id='cke_1_contents']/iframe");
		selenium.waitForPartialText("//body[@class='html-editor portlet portlet-wiki cke_editable cke_editable_themed cke_contents_ltr cke_show_borders']/p",
			"Wiki FrontPage ChildPage2 Content");
		selenium.waitForPartialText("//body[@class='html-editor portlet portlet-wiki cke_editable cke_editable_themed cke_contents_ltr cke_show_borders']/p[2]",
			"Wiki FrontPage ChildPage1 Title");
		selenium.selectFrame("relative=top");
		selenium.clickAt("//input[@value='Publish']",
			RuntimeVariables.replace("Publish"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace("Wiki FrontPage ChildPage1 Title"),
			selenium.getText("//div[@class='wiki-body']/p/a"));
		selenium.open("/web/guest/home/");
		selenium.waitForElementPresent("link=Wiki Display Test Page");
		selenium.clickAt("link=Wiki Display Test Page",
			RuntimeVariables.replace("Wiki Display Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForText("//div[@class='child-pages']/ul/li/a[contains(.,'Wiki FrontPage ChildPage2 Title')]",
			"Wiki FrontPage ChildPage2 Title");
		selenium.clickAt("//div[@class='child-pages']/ul/li/a[contains(.,'Wiki FrontPage ChildPage2 Title')]",
			RuntimeVariables.replace("Wiki FrontPage ChildPage2 Title"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForText("//div[@class='wiki-body']/p/a",
			"Wiki FrontPage ChildPage1 Title");
		selenium.clickAt("//div[@class='wiki-body']/p/a",
			RuntimeVariables.replace("Wiki FrontPage ChildPage1 Title"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Wiki FrontPage ChildPage1 Title"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertEquals(RuntimeVariables.replace(
				"Wiki FrontPage ChildPage1 Content"),
			selenium.getText("//div[@class='wiki-body']/p"));
	}
}