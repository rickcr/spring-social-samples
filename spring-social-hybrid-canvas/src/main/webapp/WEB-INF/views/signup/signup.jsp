<%@ page session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>


<c:if test="${not empty message}">
<div class="${message.type.cssClass}">${message.text}</div>
</c:if>


<h3>Login with your existing application Login:</h3>
<form:form  action="standardSignIn" method="post" modelAttribute="signInForm">
	<div class="formInfo">
		<s:bind path="*">
			<c:choose>
				<c:when test="${status.error}">
					<div class="error">Unable to sign in. Please fix the errors below and resubmit.</div>
				</c:when>
			</c:choose>
		</s:bind>
	</div>
	<fieldset>
		 <form:label path="username">Username: <form:errors path="username" cssClass="error" /></form:label>
		 <form:input path="username" />
		 <form:label path="password">Password: <form:errors path="password" cssClass="error" /></form:label>
		 <form:password path="password" />
	</fieldset>


	<p><button type="submit">Submit</button></p>
</form:form>
<p>[ Can login with Username "john@foo.com" Password just any six or more characters ]</p>


<h3>Sign Up For A New Account</h3>
<c:url value="/signupSubmit" var="signupUrl" />
<form:form id="signup" action="${signupUrl}" method="post" modelAttribute="signupForm">
	<div class="formInfo">
		<s:bind path="*">
			<c:choose>
				<c:when test="${status.error}">
					<div class="error">Unable to sign up. Please fix the errors below and resubmit.</div>
				</c:when>
			</c:choose>
		</s:bind>
	</div>

	<fieldset>
		<form:label path="firstName">First Name <form:errors path="firstName" cssClass="error" /></form:label>
		<form:input path="firstName" />
		<form:label path="lastName">Last Name <form:errors path="lastName" cssClass="error" /></form:label>
		<form:input path="lastName" />
		<form:label path="username">Username (email) <form:errors path="username" cssClass="error" /></form:label>
		<form:input path="username" />
		<form:label path="password">Password (at least 6 characters) <form:errors path="password" cssClass="error" /></form:label>
		<form:password path="password" />
	</fieldset>
	<p><button type="submit">Sign Up</button></p>
</form:form>

