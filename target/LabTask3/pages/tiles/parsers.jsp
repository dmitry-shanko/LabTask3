<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<span class="contentNewsTitle"><html:link action="/products">
		<bean:message key="label.parsers.products" />
	</html:link></span>
&gt;&gt;
<bean:message key="label.parsers.title" />
<br>
<br>
<html:link action="/changeparser.do?parserName=sax"><bean:message key="label.parsers.link.SAX" /></html:link><br>
<html:link action="/changeparser.do?parserName=dom"><bean:message key="label.parsers.link.DOM" /></html:link><br>
<html:link action="/changeparser.do?parserName=stax"><bean:message key="label.parsers.link.StAX" /></html:link><br>