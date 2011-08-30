<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>

<div style="border: 1px solid gray; padding: 5px;">
	<c:choose>
		<c:when test="${not empty user}">
			<ul class="menu">
				<li><a href="<c:url value="/"/>">Home</a></li>
				<c:if test="${connectedToFacebook}">
					<li><a href="<c:url value="/facebook"/>">User Profile</a></li>
					<li><a href="<c:url value="/facebook/feed"/>">Feed</a></li>
					<li><a href="<c:url value="/facebook/friends"/>">Friends</a></li>
					<li><a href="<c:url value="/facebook/albums"/>">Albums</a></li>
				</c:if>
			</ul>

			<a href="<c:url value="/signout" />">Sign Out</a>

		</c:when>
		<c:otherwise>
			<a href="<c:url value="/"/>">Sign In</a>
		</c:otherwise>
	</c:choose>
</div>
