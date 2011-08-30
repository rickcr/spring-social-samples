<?xml version="1.0" encoding="UTF-8" ?>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ page session="false" %>
<html>
	<head>
		<title><tiles:insertAttribute name="title"/></title>
		<tiles:insertTemplate template="/WEB-INF/layouts/baseHeader.jsp"/>
	</head>

	<body>

		<div id="header">
			<h1><a href="<c:url value="/"/>">Spring Social Hybrid Canvas App</a></h1>
		</div>

		<tiles:insertAttribute name="pageBody" />

	</body>
</html>
