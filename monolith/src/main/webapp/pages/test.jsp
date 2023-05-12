<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
	<head>
        <meta charset="utf-8">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
		<title>Test</title>
		<link rel="stylesheet" type="text/css" href="bootstrap/css/bootstrap.min.css">
		<link rel="stylesheet" type="text/css" href="bootstrap/css/bootstrap-theme.min.css">
		<link rel="stylesheet" type="text/css" href="bootstrap/css/custom.css">
		<script type="text/javascript" src="jquery/jquery-1.11.3.js"></script>
		<script type="text/javascript" src="bootstrap/js/bootstrap.min.js"></script>
	</head>
	<body>
		<nav class="navbar navbar-default " role="navigation">
			<div class="container">
				<div class="navbar-header">
					<a class="navbar-brand" href="http://www.iwi.hs-karlsruhe.de">Informatik</a>
				</div>
				<div >
					<ul class="nav navbar-nav">
					</ul>
				</div>
			</div>
		</nav>
	
		<div class="container">
			<div class="row">
					<h2> Test </h2>
			</div>
			<div class="row">
				<div class="col-xs-8">
					<s:form action="TestAction" theme="simple">
						<s:submit method="execute" key="Test Submit" align="center" cssClass="btn btn-success" />
					</s:form>
				</div>
				<div><s:property value="product.name" /></div>
				<div><s:property value="product.price" /></div>
			</div>
			</div>
		<s:actionerror/>
		
		<s:actionmessage/>
	
	</body>
</html>