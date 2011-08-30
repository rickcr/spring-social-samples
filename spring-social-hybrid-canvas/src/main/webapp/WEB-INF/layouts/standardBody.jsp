<?xml version="1.0" encoding="UTF-8" ?>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ page session="false" %>

<div id="leftNav">
	<tiles:insertAttribute name="menu" />
</div>

<div id="content">
	<tiles:insertAttribute name="content" />
</div>