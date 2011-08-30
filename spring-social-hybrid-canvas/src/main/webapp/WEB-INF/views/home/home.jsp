<%@ page session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ taglib uri="http://www.springframework.org/spring-social/facebook/tags" prefix="facebook" %>

<p>Name: ${user.firstName} ${user.lastName}</p>
<p>Username: ${user.userName}</p>
<br/>
<p>You have been logged into Spring Social Hybrid Canvas App with your facebook account.</p>
<br/>
<p>Since you logged in with facebook, you can click links in left menu to view facebook account details</p>

